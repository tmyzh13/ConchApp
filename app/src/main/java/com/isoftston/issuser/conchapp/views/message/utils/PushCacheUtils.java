package com.isoftston.issuser.conchapp.views.message.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.SecurityTroubleBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 读取本地的缓存数据
     */
    public List<MessageBean> readPushLocalCache(Context context) {
        String msg = SharePrefsUtils.getValue(context, Constant.PUSH_MESSAGE, "");
        if (!TextUtils.isEmpty(msg)) {
            List<MessageBean> list = JSONObject.parseArray(msg, MessageBean.class);
            return list;
        }
        return null;
    }

    /**
     * 读取本地的缓存数据
     */
    public List<String> readPushLocalCacheType(Context context, String type) {
        String msg = SharePrefsUtils.getValue(context, Constant.PUSH_MESSAGE, "");
        List<String> returnList = new ArrayList<>();
        if (!TextUtils.isEmpty(msg)) {
            List<MessageBean> list = JSONObject.parseArray(msg, MessageBean.class);
            for (MessageBean bean : list) {
                if (bean.getType().equals(type)) {
                    returnList.add(bean.getId());
                }
            }
            return returnList;
        }
        return returnList;
    }

    /**
     * 缓存推送的消息
     *
     * @param context
     * @param list
     */
    public void writePushLocalCache(Context context, List<MessageBean> list) {
        List<MessageBean> listLocal = readPushLocalCache(context);
        if (listLocal == null) {
            listLocal = new ArrayList<>();
        }
        listLocal.addAll(list);
        SharePrefsUtils.putValue(context, Constant.PUSH_MESSAGE, JSONObject.toJSONString(listLocal));
    }

    /**
     * 缓存推送的消息
     *
     * @param context
     * @param map
     */
    public void writePushLocalCache(Context context, Map<String, String> map) {
        if (map.containsKey("id") && map.containsKey("type")) {
            MessageBean bean = new MessageBean();
            bean.setType(map.get("type"));
            bean.setId(map.get("id"));
            List<MessageBean> listLocal = readPushLocalCache(context);
            if (listLocal == null) {
                listLocal = new ArrayList<>();
            }
            if (listLocal == null || listLocal.size() == 0) {
                listLocal.add(bean);
                SharePrefsUtils.putValue(context, Constant.PUSH_MESSAGE, JSONObject.toJSONString(listLocal));
            } else {
                boolean isHave = false;
                for (MessageBean localBean : listLocal) {
                    if (localBean.getId().equals(bean.getId()) && localBean.getType().equals(bean.getType())) {
                        isHave = true;
                    }
                }
                if (!isHave){
                    listLocal.add(bean);
                    SharePrefsUtils.putValue(context, Constant.PUSH_MESSAGE, JSONObject.toJSONString(listLocal));
                }
            }
        }
    }

    /**
     * 覆盖本地缓存推送的消息
     *
     * @param context
     * @param list
     */
    public void coverPushLocalCache(Context context, List<MessageBean> list) {
        SharePrefsUtils.putValue(context, Constant.PUSH_MESSAGE, JSONObject.toJSONString(list));
    }

    /**
     * 获取对应类型未读消息的数量
     *
     * @param type 1是隐患   2是违章  3是安全
     */
    public int getTypeMessageCount(List<MessageBean> list, String type) {
        if (list != null && list.size() >= 0) {
            int count = 0;
            if ("all".equals(type)) {
                return list.size();
            }
            for (MessageBean bean : list) {
                if (type.equals(bean.getType())) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    /**
     * 对比缓存中的消息
     *
     * @param context
     * @param list
     */
    public void compareLocalPushMessage(Context context, List<MessageBean> list) {
        List<MessageBean> localList = readPushLocalCache(context);
        if (localList == null || localList.size() == 0) {
            return;
        }
        for (MessageBean localBean : localList) {
            for (MessageBean bean : list) {
                if (localBean.getId().equals(bean.getId())) {
                    bean.setRead(false);
                }
            }
        }
    }


    /**
     * 对比缓存中的消息 ,安全模块
     *
     * @param context
     * @param list
     */
    public void compareLocalSecurityPushMessage(Context context, List<SecurityTroubleBean> list) {
        List<MessageBean> localList = readPushLocalCache(context);
        if (localList == null || localList.size() == 0) {
            return;
        }
        for (MessageBean localBean : localList) {
            for (SecurityTroubleBean bean : list) {
                if (localBean.getId().equals(bean.getId())) {
                    bean.setRead(false);
                }
            }
        }
    }

    /**
     * 删除缓存在指定的id的信息
     *
     * @param context
     * @param id      删除的信息id
     */
    public void removePushIdMessage(Context context, String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        List<MessageBean> localList = readPushLocalCache(context);
        if (localList == null) {
            return;
        }
        for (MessageBean bean : localList) {
            if (id.equals(bean.getId())) {
                localList.remove(bean);
                break;
            }
        }
        coverPushLocalCache(context, localList);
    }
}
