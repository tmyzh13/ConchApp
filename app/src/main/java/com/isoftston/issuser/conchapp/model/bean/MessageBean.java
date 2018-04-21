package com.isoftston.issuser.conchapp.model.bean;

/**
 * Created by issuser on 2018/4/11.
 */

public class MessageBean {
    public String id;
    public String type;
    public String content;
    public String location;
    public String createTime;
    public String imags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImags() {
        return imags;
    }

    public void setImags(String imags) {
        this.imags = imags;
    }
}
