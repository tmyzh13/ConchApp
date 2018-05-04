package com.isoftston.issuser.conchapp.views.message.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.isoftston.issuser.conchapp.model.bean.MessageBean;

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
                String id = intent.getStringExtra("getThumbService.id");
                String type = intent.getStringExtra("getThumbService.type");
                if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(type)){
                    MessageBean bean = new MessageBean();
                    bean.setType(type);
                    bean.setId(id);
                    Message msg = new Message();
                    msg.what = MESS_PUSH_CODE;
                    msg.obj = bean;
                    mHandler.sendMessage(msg);
                }
                break;
        }

    }
}
