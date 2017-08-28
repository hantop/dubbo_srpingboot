package com.lizikj.cron.jmx;

import com.lizikj.cron.jmx.connector.JmxConnection;

import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.IOException;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public abstract class AbstractQuartzSchedulerConnector implements IQuartzSchedulerConnector {
    private JmxConnection jmxConnection;
    private ObjectName schedulerMBeanName;

    protected AbstractQuartzSchedulerConnector(JmxConnection jmxConnection, ObjectName schedulerMBeanName) {
        this.jmxConnection = jmxConnection;
        this.schedulerMBeanName = schedulerMBeanName;
    }

    @Override
    public JmxConnection getJmxConnection() {
        return this.jmxConnection;
    }

    @Override
    public ObjectName getMBeanName() {
        return this.schedulerMBeanName;
    }

    protected Object invokeJmxOperation(String operationName, Object[] args, String[] signature) {
        try {
            MBeanServerConnection connection = this.jmxConnection.getConnection();
            ObjectName schedulerMBeanName = getMBeanName();

            return connection.invoke(schedulerMBeanName, operationName, args, signature);
        } catch (JMException e) {
            throw new QuartzSchedulerConnectorException(e);
        } catch (IOException e) {
            throw new QuartzSchedulerConnectorException(e);
        }
    }
}
