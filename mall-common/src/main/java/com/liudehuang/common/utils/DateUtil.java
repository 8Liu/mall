package com.liudehuang.common.utils;

import com.liudehuang.common.exception.ConvertException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:03
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:03
 * @UpdateRemark:
 * @Version:
 */
public class DateUtil {
    public static final String SPLIT_CHAR = "-";
    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String MONTH_DAY = "MM-dd";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Z_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
    public static final String EXCEL_DATE_TIME = "yyyy/MM/dd HH:mm:ss";
    public static final String YEARMONTHDAY = "yyyyMMdd";
    public static final String YEARMONTHDAYHHMMSS = "yyyyMMddHHmmss";
    public static final String YEARMONTHDAYHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String SHORT_YEARMONTHDAY = "yyMMdd";
    public static final String SHORT_YEARMONTHDAYHHMMSS = "yyMMddHHmmss";
    public static final String HHMMSSSSS = "HHmmssSSS";
    private static final String COMPARE_DATE_NULL_MSG = "比较的日期不能为空";

    protected DateUtil() {
    }

    public static Date formatDateTimeStr(String dateTimeStr, String pattern) throws ConvertException {
        return StringUtils.isEmpty(dateTimeStr) ? new Date() : formatDateTimeStr(dateTimeStr, pattern, false);
    }

    public static Date formatUTCDateTimeStr(String dateTimeStr, String pattern) throws ConvertException {
        return formatDateTimeStr(dateTimeStr, pattern, false);
    }

    public static Date formatDateTimeStr(String dateTimeStr, String pattern, boolean utcFlag) throws ConvertException {
        if (utcFlag) {
            if (StringUtils.isEmpty(pattern)) {
                pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
            }

            dateTimeStr = dateTimeStr.replace("Z", " UTC");
        } else {
            if (StringUtils.isEmpty(pattern)) {
                pattern = "yyyyMMddHHmmss";
            }

            if (dateTimeStr.length() < pattern.length()) {
                pattern = pattern.substring(0, dateTimeStr.length());
            }
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(dateTimeStr);
        } catch (ParseException var4) {
            throw new ConvertException(var4);
        }
    }

    /** @deprecated */
    @Deprecated
    public static String alipayDateParse(String aliDateStr) {
        String resultDataStr;
        if (!StringUtils.isEmpty(aliDateStr)) {
            Date aliDate = formatDateTimeStr(aliDateStr, "yyyy-MM-dd HH:mm:ss");
            resultDataStr = formatDate(aliDate, "yyyyMMddHHmmss");
        } else {
            resultDataStr = formatDate(new Date(), "yyyyMMddHHmmss");
        }

        return resultDataStr;
    }

    public static String formatDate(Date date, String pattern) {
        if (null == date) {
            return null;
        } else {
            SimpleDateFormat format;
            if (StringUtils.isEmpty(pattern)) {
                format = new SimpleDateFormat("yyyyMMddHHmmss");
            } else {
                format = new SimpleDateFormat(pattern);
            }

            return format.format(date);
        }
    }

    public static Date addYear(int n) {
        return addYear((Date)null, n);
    }

    public static Date addYear(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(1, n);
        return rightNow.getTime();
    }

    public static Date addMonth(int n) {
        return addMonth((Date)null, n);
    }

    public static Date addMonth(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(2, n);
        return rightNow.getTime();
    }

    public static Date addWeek(int n) {
        return addWeek((Date)null, n);
    }

    public static Date addWeek(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(3, n);
        return rightNow.getTime();
    }

    public static Date addDay(int n) {
        return addDay((Date)null, n);
    }

    public static Date addDay(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(6, n);
        return rightNow.getTime();
    }

    public static Date addHour(int n) {
        return addHour((Date)null, n);
    }

    public static Date addHour(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(11, n);
        return rightNow.getTime();
    }

    public static Date addMinute(int n) {
        return addMinute((Date)null, n);
    }

    public static Date addMinute(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(12, n);
        return rightNow.getTime();
    }

    public static Date addSeconds(int n) {
        return addSeconds((Date)null, n);
    }

    public static Date addSeconds(Date date, int n) {
        Calendar rightNow = getCalendar(date);
        rightNow.add(13, n);
        rightNow.set(14, 0);
        return rightNow.getTime();
    }

    public static int getYear() {
        return getYear((Date)null);
    }

    public static int getYear(Date date) {
        Calendar rightNow = getCalendar(date);
        return rightNow.get(1);
    }

    public static int getMonthOfYear() {
        return getMonthOfYear((Date)null);
    }

    public static int getMonthOfYear(Date date) {
        Calendar rightNow = getCalendar(date);
        return rightNow.get(2);
    }

    public static int getWeekOfYear() {
        return getWeekOfYear((Date)null);
    }

    public static int getWeekOfYear(Date date) {
        Calendar rightNow = getCalendar(date);
        int rightWeek = rightNow.get(3);
        if (rightWeek == 1 && rightNow.get(2) == 11) {
            rightWeek = 53;
        }

        return rightWeek;
    }

    public static int getQuarterOfYear() {
        return getQuarterOfYear((Date)null);
    }

    public static int getQuarterOfYear(Date date) {
        return getMonthOfYear(date) / 3 + 1;
    }

    public static int getMonthDay() {
        return getMonthDay((Date)null);
    }

    public static int getMonthDay(Date date) {
        Calendar rightNow = getCalendar(date);
        return rightNow.get(5);
    }

    public static int getWeekDay() {
        return getWeekDay((Date)null);
    }

    public static int getWeekDay(Date date) {
        Calendar rightNow = getCalendar(date);
        return rightNow.get(7);
    }

    public static Date getFirstDayOfYear() {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(2, 0);
        setFirstTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    /** @deprecated */
    @Deprecated
    public static Date getFirstdayOfYear(int year) {
        return getFirstDayOfYear(year);
    }

    public static Date getFirstDayOfYear(int year) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(1, year);
        rightNow.set(2, 0);
        setFirstTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfYear() {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(2, 11);
        setLastTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfYear(int year) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(1, year);
        rightNow.set(2, 11);
        setLastTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    /** @deprecated */
    @Deprecated
    public static Date getFirstdayOfMonth(Date date) {
        return getFirstDayOfMonth(date);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar rightNow = getCalendar(date);
        setFirstTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    /** @deprecated */
    @Deprecated
    public static Date getFirstdayOfMonth(int month) {
        return getFirstDayOfMonth(month);
    }

    public static Date getFirstDayOfMonth(int month) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(2, month);
        setFirstTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(1, year);
        rightNow.set(2, month);
        setFirstTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar rightNow = getCalendar(date);
        setLastTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfMonth(int month) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(2, month);
        setLastTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfMonth(int year, int month) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(1, year);
        rightNow.set(2, month);
        setLastTimeOfMonth(rightNow);
        return rightNow.getTime();
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar rightNow = getCalendar(date);
        rightNow.set(7, 1);
        setFirstTimeOfDay(rightNow);
        return rightNow.getTime();
    }

    public static Date getFirstDayOfWeek(int n) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(3, n);
        rightNow.set(7, 1);
        setFirstTimeOfDay(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar rightNow = getCalendar(date);
        rightNow.set(7, 7);
        setLastTimeOfDay(rightNow);
        return rightNow.getTime();
    }

    public static Date getLastDayOfWeek(int n) {
        Calendar rightNow = new GregorianCalendar();
        rightNow.set(3, n);
        rightNow.set(7, 7);
        setLastTimeOfDay(rightNow);
        return rightNow.getTime();
    }

    public static Date getFirstTimeOfDay(Date date) {
        return getDateTimeOfDay(date, 0, 0, 0, 0);
    }

    public static Date getLastTimeOfDay(Date date) {
        return getDateTimeOfDay(date, 23, 59, 59, 999);
    }

    public static Date getDateTimeOfDay(Date date, int hour, int minute, int second, int milliSecond) {
        Calendar rightNow = getCalendar(date);
        setDayTime(rightNow, hour, minute, second, milliSecond);
        return rightNow.getTime();
    }

    public static Long getDiffMillis(Date d1, Date d2) {
        if (null != d1 && null != d2) {
            long diffMillis = d2.getTime() - d1.getTime();
            return Math.abs(diffMillis);
        } else {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
    }

    public static long getDiffDays(Date d1, Date d2) {
        if (null != d1 && null != d2) {
            Calendar c1 = getCalendarFromDate(d1);
            setFirstTimeOfDay(c1);
            Calendar c2 = getCalendarFromDate(d2);
            setFirstTimeOfDay(c2);
            long diffMillis = c2.getTimeInMillis() - c1.getTimeInMillis();
            long diffDays = 0L;
            if (diffMillis != 0L) {
                long dayMills = 86400000L;
                diffDays = diffMillis / dayMills;
            }

            return diffDays;
        } else {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
    }

    public static int getNowMaxMonthDay() {
        GregorianCalendar c = new GregorianCalendar();
        return c.getActualMaximum(5);
    }

    public static int compare(Date d1, Date d2) {
        if (null != d1 && null != d2) {
            Calendar c1 = getCalendarFromDate(d1);
            Calendar c2 = getCalendarFromDate(d2);
            return c1.compareTo(c2);
        } else {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
    }

    public static int compareDate(Date d1, Date d2) {
        if (null != d1 && null != d2) {
            Calendar c1 = getCalendarFromDate(d1);
            setFirstTimeOfDay(c1);
            Calendar c2 = getCalendarFromDate(d2);
            setFirstTimeOfDay(c2);
            return c1.compareTo(c2);
        } else {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static boolean refBetween(Date rangeBoundaryTime1, Date rangeBoundaryTime2) {
        return refBetween((Date)null, rangeBoundaryTime1, rangeBoundaryTime2);
    }

    public static boolean refBetween(Date verifyTime, Date rangeBoundaryTime1, Date rangeBoundaryTime2) {
        if (null != rangeBoundaryTime1 && null != rangeBoundaryTime2) {
            Calendar rangeBoundary1 = getCalendarFromDate(rangeBoundaryTime1);
            Calendar rangeBoundary2 = getCalendarFromDate(rangeBoundaryTime2);
            return refBetween(verifyTime, rangeBoundary1, rangeBoundary2, false);
        } else {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
    }

    public static boolean refBetweenDate(Date rangeBoundaryDate1, Date rangeBoundaryDate2) {
        return refBetweenDate((Date)null, rangeBoundaryDate1, rangeBoundaryDate2);
    }

    public static boolean refBetweenDate(Date verifyTime, Date rangeBoundaryDate1, Date rangeBoundaryDate2) {
        if (null != rangeBoundaryDate1 && null != rangeBoundaryDate2) {
            Calendar rangeBoundary1 = getCalendarFromDate(rangeBoundaryDate1);
            setFirstTimeOfDay(rangeBoundary1);
            Calendar rangeBoundary2 = getCalendarFromDate(rangeBoundaryDate2);
            setFirstTimeOfDay(rangeBoundary2);
            return refBetween(verifyTime, rangeBoundary1, rangeBoundary2, true);
        } else {
            throw new IllegalArgumentException("比较的日期不能为空");
        }
    }

    private static boolean refBetween(Date verifyTime, Calendar rangeBoundary1, Calendar rangeBoundary2, boolean refDayFlag) {
        int compare = rangeBoundary2.compareTo(rangeBoundary1);
        Calendar leftRange;
        Calendar rightRange;
        if (compare == 0) {
            if (!refDayFlag) {
                throw new IllegalArgumentException("区间起始时间与结束时间相同");
            }

            leftRange = rangeBoundary1;
            rightRange = rangeBoundary2;
        } else if (compare == 1) {
            leftRange = rangeBoundary1;
            rightRange = rangeBoundary2;
        } else {
            leftRange = rangeBoundary2;
            rightRange = rangeBoundary1;
        }

        Calendar verifyCalendar = getCalendar(verifyTime);
        if (verifyCalendar.compareTo(leftRange) == -1) {
            return false;
        } else {
            if (refDayFlag) {
                setLastTimeOfDay(rightRange);
            }

            return rightRange.compareTo(verifyCalendar) != -1;
        }
    }

    private static Calendar getCalendar(Date date) {
        Object rightNow;
        if (null != date) {
            rightNow = getCalendarFromDate(date);
        } else {
            rightNow = new GregorianCalendar();
        }

        return (Calendar)rightNow;
    }

    private static Calendar getCalendarFromDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    private static void setFirstTimeOfMonth(Calendar calendar) {
        calendar.set(5, 1);
        setFirstTimeOfDay(calendar);
    }

    private static void setLastTimeOfMonth(Calendar calendar) {
        calendar.set(5, calendar.getActualMaximum(5));
        setLastTimeOfDay(calendar);
    }

    private static void setFirstTimeOfDay(Calendar calendar) {
        setDayTime(calendar, 0, 0, 0, 0);
    }

    private static void setLastTimeOfDay(Calendar calendar) {
        setDayTime(calendar, 23, 59, 59, 999);
    }

    private static void setDayTime(Calendar calendar, int hour, int minute, int second, int milliSecond) {
        calendar.set(11, hour);
        calendar.set(12, minute);
        calendar.set(13, second);
        calendar.set(14, milliSecond);
    }
}