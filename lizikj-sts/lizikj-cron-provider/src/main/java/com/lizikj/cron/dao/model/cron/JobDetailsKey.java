package com.lizikj.cron.dao.model.cron;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JobDetailsKey {
    private String schedName;

    private String jobName;

    private String jobGroup;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}