package com.isoftston.issuser.conchapp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/12.
 */

public class DeviceBean implements Serializable{

    public String equipCode;
    public String name;
    public String centerPhone;
    public String hurtType;//伤害种类
    public String attention;//注意事项
    public String accident;//危险事故
    @SerializedName("case")
    public String myCase;//事故案例
    public String inspectionCode;//检修规范
    public int powerSystem;//停送电制度
    public String examine;//审批制度
    public String isolation;//隔离制度
    public String createTime;//创建时间
}
