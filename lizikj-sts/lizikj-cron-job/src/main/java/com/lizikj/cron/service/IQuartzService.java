package com.lizikj.cron.service;

import org.quartz.SchedulerException;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public interface IQuartzService {
    /**
     * 运行所有任务
     */
    void runAllJob() throws SchedulerException, ClassNotFoundException;
    
    /**
     * 立即执行某个任务
     * @param jobName 任务名
     * */
    void executeJob(String jobName,String jobGroup) throws SchedulerException;
}
