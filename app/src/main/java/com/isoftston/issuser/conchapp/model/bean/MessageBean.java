package com.isoftston.issuser.conchapp.model.bean;

/**
 * Created by issuser on 2018/4/11.
 */

public class MessageBean {

    public String mId;
    public String id;
    public String type;
    public String content = "";
    public String location;
    public String createTime = "";
    public String imgs;
    private String yhjb;
    private boolean isRead = true; //是否已读

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getYhjb() {
        return yhjb;
    }

    public void setYhjb(String yhjb) {
        this.yhjb = yhjb;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
