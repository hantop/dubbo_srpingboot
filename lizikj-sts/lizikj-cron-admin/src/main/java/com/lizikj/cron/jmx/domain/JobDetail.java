package com.lizikj.cron.jmx.domain;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JobDetail {
    private Integer id;
    private String name;
    private String group;
    private String description;
    private String jobClass;
    private Boolean durability;
    private Boolean shouldRecover;
    private JobDataMap jobDataMap;
    private Boolean volatility;
    private Integer scheduleId;
    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public Boolean getDurability() {
        return durability;
    }

    public void setDurability(Boolean durability) {
        this.durability = durability;
    }

    public Boolean getShouldRecover() {
        return shouldRecover;
    }

    public void setShouldRecover(Boolean shouldRecover) {
        this.shouldRecover = shouldRecover;
    }

    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    public Boolean getVolatility() {
        return volatility;
    }

    public void setVolatility(Boolean volatility) {
        this.volatility = volatility;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public JobDetail withId(Integer id) {
        setId(id);
        return this;
    }

    public JobDetail withName(String value) {
        setName(value);
        return this;
    }

    public JobDetail withGroup(String value) {
        setGroup(value);
        return this;
    }

    public JobDetail withDescription(String value) {
        setDescription(value);
        return this;
    }

    public JobDetail withDurability(Boolean value) {
        setDurability(value);
        return this;
    }

    public JobDetail withShouldRecover(Boolean value) {
        setShouldRecover(value);
        return this;
    }

    public JobDetail withJobClass(String value) {
        setJobClass(value);
        return this;
    }

    public JobDetail withJobDataMap(JobDataMap value) {
        setJobDataMap(value);
        return this;
    }

    public JobDetail withVolatility(Boolean value) {
        setVolatility(value);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobDetail{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", jobClass='").append(jobClass).append('\'');
        sb.append(", durability=").append(durability);
        sb.append(", shouldRecover=").append(shouldRecover);
        sb.append(", jobDataMap=").append(jobDataMap);
        sb.append(", volatility=").append(volatility);
        sb.append(", scheduleId=").append(scheduleId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
