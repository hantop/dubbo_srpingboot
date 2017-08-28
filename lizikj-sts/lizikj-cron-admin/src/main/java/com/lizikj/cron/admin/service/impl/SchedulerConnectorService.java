package com.lizikj.cron.admin.service.impl;

import com.lizikj.cron.admin.enums.TriggerState;
import com.lizikj.cron.admin.service.ISchedulerConnectorService;
import com.lizikj.cron.dto.CronClientDto;
import com.lizikj.cron.enums.cron.CronClientStatus;
import com.lizikj.cron.facade.ICronFacade;
import com.lizikj.cron.jmx.QuartzSchedulerConnector;
import com.lizikj.cron.jmx.connector.JmxConnection;
import com.lizikj.cron.jmx.utils.ObjectNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Service
public class SchedulerConnectorService implements ISchedulerConnectorService {
    private final static Logger logger = LoggerFactory.getLogger(SchedulerConnectorService.class);

    @Autowired
    private ICronFacade dubboCronFacade;

    // 缓存所有连接
    private Map<String, QuartzSchedulerConnector> quartzSchedulerConnectorMap;

    @Override
    public void initialize() {
        List<CronClientDto> cronClients = dubboCronFacade.findAllCronClients(CronClientStatus.Enable);
        quartzSchedulerConnectorMap = new ConcurrentHashMap<String, QuartzSchedulerConnector>(cronClients.size());
        for (CronClientDto cronClient : cronClients) {
            QuartzSchedulerConnector schedulerConnector = getSchedulerConnector(cronClient.getHost(), cronClient.getPort());
            if (schedulerConnector != null) {
                String domainName = getDomainName(cronClient.getHost(), cronClient.getPort());
                quartzSchedulerConnectorMap.put(domainName, schedulerConnector);
            }
        }
    }

    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.pauseJob(jobName, jobGroupName);
        }
    }

    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.resumeJob(jobName, jobGroupName);
        }
    }

    @Override
    public void pauseTrigger(String triggerName, String triggerGroupName) {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.pauseTrigger(triggerName, triggerGroupName);
        }
    }

    @Override
    public void resumeTrigger(String triggerName, String triggerGroupName) {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.resumeTrigger(triggerName, triggerGroupName);
        }
    }

    @Override
    public TriggerState getTriggerState(String triggerName, String triggerGroupName) {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            return quartzSchedulerConnector.getTriggerState(triggerName, triggerGroupName);
        }
        return null;
    }

    /**
     * 立即执行某个任务
     */
    @Override
    public void triggerJob(String jobName, String jobGroup, Map<String, String> jobDataMap) throws Exception {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.triggerJob(jobName, jobGroup, jobDataMap);
        }
    }

    /**
     * 删除计划任务
     */
    @Override
    public boolean deleteJob(String jobName, String jobGroupName) throws Exception {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector == null) {
            return false;
        }
        return quartzSchedulerConnector.deleteJob(jobName, jobGroupName);
    }

    /**
     * 添加计划任务
     */
    @Override
    public void addJob(Map<String, Object> jobMap, boolean replace) throws Exception {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.addJob(jobMap, replace);
        }
    }

    @Override
    public void scheduleJob(String jobName, String jobGroup, Map<String, Object> triggerMap) throws Exception {
        QuartzSchedulerConnector quartzSchedulerConnector = randomSchedulerConnector();
        if (quartzSchedulerConnector != null) {
            quartzSchedulerConnector.scheduleJob(jobName, jobGroup, triggerMap);
        }
    }

    private String getDomainName(String host, int port) {
        return host + ":" + port;
    }

    private QuartzSchedulerConnector getSchedulerConnector(String host, Integer port) {
        try {
            // 获取 ObjectName
            String objectName = ObjectNameUtil.getObjectName(host, port);
            ObjectName schedulerMBeanName = new ObjectName(objectName);
            // 得到 JMX 连接
            JmxConnection jmxConnection = JmxConnection.forRemoteService(host, port);
            jmxConnection.getConnection();
            return new QuartzSchedulerConnector(jmxConnection, schedulerMBeanName);
        } catch (Exception e) {
            logger.error("get scheduler connector error:", e);
        }
        return null;
    }

    private QuartzSchedulerConnector randomSchedulerConnector() {
        boolean refresh = false;
        for (Map.Entry<String, QuartzSchedulerConnector> schedulerConnectorEntry : quartzSchedulerConnectorMap.entrySet()) {
            if (schedulerConnectorEntry.getValue().getJmxConnection().isClosed()) {
                refresh = true;
                break;
            }
        }
        if (quartzSchedulerConnectorMap.size() == 0 || refresh) {
            refreshConnector();
        }
        for (Map.Entry<String, QuartzSchedulerConnector> schedulerConnectorEntry : quartzSchedulerConnectorMap.entrySet()) {
            QuartzSchedulerConnector quartzSchedulerConnector = schedulerConnectorEntry.getValue();
            if (!quartzSchedulerConnector.getJmxConnection().isClosed()) {
                return quartzSchedulerConnector;
            }
        }
        return null;
    }

    private void refreshConnector() {
        List<CronClientDto> cronClients = dubboCronFacade.findAllCronClients(CronClientStatus.Enable);
        for (CronClientDto cronClient : cronClients) {
            String domainName = getDomainName(cronClient.getHost(), cronClient.getPort());
            if (quartzSchedulerConnectorMap.containsKey(domainName)) {
                QuartzSchedulerConnector quartzSchedulerConnector = quartzSchedulerConnectorMap.get(domainName);
                if (quartzSchedulerConnector.getJmxConnection().isClosed()) {
                    quartzSchedulerConnectorMap.remove(domainName);
                } else {
                    continue;
                }
            }
            QuartzSchedulerConnector schedulerConnector = getSchedulerConnector(cronClient.getHost(), cronClient.getPort());
            if (schedulerConnector != null) {
                quartzSchedulerConnectorMap.put(domainName, schedulerConnector);
            }
        }
    }

}
