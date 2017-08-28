package com.lizikj.cron.enums.cron;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public enum ExecHistoryStatus {
    DEFAULT((byte) 0), SUCCESS((byte) 1), FAILURE((byte) 2), VETO((byte) 3);

    private Byte code;

    ExecHistoryStatus(Byte code) {
        this.code = code;
    }

    public Byte getCode() {
        return code;
    }
}
