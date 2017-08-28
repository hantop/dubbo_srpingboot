package com.lizikj.cron.jmx;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class QuartzSchedulerConnectorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QuartzSchedulerConnectorException(String message) {
        super(message);
    }

    public QuartzSchedulerConnectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuartzSchedulerConnectorException(Throwable cause) {
        super(cause);
    }

}
