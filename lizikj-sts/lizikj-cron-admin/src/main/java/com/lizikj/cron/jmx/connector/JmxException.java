package com.lizikj.cron.jmx.connector;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class JmxException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JmxException(String message) {
        super(message);
    }

    public JmxException(String message, Throwable cause) {
        super(message, cause);
    }

    public JmxException(Throwable cause) {
        super(cause);
    }

}
