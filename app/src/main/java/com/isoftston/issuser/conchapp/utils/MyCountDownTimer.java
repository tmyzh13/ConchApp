package com.isoftston.issuser.conchapp.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;


/**
 * 继承 CountDownTimer 防范
 * 重写 父类的方法 onTick() 、 onFinish()
 * 倒计时
*/

public class MyCountDownTimer extends CountDownTimer {
    private TextView tv;
    private OnClickListener onClick;
    private boolean isChangeButtonBg;
    private Context context;

        /**
         *
         * @param millisInFuture
         *            表示以毫秒为单位 倒计时的总数
         *
         *            例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *            表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *            例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(TextView tv, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.tv=tv;
        }

        public MyCountDownTimer(Context context,TextView tv, long millisInFuture, long countDownInterval, boolean isChangeButtonBg) {
            super(millisInFuture, countDownInterval);
            this.tv=tv;
            this.isChangeButtonBg = isChangeButtonBg;
            this.context=context;
            tv.setEnabled(false);
            if(isChangeButtonBg){
                tv.setBackgroundResource(R.color.app_blue);
                tv.setTextColor(context.getResources().getColor(R.color.white));
            }
        }
        /**
         * 
         * @param millisInFuture
         *            表示以毫秒为单位 倒计时的总数
         * 
         *            例如 millisInFuture=1000 表示1秒
         * 
         * @param countDownInterval
         *            表示 间隔 多少微秒 调用一次 onTick 方法
         * 
         *            例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         * 
         */
        public MyCountDownTimer(TextView tv, long millisInFuture, long countDownInterval, OnClickListener click) {
            super(millisInFuture, countDownInterval);
            this.onClick = click;
            this.tv=tv;
        }


        @Override
        public void onFinish() {
            if (onClick != null) {
                tv.setOnClickListener(onClick);
            }
            tv.setText(context.getString(R.string.forget_password_get_code_again));
            if(isChangeButtonBg){
                tv.setBackgroundColor(context.getResources().getColor(R.color.app_blue));
                tv.setTextColor(context.getResources().getColor(R.color.white));
            }else{
                myClickListener.click(null,0);
            }
            this.cancel();
            tv.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (onClick != null) {
                tv.setOnClickListener(null);
            }
            tv.setText(millisUntilFinished / 1000 + context.getString(R.string.forget_password_get_code_again_));
        }


    MyClickListener myClickListener;
    public MyCountDownTimer setOnClick(MyClickListener myClickListener){
        this.myClickListener = myClickListener;
        return this;
    }

    public interface MyClickListener {
        public void click(Object object, int value);
    }
}


