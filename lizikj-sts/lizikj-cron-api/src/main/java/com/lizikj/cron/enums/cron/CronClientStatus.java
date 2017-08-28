package com.lizikj.cron.enums.cron;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public enum CronClientStatus {
    DISABLE((byte) 0), Enable((byte) 1);

    private Byte status;

    CronClientStatus(Byte status) {
        this.status = status;
    }

    public Byte getStatus() {
        return status;
    }
}
