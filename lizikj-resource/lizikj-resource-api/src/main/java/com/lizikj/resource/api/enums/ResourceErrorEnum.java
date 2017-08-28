package com.lizikj.resource.api.enums;


public enum ResourceErrorEnum {

    PARAMETERS_ERROR("200", "参数有误"),
    SYSTEM_ERROR("999", "系统异常");

    private String code;

    private String message;

    ResourceErrorEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
    public static ResourceErrorEnum fromCode(String code) {
        if (code == null) return null;

        for (ResourceErrorEnum cronErrorEnum : values()) {
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
