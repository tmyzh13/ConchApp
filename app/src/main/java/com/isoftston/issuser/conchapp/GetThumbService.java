package com.isoftston.issuser.conchapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Des:自定义通知处理方式  通过服务获取自定义消息内容  并把内容传给广播接收者  通过服务的方式传递给Activity
 */
public class GetThumbService extends UmengMessageService {

    private static final String TAG = GetThumbService.class.getSimpleName();

    @Override
    public void onMessage(Context context, Intent intent) {

        try {
            // 通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            // 消息体
            Log.i(TAG , "message：" + message);
            // 自定义消息的内容
            Log.i(TAG , "custom：" + msg.custom);
            // 通知标题
            Log.i(TAG , "title：" + msg.title);
            // 通知内容
            Log.i(TAG , "content：" + msg.text);

            // 将消息内容以广播的形式传递给MainActivity
            Intent broadcaseIntent = new Intent();
            if(!"".equals(msg.custom)){
                broadcaseIntent.putExtra("getThumbService.custom" , msg.custom);
            }
            if(!"".equals(msg.title)){
                broadcaseIntent.putExtra("getThumbService.title" , msg.title);
            }
            if(!"".equals(msg.text)){
                broadcaseIntent.putExtra("getThumbService.content" , msg.text);
            }
            broadcaseIntent.setAction("getThumbService");
            sendBroadcast(broadcaseIntent);

            ((MainActivity)context).tabHost.setCurrentTab(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



















