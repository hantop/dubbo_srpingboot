package com.lizikj.cron.admin.common;

import org.quartz.CronExpression;

import java.util.Date;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class ValidateUtil {
    /**
     * 验证Quartz表达式
     *
     * @param expression 待验证的表达式
     * @return boolean
     */
    public static boolean validateQuartzExpression(String expression) throws Exception {
        try {
            CronExpression cronExpression = new CronExpression(expression);
            Date date = new Date();
            date = cronExpression.getNextValidTimeAfter(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
