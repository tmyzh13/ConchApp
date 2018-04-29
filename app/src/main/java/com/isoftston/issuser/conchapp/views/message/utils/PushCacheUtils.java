package com.isoftston.issuser.conchapp.views.message.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kouxy on 2018/4/29.
 * 缓存推送的消息到本地，提供读取的方法
 */

public class PushCacheUtils {

    private static PushCacheUtils instance;

    public static PushCacheUtils getInstance() {
        synchronized (PushCacheUtils.class) {
            if (instance == null) {
                instance = new PushCacheUtils();
            }
        }
        return instance;
    }

    public List<MessageBean> readPushLocalCache(Context context) {
        String msg = SharePrefsUtils.getValue(context, Constant.PUSH_MESSAGE, "");
        if (!TextUtils.isEmpty(msg)) {
            List<MessageBean> list= JSONObject.parseArray(msg,MessageBean.class);
            return list;
        }
        return null;
    }

    /**
     * 缓存推送的消息
     * @param context
     * @param bean
     */
    public void writePushLocalCache(Context context, MessageBean bean) {
        List<MessageBean> list = readPushLocalCache(context);
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(bean);
        SharePrefsUtils.putValue(context, Constant.PUSH_MESSAGE, JSONObject.toJSONString(list));
    }

    /**
     * 获取对应类型未读消息的数量
     * @param type
     * */
    public int getTypeMessageCount(List<MessageBean> list, String type) {
        if(list != null){
            int count =0 ;
            if("all".equals(type)){
                return list.size();
            }

            for(MessageBean bean : list){
                if(type.equals(bean.getType())){
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    public void compareLocalPushMessage(Context context, List<MessageBean> list) {
        List<MessageBean> localList = readPushLocalCache(context);
        if(localList == null){
            return;
        }
        for (MessageBean localBean : localList){
            for(MessageBean bean : list){
                if(localBean.getId().equals(bean.getId())){
                    bean.setRead(false);
                }
            }
        }
    }


    public void removePushIdMessage(Context context, String id) {
        List<MessageBean> localList = readPushLocalCache(context);
        if(localList == null){
            return;
        }
        for(MessageBean bean : localList){
            if(id.equals(bean.getId())){
                localList.remove(bean);
                break;
            }
        }
    }
}
