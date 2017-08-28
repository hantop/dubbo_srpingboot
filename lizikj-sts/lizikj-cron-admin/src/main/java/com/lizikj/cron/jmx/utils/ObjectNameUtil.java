package com.lizikj.cron.jmx.utils;

import com.lizikj.cron.jmx.connector.JmxConnection;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class ObjectNameUtil {

    public static String getObjectName(String host, int port) throws IOException, MalformedObjectNameException {
        ObjectName schedulerMBeanName = new ObjectName("quartz:type=QuartzScheduler,*");
        JmxConnection jmxConnection = JmxConnection.forRemoteService(host, port);
        Set<ObjectName> names = jmxConnection.getConnection().queryNames(schedulerMBeanName, null);
        for (ObjectName objectName : names) {
            String instance = objectName.getKeyProperty("instance");
            String name = objectName.getKeyProperty("name");

            return "quartz:type=QuartzScheduler,name=" + name + ",instance=" + instance;
        }
        return null;
    }

}
