package com.isoftston.issuser.conchapp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/12.
 */

public class DeviceBean implements Serializable{

//    public String equipCode;
//    public String name;
//    public String centerPhone;
//    public String hurtType;//伤害种类
//    public String attention;//注意事项
//    public String accident;//危险事故
//    @SerializedName("case")
//    public String myCase;//事故案例
//    public String inspectionCode;//检修规范
//    public int powerSystem;//停送电制度
//    public String examine;//审批制度
//    public String isolation;//隔离制度
//    public String createTime;//创建时间
    private String id;
    private String name;
    private String centerPhone;
    private String hurtType;//伤害种类
    private String attention;//注意事项
    private String accident;//危险事故
    private String cases;
    private String inspectionCode;//检修规范
    private String powerSystem;
    private String examine;
    private String isolationId;
    private String createTime;
    private String showTime;
    private String equipCode;
    private String desc;
    private String descId;
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenterPhone() {
        return centerPhone;
    }

    public void setCenterPhone(String centerPhone) {
        this.centerPhone = centerPhone;
    }

    public String getHurtType() {
        return hurtType;
    }

    public void setHurtType(String hurtType) {
        this.hurtType = hurtType;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getInspectionCode() {
        return inspectionCode;
    }

    public void setInspectionCode(String inspectionCode) {
        this.inspectionCode = inspectionCode;
    }

    public String getPowerSystem() {
        return powerSystem;
    }

    public void setPowerSystem(String powerSystem) {
        this.powerSystem = powerSystem;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getIsolationId() {
        return isolationId;
    }

    public void setIsolationId(String isolationId) {
        this.isolationId = isolationId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescId() {
        return descId;
    }

    public void setDescId(String descId) {
        this.descId = descId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DeviceBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", centerPhone='" + centerPhone + '\'' +
                ", hurtType='" + hurtType + '\'' +
                ", attention='" + attention + '\'' +
                ", accident='" + accident + '\'' +
                ", cases='" + cases + '\'' +
                ", inspectionCode='" + inspectionCode + '\'' +
                ", powerSystem='" + powerSystem + '\'' +
                ", examine='" + examine + '\'' +
                ", isolationId='" + isolationId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", showTime='" + showTime + '\'' +
                ", equipCode='" + equipCode + '\'' +
                ", desc='" + desc + '\'' +
                ", descId='" + descId + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
