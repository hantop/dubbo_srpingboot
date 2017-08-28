package com.lizikj.cron.jmx;

import com.lizikj.cron.admin.enums.TriggerState;
import com.lizikj.cron.jmx.connector.JmxConnection;

import javax.management.ObjectName;
import java.util.Map;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class QuartzSchedulerConnector extends AbstractQuartzSchedulerConnector {

    public QuartzSchedulerConnector(JmxConnection jmxConnection, ObjectName schedulerMBeanName) {
        super(jmxConnection, schedulerMBeanName);
    }

    @Override
    public void start() {
        invokeJmxOperation("start", null, null);
    }

    @Override
    public void stop() {
        invokeJmxOperation("shutdown", null, null);
    }

    @Override
    public void pause() {
        invokeJmxOperation("standby", null, null);
    }

    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        invokeJmxOperation("pauseJob", new Object[]{jobName, jobGroupName}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        invokeJmxOperation("resumeJob", new Object[]{jobName, jobGroupName}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void pauseTrigger(String triggerName, String triggerGroupName) {
        invokeJmxOperation("pauseTrigger", new Object[]{triggerName, triggerGroupName}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void resumeTrigger(String triggerName, String triggerGroupName) {
        invokeJmxOperation("resumeTrigger", new Object[]{triggerName, triggerGroupName}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public TriggerState getTriggerState(String triggerName, String triggerGroupName) {
        String stateStr = (String) invokeJmxOperation("getTriggerState", new Object[]{triggerName, triggerGroupName}, new String[]{String.class.getName(), String.class.getName()});

        return convertTriggerState(stateStr);
    }

    @Override
    public void pauseAllTriggers() {
        this.invokeJmxOperation("pauseAllTriggers", null, null);
    }

    @Override
    public void resumeAllTriggers() {
        this.invokeJmxOperation("resumeAllTriggers", null, null);
    }

    /**
     * 立即执行某个任务
     */
    @Override
    public void triggerJob(String jobName, String jobGroup, Map<String, String> jobDataMap) throws Exception {
        invokeJmxOperation("triggerJob", new Object[]{jobName, jobGroup, jobDataMap}, new String[]{String.class.getName(), String.class.getName(), "java.util.Map"});
    }

    /**
     * 删除计划任务
     */
    @Override
    public boolean deleteJob(String jobName, String jobGroupName) throws Exception {
        return (Boolean) invokeJmxOperation("deleteJob", new Object[]{jobName, jobGroupName}, new String[]{String.class.getName(), String.class.getName()});
    }

    /**
     * 添加计划任务
     */
    @Override
    public void addJob(Map<String, Object> jobMap, boolean replace) throws Exception {
        invokeJmxOperation("addJob", new Object[]{jobMap, replace}, new String[]{"java.util.Map", "boolean"});
    }

    @Override
    public void scheduleJob(String jobName, String jobGroup, Map<String, Object> triggerMap) throws Exception {
        invokeJmxOperation("scheduleJob", new Object[]{jobName, jobGroup, triggerMap}, new String[]{String.class.getName(), String.class.getName(), "java.util.Map"});
    }


    private TriggerState convertTriggerState(String triggerStateStr) {
        if ("NONE".equals(triggerStateStr)) {
            return TriggerState.NONE;
        }
        if ("NORMAL".equals(triggerStateStr)) {
            return TriggerState.NORMAL;
        }
        if ("PAUSED".equals(triggerStateStr)) {
            return TriggerState.PAUSED;
        }
        if ("COMPLETE".equals(triggerStateStr)) {
            return TriggerState.COMPLETE;
        }
        if ("ERROR".equals(triggerStateStr)) {
            return TriggerState.ERROR;
        }
        if ("BLOCKED".equals(triggerStateStr)) {
            return TriggerState.BLOCKED;
        }
        throw new QuartzSchedulerConnectorException("Unexpected value of the trigger state: " + triggerStateStr);
    }

}
