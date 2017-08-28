package com.lizikj.cron.jmx.domain;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JobDataMapEntry {
    private Integer id;
    private String name;
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JobDataMapEntry withName(String value) {
        setName(value);
        return this;
    }

    public JobDataMapEntry withValue(String value) {
        setValue(value);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JobDataMapEntry{");
        sb.append("name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
