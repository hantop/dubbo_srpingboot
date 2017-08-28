package com.lizikj.common.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.Date;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class JodaTimeUtil {

    public static boolean isToday(Date date) {
        return (new DateTime(date).toLocalDate()).equals(new LocalDate());
    }

    public static boolean isAfterOneDay(Date date) {
        DateTime dateTime = new DateTime(date).plusDays(1);
        return (dateTime.toLocalDate()).equals(new LocalDate());
    }

    public static boolean isAfterFourDays(Date date) {
        DateTime dateTime = new DateTime(date).plusDays(4);
        return (dateTime.toLocalDate()).equals(new LocalDate());
    }

    public static boolean isBeforFourDays(Date date) {
        DateTime dateTime = new DateTime(date).plusDays(-4);
        return (dateTime.toLocalDate()).equals(new LocalDate());
    }

    public static boolean isAfterOneMonthAndOneDay(Date date) {
        DateTime dateTime = new DateTime(date).plusMonths(1).plusDays(1);
        return (dateTime.toLocalDate()).equals(new LocalDate());
    }

    /**
     * 获取两个时间之间的间隔天数(起始时间大于终止时间返回负数)
     */
    public static Integer daysBetween(Date start, Date end) {
        return Days.daysBetween(new DateTime(start).toLocalDate(), new DateTime(end).toLocalDate()).getDays();
    }

    /**
     * 判断某个时间是否在指定区间内
     */
    public static boolean inRegion(Date date, LocalTime startLocalTime, LocalTime endLocalTime) {
        try {
            LocalTime localTime = new DateTime(date).toLocalTime();
            if (localTime.isBefore(endLocalTime) && localTime.isAfter(startLocalTime)) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }
}
