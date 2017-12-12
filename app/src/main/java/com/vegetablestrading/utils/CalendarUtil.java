package com.vegetablestrading.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ${王sir} on 2017/11/14.
 * application
 */

public class CalendarUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @NonNull
    private static Calendar getCalendar() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        return ca;
    }

    /**
     * 将时间戳转换为固定格式的时间
     *
     * @return
     */
    public static String getSpecialTypeTime(String time) {
        // 将时间戳转为字符串
            String re_StrTime = null;
            long lcc_time = Long.valueOf(time);
            re_StrTime = sdf.format(new Date(lcc_time * 1000L));
            return re_StrTime;
}

    /**
     * 获取当前的时间
     *
     * @return
     */
    public static String getCurrentTime() {
        Calendar ca = Calendar.getInstance();
        return sdf.format(ca.getTime());

    }

    /**
     * 获取当天12点的时间
     *
     * @return
     */
    public static String getTimeMidOfDay() {
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(ca.getTime()) + " " + "12:00:00";

    }
    /**
     * 获取0点的时间
     *
     * @time yyyy-MM-dd **:**:**
     * @return  yyyy-MM-dd 00:00:00
     */
    public static String getZeroTime(String time) {
        String time_return = "";
        SimpleDateFormat sdf_a = new SimpleDateFormat("yyyy-MM-dd");
        try {
           Date date =  sdf.parse(time);
           time_return =  sdf_a.format(date)+" 00:00:00";
        } catch (ParseException e) {
            e.printStackTrace();
        }
return time_return;
    }

    /**
     * 获取本周的第一天||上周的最后一天
     *
     * @return
     */
    public static String getTimeOfWeekStart() {

        Calendar ca = getCalendar();
        ca.set(Calendar.DAY_OF_WEEK, ca.getFirstDayOfWeek() + 1);
        String time = sdf.format(ca.getTime());
        return time;
    }

    /**
     * 获取本周的最后一天
     *
     * @return
     */
    public static String getTimeOfWeekEnd() {
        Calendar ca = getCalendar();
        ca.set(Calendar.DAY_OF_WEEK, ca.getFirstDayOfWeek() + 1);
        ca.add(Calendar.DAY_OF_WEEK, 7);
        String time = sdf.format(ca.getTime());
        return time;
    }

    /**
     * 获取上周的第一天
     *
     * @return
     */
    public static String getTimeOfLastWeekStart() {

        Calendar ca = getCalendar();
        ca.set(Calendar.DAY_OF_WEEK, ca.getFirstDayOfWeek() + 1);
        ca.add(Calendar.DAY_OF_MONTH, -7);
        String time = sdf.format(ca.getTime());
        return time;
    }


    /**
     * 通过日期获取对应的星期
     *
     * @param time yyyy年MM月dd日
     * @return
     */
    public static String GetWeekFromDate(String time) {

        Calendar cal = Calendar.getInstance();

        int i = -1;

// 对 calendar 设置时间的方法

// 设置传入的时间格式

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

// 指定一个日期

            Date date;

            date = dateFormat.parse(time);

            cal.setTime(date);

            i = cal.get(Calendar.DAY_OF_WEEK);

        } catch (java.text.ParseException e) {


            e.printStackTrace();

        }
        String week = "";
        if (i == 1) {
            week = "星期日";
        }
        if (i == 2) {
            week = "星期一";
        }
        if (i == 3) {
            week = "星期二";
        }
        if (i == 4) {
            week = "星期三";
        }
        if (i == 5) {
            week = "星期四";
        }
        if (i == 6) {
            week = "星期五";
        }
        if (i == 7) {
            week = "星期六";
        }
        return week;
    }

    /**
     * 比较两个时间串的大小
     *
     * @param startTime 开始时间yyyy-MM-dd HH:mm:ss
     * @param endTime   结束时间yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean compareTime(String startTime, String endTime) {
        try {
            Long a = sdf.parse(startTime).getTime();
            Long b = sdf.parse(endTime).getTime();
            if (a > b) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较两个时间串的大小
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean compareTimeOfAddApply(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Long a = sdf.parse(startTime).getTime();
            Long b = sdf.parse(endTime).getTime();
            if (a > b) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据传入的日期，得到对应的日期
     *
     * @param time 一个日期 yyyy-MM-dd HH:mm:ss
     */
    public static String[] getWeekStartAndWeekEndBaseTime(String time) {
        StringBuffer sb = new StringBuffer();
        Calendar ca = getCalendar();
        try {
            Date date = sdf.parse(time);
            ca.setTime(date);
            ca.set(Calendar.DAY_OF_WEEK, ca.getFirstDayOfWeek() + 1);
            String time_start = sdf.format(ca.getTime());
            sb.append(time_start + ",");
            ca.add(Calendar.DAY_OF_WEEK, 6);
            String time_end = sdf.format(ca.getTime());
            sb.append(time_end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sb.toString().split(",");
    }

    /**
     * 将yyyy年MM月dd日格式的日期转换为yyyy-MM-dd HH:mm:ss
     */
    public static String GetUploadTime(String time, String tag) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String time_info = "";
        if ("start".equals(tag)) {//开始时间
            try {
                Date startDate = simpleDateFormat.parse(time);
                return sdf.format(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if ("end".equals(tag)) {//结束时间
            try {
                Date endDate = simpleDateFormat.parse(time);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                return sdf.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return time_info;
    }

    /**
     * 将yyyy年MM月dd日格式的日期转换为yyyy-MM-dd
     */
    public static String GetUploadTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time_info = "";
        try {
            Date startDate = simpleDateFormat.parse(time);
            return sdf.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time_info;
    }

}
