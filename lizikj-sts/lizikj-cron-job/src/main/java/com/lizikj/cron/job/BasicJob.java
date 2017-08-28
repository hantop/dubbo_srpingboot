package com.lizikj.cron.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@DisallowConcurrentExecution
public abstract class BasicJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(BasicJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobName = this.getClass().getSimpleName();
        long startTime = System.currentTimeMillis();

        logger.info("start the job: {}", jobName);
        try {
            internalExecute(jobExecutionContext);
        } catch (Exception ex) {
            logger.info("the job is error: {}", jobName);
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("the job is finished: {} [{}ms]", jobName, (endTime - startTime));
        }
    }

    public abstract void internalExecute(JobExecutionContext jobExecutionContext) throws Exception;

}
