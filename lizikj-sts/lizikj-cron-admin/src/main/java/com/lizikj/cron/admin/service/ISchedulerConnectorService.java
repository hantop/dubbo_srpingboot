package com.lizikj.cron.admin.service;

import com.lizikj.cron.admin.enums.TriggerState;

import java.util.Map;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface ISchedulerConnectorService {

    void initialize();

    void pauseJob(String jobName, String jobGroupName);

    void resumeJob(String jobName, String jobGroupName);

    void pauseTrigger(String triggerName, String triggerGroupName);

    void resumeTrigger(String triggerName, String triggerGroupName);

    TriggerState getTriggerState(String triggerName, String triggerGroupName);

    /**
     * 立即执行某个任务
     */
    public void triggerJob(String jobName, String jobGroup, Map<String, String> jobDataMap) throws Exception;

    /**
     * 删除计划任务
     */
    public boolean deleteJob(String jobName, String jobGroupName) throws Exception;

    /**
     * 添加计划任务
     */
    public void addJob(Map<String, Object> jobMap, boolean replace) throws Exception;

    public void scheduleJob(String jobName, String jobGroup, Map<String, Object> abstractTriggerInfo) throws Exception;
}
