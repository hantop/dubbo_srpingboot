package com.lizikj.cron.admin.common;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class ActionRespData {
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    private ActionRespData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ActionRespData(int code, String message, Object data) {
        this(code, message);
        this.data = data;
    }

    public static ActionRespData genError() {
        return new ActionRespData(-1, "系统错误");
    }

    public static ActionRespData genError(String message) {
        return new ActionRespData(-1, message);
    }

    public static ActionRespData genError(int code, String message) {
        return new ActionRespData(code, message);
    }

    public static ActionRespData genSus() {
        return new ActionRespData(0, "成功");
    }

    public static ActionRespData genSus(Object data) {
        return new ActionRespData(0, "成功", data);
    }

    public static ActionRespData genSus(String message, Object data) {
        return new ActionRespData(0, message, data);
    }

    public static ActionRespData genSusMsg(String message) {
        return new ActionRespData(0, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonReturnData [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
