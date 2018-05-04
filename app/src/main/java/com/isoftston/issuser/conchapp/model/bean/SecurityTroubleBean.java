package com.isoftston.issuser.conchapp.model.bean;

/**
 * Created by bbwug on 2018/4/27.
 */

public class SecurityTroubleBean {


    /**
     * id :
     * yhlx :
     * yhms :
     * yhdd :
     * cjsj :
     * tplj :
     * yhjb :
     */

    private String id;
    private String yhlx;
    private String yhms;
    private String yhdd;
    private String cjsj;
    private String tplj;
    private String yhjb;
    private String yhlxbm;
    private boolean isRead = true; //是否已读

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYhlx() {
        return yhlx;
    }

    public void setYhlx(String yhlx) {
        this.yhlx = yhlx;
    }

    public String getYhms() {
        return yhms;
    }

    public void setYhms(String yhms) {
        this.yhms = yhms;
    }

    public String getYhdd() {
        return yhdd;
    }

    public void setYhdd(String yhdd) {
        this.yhdd = yhdd;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getTplj() {
        return tplj;
    }

    public void setTplj(String tplj) {
        this.tplj = tplj;
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

    public String getYhlxbm() {
        return yhlxbm;
    }

    public void setYhlxbm(String yhlxbm) {
        this.yhlxbm = yhlxbm;
    }
}
