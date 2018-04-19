package com.isoftston.issuser.conchapp.model.bean;

import java.util.List;

/**
 * Created by issuser on 2018/4/18.
 */

public class WorkBean {
//    public List<MessageBean> list;
    public int id;
    public String name;
    public String orgID;
    public String createTime;
    public String createBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
