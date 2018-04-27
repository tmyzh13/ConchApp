package com.isoftston.issuser.conchapp.model.bean;

/**
 * Created by issuser on 2018/4/20.
 */

public class MessageQueryBean {
    /**
     * 查询类别
     */
    private String type;

    /**
     * 查询关键字
     */
    private String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
