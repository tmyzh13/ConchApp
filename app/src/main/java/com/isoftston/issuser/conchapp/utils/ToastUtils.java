package com.isoftston.issuser.conchapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by issuser on 2018/4/12.
 */

public class ToastUtils {
    public static Toast toast;
    public  static  void showtoast(Context context,String content){
        if (toast==null){
            toast=Toast.makeText(context,content,Toast.LENGTH_SHORT);

        }else {
            toast.setText(content);
        }
        toast.show();
    }
}
