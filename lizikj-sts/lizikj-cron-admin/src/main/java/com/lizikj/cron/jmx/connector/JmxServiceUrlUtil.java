package com.lizikj.cron.jmx.connector;

import javax.management.remote.JMXServiceURL;
import java.net.MalformedURLException;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JmxServiceUrlUtil {
    public static JMXServiceURL createServiceUrl(String host, int port)
            throws JmxException {
        StringBuilder rawServiceUrl = new StringBuilder();
        rawServiceUrl.append("service:jmx:rmi:///jndi/rmi://").append(host).append(':').append(port).append("/jmxrmi");

        return createServiceUrl(rawServiceUrl.toString());
    }

    public static JMXServiceURL createServiceUrl(String serviceUrl) {
        try {
            return new JMXServiceURL(serviceUrl);
        } catch (MalformedURLException e) {
            throw new JmxException("Invalid format of JMX service URL: " + serviceUrl, e);
        }
    }
}
