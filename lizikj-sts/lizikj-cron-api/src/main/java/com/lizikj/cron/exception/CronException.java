package com.lizikj.cron.exception;

import com.lizikj.cron.enums.CronErrorEnum;

import java.text.MessageFormat;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class CronException extends RuntimeException {
    private static final long serialVersionUID = -5006042022910975725L;

    /**
     * 错误信息模板
     */
    private final static String MSG_TEMPLATE = "错误码:{0}, 描述:{1}";
    private final static String MSG_FULL_TEMPLATE = "错误码:{0}, 描述:{1}, 异常信息:{2}";

    private CronErrorEnum cronErrorEnum;
    private String extMsg;

    public CronException(CronErrorEnum cronErrorEnum) {
        super(MessageFormat.format(MSG_TEMPLATE
                , cronErrorEnum.getCode(), cronErrorEnum.getMessage()));
        this.cronErrorEnum = cronErrorEnum;
    }

    public CronException(CronErrorEnum cronErrorEnum, String exMsg) {
        super(MessageFormat.format(MSG_FULL_TEMPLATE
                , cronErrorEnum.getCode(), cronErrorEnum.getMessage(), exMsg));
        this.cronErrorEnum = cronErrorEnum;
        this.extMsg = exMsg;
    }

    public CronErrorEnum getCronErrorEnum() {
        return cronErrorEnum;
    }

    public String getExtMsg() {
        return extMsg;
    }
}
