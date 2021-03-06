package com.isoftston.issuser.conchapp.model;


import com.corelibs.utils.PreferencesHelper;
import com.google.gson.Gson;
import com.isoftston.issuser.conchapp.model.bean.UserBean;

import java.util.ArrayList;
import java.util.List;


public class UserHelper {
    public static void saveUser(UserBean user) {
        PreferencesHelper.saveData(user);
    }

    public static UserBean getSavedUser() {
        return PreferencesHelper.getData(UserBean.class);
    }

    public static long getUserId() {
        return getSavedUser().id;
    }
    public static void clearUserInfo(Class user){
        PreferencesHelper.remove(user);
    }





    public static  void clearInfo(String key){
        PreferencesHelper.remove(key);
    }
}
