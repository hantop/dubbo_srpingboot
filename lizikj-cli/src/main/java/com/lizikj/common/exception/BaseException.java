package com.lizikj.common.exception;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -233607534302505304L;

    String code;

    String message;

    Throwable cause;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }


    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public BaseException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
