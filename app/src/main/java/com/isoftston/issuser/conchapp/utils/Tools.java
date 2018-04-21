package com.isoftston.issuser.conchapp.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by issuser on 2018/4/9.
 */

public class Tools {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getStatueHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     * @author TangWei 2013-12-13下午2:33:16
     */
    public static boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email))
            return false;
        String pattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        return email.matches(pattern);
    }

    // 具体方法（通过反射的方式）
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 判断 一个字段的值否为空
     *
     * @author Michael.Zhang 2013-9-7 下午4:39:00
     * @param s
     * @return
     */
    public static boolean isNull(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }

        return false;
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 获取当前语言 en英语 zh汉语
     * @param context
     * @return
     */
    public static String getLocalLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    public static String getHanyuPinyin(String content) {
        char[] target = content.toCharArray();
        String result = "";
        String temp = "";
        ArrayList<String> list = new ArrayList<>();
        if (Tools.isNull(content)) {
            return result;
        }


        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            for (int i = 0; i < target.length; i++) {

                if (Character.toString(target[i]).matches("[\\u4E00-\\u9FA5]+")) {
//                        if(i==0){
//                            result+=PinyinHelper.toHanyuPinyinStringArray(target[i],format)[0];
//                        }else{
//                            result+=PinyinHelper.toHanyuPinyinStringArray(target[i],format)[0]+" ";
//                        }
                    list.add(temp);
                    temp = "";
                    list.add(PinyinHelper.toHanyuPinyinStringArray(target[i], format)[0]);


                } else {
                    if (!Character.toString(target[i]).equals(" ")) {
                        if ((target[i] + "").matches("[0-9]+")) {
                            if (!Tools.isNull(temp)) {
                                if (isNumeric(temp)) {
                                    //上次存储的都是数字
                                    temp += target[i];
                                } else {
                                    //不是数字
                                    list.add(temp);
                                    temp = target[i] + "";
                                }
                            } else {
                                temp += target[i];
                            }
                        } else {
                            if (!Tools.isNull(temp)) {
                                if (isNumeric(temp)) {
                                    //上次存储的都是数字
                                    list.add(temp);
                                    temp = target[i] + "";

                                } else {
                                    //不是数字
                                    temp += target[i];
                                }
                            } else {
                                temp += target[i];
                            }
                        }
                    } else {
                        list.add(temp);
                        temp = "";
                        list.add(Character.toString(target[i]));
                    }

                }

//                        result+=target[i];
            }


            list.add(temp);
        } catch (Exception e) {

        }

        for (int i = 0; i < list.size(); i++) {
            if (Tools.isNull(list.get(i))) {
//                result+=list.get(i);
            } else {
                result += list.get(i) + " ";
            }

        }

        return result.toUpperCase().trim();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static String getPhoneType() {

        String SYS = "";
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = "xiaomi";//小米
            } else {
                SYS = "android";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    /**
     * 获取手机IMEi
     * @param context
     * @return
     */
    // TODO: Consider calling
    //    ActivityCompat#requestPermissions
    // here to request the missing permissions, and then overriding
    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                                          int[] grantResults)
    // to handle the case where the user grants the permission. See the documentation
    // for ActivityCompat#requestPermissions for more details.
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();

        return imei;
    }
}
