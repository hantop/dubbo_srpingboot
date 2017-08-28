package com.lizikj.cron.service.impl;

import com.lizikj.cron.dto.CronJobDto;
import com.lizikj.cron.facade.ICronFacade;
import com.lizikj.cron.service.IQuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Service
public class QuartzService implements IQuartzService {

    @Autowired
    private ICronFacade cronFacade;
    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @Override
    public void runAllJob() throws SchedulerException, ClassNotFoundException {
        List<CronJobDto> jobs = cronFacade.findCronJobs();
        for (CronJobDto job : jobs) {
            if (!checkExists(job.getTriggerName(), job.getTriggerGroup())) {
                scheduleJob(job);
            } else {
                updateAbleJob(job);
            }
        }
    }

    private boolean checkExists(String triggerName, String triggerGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        return schedulerFactory.getScheduler().checkExists(triggerKey);
    }

    @SuppressWarnings("unchecked")
    private void scheduleJob(CronJobDto job) throws SchedulerException, ClassNotFoundException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = new TriggerKey(job.getTriggerName(), job.getTriggerGroup());
        if (!scheduler.checkExists(triggerKey)) {
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClassName()))
                    .withIdentity(job.getJobName(), job.getJobGroup()).build();

            jobDetail.getJobDataMap().put("jobId", job.getId());

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getTriggerCron());

            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    @Override
    public void executeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        schedulerFactory.getScheduler().triggerJob(jobKey);
    }

    /**
     * 对已经存在的Job检查是否需要更新Trigger
     *
     * @param job
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    private void updateAbleJob(CronJobDto job) throws SchedulerException, ClassNotFoundException {
        if (null == job)
            return;
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (null == jobDetail) {// Job不存在无需理会
            return;
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
        if (scheduler.checkExists(triggerKey)) {
            CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (oldTrigger != null && job.getTriggerCron() != null) {
                if (!job.getTriggerCron().equals(oldTrigger.getCronExpression())) {
                    // Trigger触发时间不一致，替换
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getTriggerCron());
                    CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder).build();
                    scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
                } else {
                    Trigger.TriggerState state = scheduler.getTriggerState(oldTrigger.getKey());
                    if (state == Trigger.TriggerState.ERROR || state == Trigger.TriggerState.BLOCKED) {
                        //如果是错误或者阻塞，重新恢复
                        scheduler.resumeTrigger(oldTrigger.getKey());
                    }
                }
            }
        }
    }

}
