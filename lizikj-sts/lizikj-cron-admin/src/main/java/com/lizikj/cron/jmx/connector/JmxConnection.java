package com.lizikj.cron.jmx.connector;

import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JmxConnection {
    private static final ThreadFactory DAEMON_THREAD_FACTORY = new DaemonThreadFactory();
    private String host;
    private int port;
    private boolean closed = true;
    private MBeanServerConnection connection;
    private JMXConnector remoteServiceConnector;

    private JmxConnection() {
    }

    private JmxConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static JmxConnection forRemoteService(String host, int port) {
        return new JmxConnection(host, port);
    }

    public MBeanServerConnection getConnection() {
        return this.connection = openRemoteConnection();
    }

    public void close() {
        this.connection = null;
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

    private MBeanServerConnection openRemoteConnection() {
        if (isClosed()) {
            JMXServiceURL serviceUrl = JmxServiceUrlUtil.createServiceUrl(host, port);
            try {
                Map<String, Object> environment = new HashMap<String, Object>();
                this.remoteServiceConnector = openRemoteConnection(serviceUrl, environment);

                this.remoteServiceConnector.addConnectionNotificationListener(new NotificationListener() {
                    public void handleNotification(Notification notification, Object handback) {
                        JmxConnection.this.closed = true;
                    }
                }, new NotificationFilter() {
                    private static final long serialVersionUID = 1L;

                    public boolean isNotificationEnabled(Notification notification) {
                        String type = notification.getType();
                        return "jmx.remote.connection.closed".equals(type);
                    }
                }, this);
                this.connection = this.remoteServiceConnector.getMBeanServerConnection();

                this.closed = false;
                return this.connection;
            } catch (Exception e) {
                throw new JmxException("Error opening remote JMX connection to: " + serviceUrl, e);
            }
        }
        return this.connection;
    }

    private JMXConnector openRemoteConnection(final JMXServiceURL url, final Map<String, ?> environment) throws IOException, SecurityException {
        final BlockingQueue<Object> mailbox = new ArrayBlockingQueue<Object>(1);

        ExecutorService executor = Executors.newSingleThreadExecutor(DAEMON_THREAD_FACTORY);
        executor.submit(new Runnable() {
            public void run() {
                try {
                    JMXConnector connector = JMXConnectorFactory.connect(url, environment);
                    if (!mailbox.offer(connector)) {
                        connector.close();
                    }
                } catch (Throwable t) {
                    mailbox.offer(t);
                }
            }
        });
        Object result;
        try {
            result = mailbox.poll(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            if (result == null) {
                if (!mailbox.offer("")) {
                    result = mailbox.take();
                }
            }
        } catch (InterruptedException e) {
            InterruptedIOException ioe = new InterruptedIOException(e.getMessage());
            ioe.initCause(e);
            throw ioe;
        } finally {
            executor.shutdown();
        }
        if (result == null) {
            throw new ConnectException("Timeout while opening remote JMX connection to: " + url);
        }
        if ((result instanceof JMXConnector)) {
            return (JMXConnector) result;
        }
        Throwable cause = (Throwable) result;

        throw new IOException("Error opening remote JMX connection to: " + url, cause);
    }

    private static class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setName(JmxConnection.class.getSimpleName() + "-timeout-thread-" + t.getId());
            t.setDaemon(true);
            return t;
        }
    }
}
