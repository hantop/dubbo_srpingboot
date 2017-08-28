package com.lizikj.common.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public class DateUtils {
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String DATE_PATTERN = "yyyyMMdd";
    /**
     * 日期格式：yyMMdd
     */
    public static final String SHORT_DATE_PATTERN = "yyMMdd";
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    public static final String FULL_INDENT_PATTERN = "yyyyMMddHHmmss";
    /**
     * 日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String FULL_BAR_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式：yyyyMMdd HH:mm:ss
     */
    public static final String FULL_SPECIAL_PATTERN = "yyyyMMdd HH:mm:ss";

    /**
     * 日期时间格式：yyyy-MM-dd
     */
    public static final String FULL_SMALL_PATTERN = "yyyy-MM-dd";

    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    public static final String READ_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 日期时间格式：yyyyMMddHHmmssSSS
     */
    public static final String FULL_READ_INDENT_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 日期时间格式：yyyy-MM-dd HH:mm
     */
    public static final String SEMI_FULL_BAR_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 日期时间格式：yyMMddHHmmss
     */
    public static final String PART_PATTERN = "yyMMddHHmmss";

    /**
     * 日期时间格式：toString 默认格式
     */
    public static final String DATE_DEFAULT_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";

    /**
     * 转换异常信息
     */
    private static final String INVALID_PARAM_MSG = "The date could not be null!";
    public static String[] weekDays = {"（周日）", "(周一)", "（周二）", "（周三）", "（周四）", "（周五）", "（周六）"};

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        return DateTime.now().toDate();
    }

    /**
     * 获取当前时间 格式： yyyyMMddHHmmss
     *
     * @return 字符日期 格式：yyyyMMddHHmmss
     */
    public static String getCurrent() {
        return getCurrent(FULL_INDENT_PATTERN);
    }

    /**
     * 获取当前时间 格式： 自定义
     *
     * @param pattern 时间格式
     * @return 自定义格式的当前时间
     */
    public static String getCurrent(String pattern) {
        return DateTime.now().toString(pattern);
    }

    /**
     * 将字符串转换成固定格式时间
     *
     * @param date    日期
     * @param pattern 自定义格式
     * @return 转换后日期
     */
    public static Date parse(String date, String pattern) {
        DateTime dateTime = parseTime(date, pattern);
        if (dateTime == null)
            return null;
        return dateTime.toDate();
    }

    /**
     * 将字符串转换成固定格式时间
     *
     * @param date    日期
     * @param pattern 自定义格式
     * @return 转换后日期
     */
    public static DateTime parseTime(String date, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(date);
    }

    /**
     * 将日期转换成固定格式字符串
     *
     * @param date    日期
     * @param pattern 自定义格式
     * @return 转换后日期格式字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return null;
        return new DateTime(date).toString(pattern);
    }

    /**
     * 将日期格式字符串转换成固定格式日期字符串
     *
     * @param date          日期
     * @param targetPattern 自定义格式
     * @return 转换后日期格式字符串
     */
    public static String convert(String date, String targetPattern) {
        return convert(date, FULL_INDENT_PATTERN, targetPattern);
    }

    /**
     * 将日期格式字符串转换成固定格式日期字符串
     *
     * @param date          日期
     * @param originPattern 原自定义格式
     * @param targetPattern 目标自定义格式
     * @return 转换后日期格式字符串
     */
    public static String convert(String date, String originPattern, String targetPattern) {
        Date originDate = parse(date, originPattern);
        return format(originDate, targetPattern);
    }

    /**
     * 根据自定义格式获取当前时间
     *
     * @param pattern 自定义格式
     * @return 当前时间
     */
    public static Date getCurrentDate(String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(new DateTime().toString(pattern), format).toDate();
    }

    /**
     * 将时间转换成自定义格式时间
     *
     * @param date    日期
     * @param pattern 自定义格式
     * @return 自定义格式时间
     */
    public static Date formatToDate(Date date, String pattern) {
        if (date == null)
            return null;
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(new DateTime(date).toString(pattern), format).toDate();
    }

    /**
     * 日期增减，负数为减
     *
     * @param dayNum 天数
     * @return 时间
     */
    public static Date plusDays(int dayNum) {
        return new DateTime().plusDays(dayNum).toDate();
    }

    public static String plusDays(int dayNum, String pattern) {
        Date date = new DateTime().plusDays(dayNum).toDate();
        return format(date, pattern);
    }

    /**
     * 按秒偏移,根据{@code source}得到{@code seconds}秒之后的日期<Br>
     *
     * @param source  要求非空
     * @param seconds 秒数,可以为负
     * @return 新创建的Date对象
     */
    public static Date addSeconds(Date source, int seconds) {
        return addDate(source, Calendar.SECOND, seconds);
    }

    /**
     * 指定日期增加天数
     *
     * @param date          时间
     * @param calendarField 天数
     * @param amount        数量
     * @return 新增后的时间
     */
    private static Date addDate(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException(INVALID_PARAM_MSG);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 获取给定时间的那天的最后时刻
     *
     * @param day 给定时间(em.2011-01-25 22:11:00...)
     * @return 给定时间的那天的最后时刻(em.2011-01-25 23:59:59...)
     */
    public static Date getEndOfDay(Date day) {
        if (day == null)
            day = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * 获取给定时间的那天的开始时刻
     *
     * @param day 给定时间(em.2011-01-25 22:11:00...)
     * @return 给定时间的那天的最后时刻(em.2011-01-25 00:00:00...)
     */
    public static Date getStartOfDay(Date day) {
        if (day == null)
            day = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));

        return calendar.getTime();
    }

    /**
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param format    时间格式
     * @return 相差天数
     * @方法名 getDaySub
     * @描述 计算两个日期之前相差的天数
     * @作者 Lin-Xiaofa
     * @创建时间 2016-11-30 下午5:14:49
     */
    public static long getDaySub(Date beginDate, Date endDate, String format) {
        if (StringUtils.isEmpty(format)) {
            return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        }
        beginDate = DateUtils.formatToDate(beginDate, format);
        endDate = DateUtils.formatToDate(endDate, format);
        return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 指定日期增加天数
     *
     * @param source
     * @param days
     * @return
     */
    public static Date addDays(Date source, int days) {
        return addDate(source, Calendar.DATE, days);
    }

    /**
     * @param date  指定日期
     * @param month 月数，如-1表示指定日期前一月的天数，1表示指定日期下一月的天数
     * @return 天数
     * @方法名 getDaysOfMonth
     * @描述 获取指定日期的月份天数
     * @作者 Lin-Xiaofa
     * @创建时间 2016-11-30 下午5:26:18
     */
    public static int getDaysOfMonth(Date date, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 指定日期增加月数
     *
     * @param source
     * @param months
     * @return
     */
    public static Date addMonths(Date source, int months) {
        return addDate(source, Calendar.MONTH, months);
    }

    /**
     * 毫秒转日期串
     *
     * @param millis 毫秒数
     * @return
     */
    public static String millisToDateStr(Long millis) {
        if (millis == null) return "";
        Date date = new Date(millis);
        return format(date, FULL_BAR_PATTERN);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt {"（周日）", "(周一)", "（周二）", "（周三）", "（周四）", "（周五）", "（周六）"}
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        return getWeekOfDate(dt, weekDays);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @param weekDays
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt, String[] weekDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 计算与当前日期相差的月数和天数
     * eg：4月4号和6月6号相差了2个月2天
     */
    public static String calcDiffInMonthAndDay(Date date) {
        DateTime dateTime = new DateTime(date);
        int months = Months.monthsBetween(DateTime.now(), dateTime).getMonths();
        int days = Days.daysBetween(DateTime.now(), dateTime.minusMonths(months)).getDays() + 1;
        if (months > 0 && days > 0) {
            return months + "个月" + days + "天";
        }
        if (months <= 0 && days > 0) {
            return days + "天";
        }
        if (months > 0 && days <= 0) {
            return months + "个月";
        }
        return "0天";
    }


}
