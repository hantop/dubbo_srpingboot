package com.lizikj.cron.admin.enums;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public enum TriggerState {
    NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED;

    private TriggerState() {
    }

    public static TriggerState fromValue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }
}
