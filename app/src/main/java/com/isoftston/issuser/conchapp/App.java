package com.isoftston.issuser.conchapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.common.Configuration;
import com.corelibs.utils.GalleryFinalConfigurator;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by issuser on 2018/4/9.
 */

public class App extends MultiDexApplication {
    private SharedPreferences mSharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        ToastMgr.init(getApplicationContext());
        LitePal.initialize(this);
        UMConfigure.init(this, Urls.APPKEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                Urls.MessageSecret);
        initUpush();
        Configuration.enableLoggingNetworkParams();
        ApiFactory.getFactory().add(Urls.ROOT); //初始化Retrofit接口工厂
        PreferencesHelper.init(getApplicationContext());
        GalleryFinalConfigurator.config(getApplicationContext());

        //galleryfinal 7.0
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        //jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initUpush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
            //注册推送服务，每次调用register方法都会回调该接口

        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                mSharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                SharedPreferences.Editor edit = mSharedPreferences.edit();
                edit.putString("deviceToken",deviceToken);
                edit.commit();
                Log.i("mytoken",deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
            }
        });

        // 拦截SDK默认的处理 交给我们自己去处理
        mPushAgent.setPushIntentServiceClass(GetThumbService.class);
    }
}


