package com.lizikj.cron.jmx;

import com.lizikj.cron.admin.enums.TriggerState;
import com.lizikj.cron.jmx.connector.JmxConnection;

import javax.management.ObjectName;
import java.util.Map;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface IQuartzSchedulerConnector {

    JmxConnection getJmxConnection();

    ObjectName getMBeanName();

    /**
     * 开始
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 暂停
     */
    void pause();

    /**
     * 暂停某个任务
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复某个任务
     */
    void resumeJob(String jobName, String jobGroupName);

    /**
     * 暂停某个触发器
     */
    void pauseTrigger(String triggerName, String triggerGroupName);

    /**
     * 恢复某个触发器
     */
    void resumeTrigger(String triggerName, String triggerGroupName);

    /**
     * 获取触发器状态
     */
    TriggerState getTriggerState(String triggerName, String triggerGroupName);

    /**
     * 暂停所有触发器
     */
    void pauseAllTriggers();

    /**
     * 恢复所有触发器
     */
    void resumeAllTriggers();

    /**
     * 立即执行某个任务
     */
    void triggerJob(String jobName, String jobGroup, Map<String, String> jobDataMap) throws Exception;

    /**
     * 删除计划任务
     */
    boolean deleteJob(String jobName, String jobGroupName) throws Exception;

    /**
     * 添加计划任务
     */
    void addJob(Map<String, Object> abstractJobInfo, boolean replace) throws Exception;

    void scheduleJob(String jobName, String jobGroup, Map<String, Object> abstractTriggerInfo) throws Exception;

}
