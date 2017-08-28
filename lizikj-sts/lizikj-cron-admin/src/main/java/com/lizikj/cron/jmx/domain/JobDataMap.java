package com.lizikj.cron.jmx.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JobDataMap {
    private List<JobDataMapEntry> entry;

    public List<JobDataMapEntry> getEntry() {
        if (this.entry == null) {
            this.entry = new ArrayList<JobDataMapEntry>();
        }
        return this.entry;
    }

    public void setEntry(List<JobDataMapEntry> entry) {
        this.entry = entry;
    }

    public JobDataMap withEntry(JobDataMapEntry... values) {
        if (values != null) {
            for (JobDataMapEntry value : values) {
                getEntry().add(value);
            }
        }
        return this;
    }

    public JobDataMap withEntry(Collection<JobDataMapEntry> values) {
        if (values != null) {
            getEntry().addAll(values);
        }
        return this;
    }
}
