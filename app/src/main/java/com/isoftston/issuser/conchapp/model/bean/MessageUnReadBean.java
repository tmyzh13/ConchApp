package com.isoftston.issuser.conchapp.model.bean;

import java.util.List;

public class MessageUnReadBean {
    private String type;
    private String[] idList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getIdList() {
        return idList;
    }

    public void setIdList(String[] idList) {
        this.idList = idList;
    }
}
