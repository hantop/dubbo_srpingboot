package com.lizikj.cron.enums;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public enum CronErrorEnum {
    PARAMETERS_ERROR("200", "参数有误"),
    USER_NOT_EXIST("201", "用户不存在"),
    ACCOUNT_NOT_EXIST("202", "账户不存在"),
    OBJECT_COPY_ERROR("203", "对象复制异常"),

    SYSTEM_ERROR("999", "系统异常");

    private String code;
    private String message;

    CronErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CronErrorEnum fromCode(String code) {
        if (code == null) return null;

        for (CronErrorEnum cronErrorEnum : values()) {
            if (cronErrorEnum.getCode().equals(code)) {
                return cronErrorEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
