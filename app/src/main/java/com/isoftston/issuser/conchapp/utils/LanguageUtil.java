package com.isoftston.issuser.conchapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageUtil {
    /**
     * @param isEnglish true  ：点击英文，把中文设置未选中
     *                  false ：点击中文，把英文设置未选中
     */
    public static void set(Context context,boolean isEnglish) {

        Configuration configuration = context.getResources().getConfiguration();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.US;
        } else {
            //设置中文
            configuration.locale = Locale.ENGLISH;
        }
        //更新配置
        context.getResources().updateConfiguration(configuration, displayMetrics);
    }
}
