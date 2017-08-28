package com.lizikj.cron.listener;

import com.lizikj.common.util.DateUtils;
import com.lizikj.common.util.SpringContextUtil;
import com.lizikj.cron.dto.ExecHistoryDto;
import com.lizikj.cron.enums.cron.ExecHistoryStatus;
import com.lizikj.cron.facade.ICronFacade;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class SimpleJobListener implements JobListener {
    private final static Logger logger = LoggerFactory.getLogger(SimpleJobListener.class);
    private ICronFacade dubboCronFacade;
    private ThreadLocal<KeyValue<Integer, Long>> execHistoryThreadLocal = new ThreadLocal<KeyValue<Integer, Long>>();

    @Override
    public String getName() {
        return "SimpleJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        try {
            Integer jobId = 0;
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            if (jobDataMap.containsKey("jobId")) {
                jobId = jobDataMap.getIntValue("jobId");
            }

            Thread currentThread = Thread.currentThread();
            ExecHistoryDto execHistory = new ExecHistoryDto();
            execHistory.setJobId(jobId);
            execHistory.setStartedAt(new Date());
            try {
                execHistory.setIpAddr(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
            }
            execHistory.setStatus(ExecHistoryStatus.DEFAULT.getCode());
            execHistory.setThreadName(currentThread.getName());

            if (dubboCronFacade == null) {
                dubboCronFacade = SpringContextUtil.getBean("dubboCronFacade");
            }
            execHistory.setCreateDate(DateUtils.getCurrent(DateUtils.FULL_SMALL_PATTERN));
            Integer execHistoryId = dubboCronFacade.insertExecHistory(execHistory);

            execHistoryThreadLocal.set(new DefaultKeyValue(execHistoryId, System.currentTimeMillis()));
        } catch (Exception ex) {
            logger.error("Job 准备启动监听异常：", ex);
        }
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        try {
            KeyValue<Integer, Long> execHistoryKeyValue = execHistoryThreadLocal.get();

            ExecHistoryDto execHistory = new ExecHistoryDto();
            execHistory.setId(execHistoryKeyValue.getKey());
            execHistory.setFinishedAt(new Date());
            execHistory.setStatus(ExecHistoryStatus.VETO.getCode());

            dubboCronFacade.updateExecHistory(execHistory);
            execHistoryThreadLocal.remove();
        } catch (Exception ex) {
            logger.error("Job 被否决监听异常：", ex);
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
            KeyValue<Integer, Long> execHistoryKeyValue = execHistoryThreadLocal.get();

            ExecHistoryDto execHistory = new ExecHistoryDto();
            execHistory.setId(execHistoryKeyValue.getKey());
            execHistory.setFinishedAt(new Date());
            execHistory.setDuration(System.currentTimeMillis() - execHistoryKeyValue.getValue());
            if (context.getResult() != null)
                execHistory.setResult(context.getResult().toString());
            if (jobException == null) {
                execHistory.setStatus(ExecHistoryStatus.SUCCESS.getCode());
            } else {
                execHistory.setStatus(ExecHistoryStatus.FAILURE.getCode());
                execHistory.setError(jobException.toString());
            }

            dubboCronFacade.updateExecHistory(execHistory);
            execHistoryThreadLocal.remove();
        } catch (Exception ex) {
            logger.error("Job 执行完毕监听异常：", ex);
        }
    }
}
