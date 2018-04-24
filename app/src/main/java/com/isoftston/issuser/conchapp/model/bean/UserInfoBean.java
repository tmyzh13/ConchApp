package com.isoftston.issuser.conchapp.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by issuser on 2018/4/17.
 */

public class UserInfoBean implements Serializable {
    private String id;
    private String realName;
    private String userRole;
    private String sex;
    private String userName;
    private String phoneNum;
    private String companyName;
    private String orgName;

    public String getRealName() {
        return realName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
