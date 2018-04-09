package com.isoftston.issuser.conchapp;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.corelibs.api.ApiFactory;
import com.corelibs.common.Configuration;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.constants.Urls;

/**
 * Created by issuser on 2018/4/9.
 */

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        init();

    }

    private void init(){
        ToastMgr.init(getApplicationContext());
        Configuration.enableLoggingNetworkParams();
        ApiFactory.getFactory().add(Urls.ROOT); //初始化Retrofit接口工厂
        PreferencesHelper.init(getApplicationContext());
    }
}
