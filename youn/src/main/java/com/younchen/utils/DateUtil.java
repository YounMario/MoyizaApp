package com.younchen.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author longquan
 * @date 2014.2.23
 * @description
 */
public class DateUtil {

    private static SimpleDateFormat fullFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat(
            "HH:mm:ss");
    private static SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    private static SimpleDateFormat hourMinute = new SimpleDateFormat("HH:mm");
    // 12小时制
    private static SimpleDateFormat hourMinuteHalf = new SimpleDateFormat(
            "hh:mm");

    public static String getDateNow() {
        return fullFormat.format(new Date());
    }

    /**
     * 时间格式 hh:mm
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getHourMinute(String time) {
        try {
            Date date = StringToDate(time);

            return hourMinute.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 12小时制,带时段， 例如： 凌晨2:34
     *
     * @param time
     * @return
     */
    public static String getTimeByHalfDayMode(String time) {
        try {
            Date date = StringToDate(time);
            String dayPart = getTime(time);
            String day = dayPart + hourMinuteHalf.format(date);
            return day;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String parseTime(Long time) {
        return getDayWithOutHMS(fullFormat.format(time));
    }

    /**
     * 获取粗略的时间， 今天是一天内， 昨天2天内，前天3天内，其他时间的显示XX月XX日，超过今年的再显示XX年XX月XX日（暂时没有）
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getDayWithOutHMS(String time) {
        Date date = null;
        try {
            date = StringToDate(time);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        int daybefore = 0;
        try {
            // 系统的原因可能导致，比较的时间比系统本身的时间大
            daybefore = beforeDay(date);
        } catch (Exception ex) {
            return "未来";
        }
        if (date.getYear() != new Date().getYear()) {
            return date.getYear() + "年" + date.getMonth() + "月" + date.getDay()
                    + "日";
        } else if (daybefore == 0) {
            return "一天内";
        } else if (daybefore == 1) {
            return "两天内";
        } else if (daybefore == 2) {
            return "三天天内";
        }
        // 如果是今年
        else {
            return date.getMonth() + "月" + date.getDay() + "日";
        }
    }

    /**
     * @param time 获取粗略的时间， 比如今天12时2分，若设置showYesterdayTime 为true, 则昨天会显示为
     *             :昨天12时24分 ， 这种格式， 否则显示　昨天
     * @return
     * @throws ParseException
     */
    public static String getDayTime(String time, boolean showYesterdayTime)
            throws ParseException {
        Date date = StringToDate(time);
        int daybefore = 0;

        try {
            // 系统的原因可能导致，比较的时间比系统本身的时间大
            daybefore = beforeDay(date);
        } catch (Exception ex) {
            return "未来";
        }

        String formatDate = (String) DateFormat.format("HH:mm", date);
        if (date.getYear() != new Date().getYear()) {
            return date.getYear() + "年" + date.getMonth() + "月" + date.getDay()
                    + "日";
        }

        if (daybefore == 0) {
            return formatDate;
        } else if (daybefore == 1) {
            if (showYesterdayTime) {
                return "昨天" + formatDate;
            } else {
                return "昨天";
            }

        } else {
            return date.getMonth() + "月" + date.getDay() + "日";
        }
    }

    /**
     * 判断是 中午 早上 还是晚上
     *
     * @param time
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getTime(String time) {
        try {
            Date date = StringToDate(time);
            if (date.getHours() > 0 && date.getHours() < 6)
                return "凌晨";
            else if (date.getHours() >= 6 && date.getHours() < 9) {
                return "早上";
            } else if (date.getHours() >= 9 && date.getHours() < 11) {
                return "上午";
            } else if (date.getHours() >= 11 && date.getHours() < 13) {
                return "中午";
            } else if (date.getHours() >= 13 && date.getHours() < 17) {
                return "下午";
            } else if (date.getHours() >= 17 && date.getHours() < 23) {
                return "晚上";
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return "";
    }

    /**
     * @param time 获取时分秒
     * @return
     * @throws ParseException 日期转换错误
     */
    public static String getDateHourMinuteSeconed(String time)
            throws ParseException {
        Date date = StringToDate(time);
        return timeFormat.format(date);
    }

    /**
     * 获取时间 年月日 时分秒
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getFullTime(String time) throws ParseException {
        Date date = StringToDate(time);
        return fullFormat.format(date);
    }

    /**
     * @param date1  日期1
     * @param date2  日期2
     * @param minute 间隔分钟
     * @return
     * @throws ParseException 日期解析错误
     */
    public static boolean isLongBetweenToTime(String date1, String date2,
                                              int minute) {

        try {
            Date time1 = StringToDate(date1);
            Date time2 = StringToDate(date2);
            long milisecond1 = time1.getTime();
            long milisecond2 = time2.getTime();

            long during = Math.abs(milisecond1 - milisecond2);
            // 5分钟视为长时间
            if (during / 1000 > minute * 60) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    /**
     * @param date 字符串日期转换为Date类型
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String date) throws ParseException {
        return fullFormat.parse(date);
    }

    public static int beforeDay(String date) {
        try {
            Date before = StringToDate(date);
            Date now = new Date();
            if (now.getTime() < before.getTime()) {
                throw new Exception("the date is later than now");
            }
            return daysBetween(now, before);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static int beforeDay(Date before) {
        try {
            Date now = new Date();
            if (now.getTime() < before.getTime()) {
                throw new Exception("the date is later than now");
            }
            return daysBetween(now, before);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        smdate = yearMonthDayFormat.parse(yearMonthDayFormat.format(smdate));
        bdate = yearMonthDayFormat.parse(yearMonthDayFormat.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = Math.abs(time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 升序比较 比较日期大小 前者大于后者 返回-1 ，等于返回0，小于返回1
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDateDesc(String date1, String date2) {
        try {
            Date dateTime1 = StringToDate(date1);
            Date dateTime2 = StringToDate(date2);
            if (dateTime1.getTime() > dateTime2.getTime()) {
                return 1;
            } else if (dateTime1.getTime() == dateTime2.getTime()) {
                return 0;
            } else {
                return -1;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int compareDateASC(String date1, String date2)
            throws ParseException {

        Date dateTime1 = StringToDate(date1);
        Date dateTime2 = StringToDate(date2);

        if (dateTime1.getTime() > dateTime2.getTime()) {
            return -1;
        } else if (dateTime1.getTime() == dateTime2.getTime()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 根据月份判断该月有多少天
     */
    public static int getDayCount(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DATE);
    }
}
