package com.lizikj.common.enums;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public enum DeviceTypeEnum {
    PC(1, "pc"),
    IOS(2, "ios"),
    ANDROID(3, "android"),
    WAP(4, "wap");

    private final int code;

    private final String type;

    private DeviceTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }
    public static DeviceTypeEnum getByCode(int code) {
        for (DeviceTypeEnum deviceType : DeviceTypeEnum.values()) {
            if (deviceType.getCode() == code) {
                return deviceType;
            }
        }
        return null;
    }
    public static DeviceTypeEnum getDeviceTypeEnum(String type) {
        for (DeviceTypeEnum deviceType : DeviceTypeEnum.values()) {
            if (deviceType.getType().equals(type)) {
                return deviceType;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
