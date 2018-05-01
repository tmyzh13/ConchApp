package com.isoftston.issuser.conchapp.views.message.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

/**
 * Created by kouxy on 2018/4/30.
 */

public class PushBroadcastReceiver extends BroadcastReceiver {

    public static final int MESS_PUSH_CODE= 1001;

    private Handler mHandler;

    public PushBroadcastReceiver(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String actiobn = intent.getAction();
        switch (actiobn){
            case "getThumbService":
                mHandler.sendEmptyMessage(MESS_PUSH_CODE);
                break;
        }

    }
}
