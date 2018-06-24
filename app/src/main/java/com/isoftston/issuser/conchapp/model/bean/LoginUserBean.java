package com.isoftston.issuser.conchapp.model.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by issuser on 2018/4/17.
 */

public class LoginUserBean extends DataSupport {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
