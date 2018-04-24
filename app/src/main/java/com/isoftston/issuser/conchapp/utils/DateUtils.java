package com.isoftston.issuser.conchapp.utils;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zhangyinfu on 2017/10/22.
 */

public class DateUtils {
    public final static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
    public final static SimpleDateFormat format_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat format_yyyy_MM_dd_china = new SimpleDateFormat("yyyy年MM月dd日");
    public final static SimpleDateFormat format_yyyy_MM_dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat format_yyyy = new SimpleDateFormat("yyyy");
    public final static SimpleDateFormat format_MM = new SimpleDateFormat("MM");
    public final static SimpleDateFormat format_dd = new SimpleDateFormat("dd");
    public final static SimpleDateFormat format_hour = new SimpleDateFormat("HH");
    public final static SimpleDateFormat format_min = new SimpleDateFormat("mm");
    public final static SimpleDateFormat format_file_name = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");


    public static String checkDataTime(String dataTime, boolean isBeginValue) {
        StringBuilder stringBuilder = new StringBuilder();
        Log.d("zyf", "===== checkDataTime =====" + dataTime.split(" ").length + "");
        String[] strs = dataTime.split(" ");
        if (strs.length > 2) {
            stringBuilder.append(strs[0].trim());
            stringBuilder.append(" ");
            stringBuilder.append(strs[1]);
        } else if (strs.length <= 1) {
            stringBuilder.append(strs[0].trim());
            stringBuilder.append(isBeginValue ? " 00:00:00" : " 23:59:59");
        } else {
            stringBuilder.append(dataTime);
        }
        Log.d("zyf", "===== stringBuilder.toString( =====" + stringBuilder.toString() + "  &" + isBeginValue);
        return stringBuilder.toString().trim();
    }

    public static long getTimeDifference(long newDateTime, long oldDtaeTime) {
        long between = 0;
        between = newDateTime - oldDtaeTime;
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);
        //Log.i("zzz", day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒");
        return between;
    }

    public static String getPauseTime(String videoBeginDateTime, String videoEndDateTime, long diffTime) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String resultTime = videoBeginDateTime;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            long time = begin.getTime() + diffTime;
            if (end.getTime() >= time) {
                Date date = new Date(time);
                resultTime = dfs.format(date);
            } else {
                resultTime = videoEndDateTime;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultTime;
    }

    /**
     * 将int值转换为分钟和秒的格式
     *
     * @param value
     * @return
     */
    public static String formatToString(int value) {
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        //需要减去时区差，否则计算结果不正确(中国时区会相差8个小时)
        value -= TimeZone.getDefault().getRawOffset();
        String result = ft.format(value);
        return result;
    }


    public static int getProgress(String videoBeginDateTime, String videoEndDateTime, long diffTime) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int position = 0;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            float num = ((float) (diffTime) / (float) ((end.getTime() - begin.getTime())));
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数，.后跟几个零代表几位小数
            String positionStr = df.format(num);//返回的是String类型
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(2);
            //nt.format(Double.valueOf(positionStr));
            Log.i("zzz", "&&&&& " + nt.format(Double.valueOf(positionStr)));
            position = (int) (Double.valueOf(positionStr) * 100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return position;
    }

    public static int getProgress(String videoBeginDateTime, String videoEndDateTime, String crrentDateTime) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diffTime = 0L;
        int position = 0;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            Date crrent = dfs.parse(crrentDateTime);

            diffTime = crrent.getTime() - begin.getTime();

            float num = ((float) (diffTime) / (float) ((end.getTime() - begin.getTime())));
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数，.后跟几个零代表几位小数
            String positionStr = df.format(num);//返回的是String类型
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(2);
            //nt.format(Double.valueOf(positionStr));
            Log.i("zzz", "&&&&& " + nt.format(Double.valueOf(positionStr)));
            position = (int) (Double.valueOf(positionStr) * 100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return position;
    }

    public static String getProgressTime(String videoBeginDateTime, String videoEndDateTime, int seekBarProgress) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String resultTime = videoBeginDateTime;
        try {
            Date begin = dfs.parse(videoBeginDateTime);
            Date end = dfs.parse(videoEndDateTime);
            long time = (long) ((end.getTime() - begin.getTime()) * seekBarProgress) / 100;
            Date date = new Date(begin.getTime() + time);
            resultTime = dfs.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultTime;
    }

    public static void setDatePickerDividerColor(DatePicker datePicker) {
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);
        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);
            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public static void setTimePickerDividerColor(TimePicker timePicker) {
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) timePicker.getChildAt(0);
        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(1);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            if (mSpinners.getChildAt(i) instanceof NumberPicker) {
                Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                for (Field pf : pickerFields) {
                    if (pf.getName().equals("mSelectionDivider")) {
                        pf.setAccessible(true);
                        try {
                            pf.set(mSpinners.getChildAt(i), new ColorDrawable());
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void setNumberPickerDividerColor(NumberPicker timePicker) {
//        // 获取 mSpinners
//        LinearLayout llFirst = (LinearLayout) timePicker.getChildAt(0);
//        // 获取 NumberPicker
//        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(1);
//        for (int i = 0; i < mSpinners.getChildCount(); i++) {
//            if (mSpinners.getChildAt(i) instanceof NumberPicker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(timePicker, new ColorDrawable());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
//            }
//        }
    }


    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.set(year, monthOfYear, dayOfMonth);
        return DateFormat.format("yyy-MM-dd", sCalendar).toString();
    }

    public static String formatTime(int year, int monthOfYear, int dayOfMonth, int hour, int min) {
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.set(year, monthOfYear, dayOfMonth, hour, min);
        return DateFormat.format("HH:mm", sCalendar).toString();
    }

    public static String getNewTime_1() {
        Date date = new Date();
        return format1.format(date).toString();
    }

    public static String getNewTime_2() {
        Date date = new Date();
        return format2.format(date).toString();
    }

    public static String formatDate(String dateTimeStr) {
        Date date = null;
        try {
            date = format_yyyy_MM_dd_HH_mm_ss.parse(dateTimeStr);
            return format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTimeStr.split(" ")[0];
    }
    /*时间戳转换成字符窜*/
    public static String getDateToString(String time) {
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new java.util.Date(
                        Long.parseLong(time)));
        return date;
    }

    /*时间戳转换成毫秒*/
    public static long getDateToLongMS(String time) {
        long date = 0;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                    .parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
