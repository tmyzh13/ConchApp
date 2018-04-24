package com.isoftston.issuser.conchapp.model.bean;

import android.widget.Spinner;

/**
 * Created by john on 2018/4/15.
 */

public class CheckPeopleBean {
    private String id;
    private String realName;
    public String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
