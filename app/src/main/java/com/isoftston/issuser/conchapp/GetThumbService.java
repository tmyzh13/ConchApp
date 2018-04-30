package com.isoftston.issuser.conchapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
            if (msg.extra != null) {
                if(msg.extra.containsKey("id") && msg.extra.containsKey("type")){
                    broadcaseIntent.putExtra("getThumbService.id", msg.extra.get("id"));
                    broadcaseIntent.putExtra("getThumbService.type", msg.extra.get("type"));
                }else{
                    broadcaseIntent.putExtra("getThumbService.id", "284");
                    broadcaseIntent.putExtra("getThumbService.type", "1");
                }
            }else{
                broadcaseIntent.putExtra("getThumbService.id", "284");
                broadcaseIntent.putExtra("getThumbService.type", "1");
            }

            //实例化通知管理器
            NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //实例化通知
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
            builder.setContentTitle(msg.title);//设置通知标题
            builder.setContentText( msg.text);//设置通知内容
            builder.setDefaults(NotificationCompat.DEFAULT_ALL);//设置通知的方式，震动、LED灯、音乐等
            builder.setAutoCancel(true);//点击通知后，状态栏自动删除通知
            builder.setSmallIcon(android.R.drawable.ic_media_play);//设置小图标
            builder.setContentIntent(PendingIntent.getActivity(this,0x102,new Intent(this,MainActivity.class),0));//设置点击通知后将要启动的程序组件对应的PendingIntent
            Notification notification=builder.build();

            //发送通知
            notificationManager.notify(0x101,notification);



            broadcaseIntent.setAction("getThumbService");
            sendBroadcast(broadcaseIntent);

           // ((MainActivity)context).tabHost.setCurrentTab(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



















