package com.isoftston.issuser.conchapp.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by issuser on 2018/4/17.
 */

public class WorkDetailBean implements Serializable{
    public long id;//主键
    public String name;//项目名称
    public long startTime;//开始时间
    public long endTime;//结束时间
    public int equipmentType;//设备类型
    public String equipmentCode;//设备型号
    public String equipmentName;//设备名称
    public String area;//作业区域
    public String part;//作业地点
    public String content;//作业内容
    public String company;//作业单位
    public int numberPeople;//作业人数
    public int type;//作业危险类型
    public String gas;//气体检测人id
    public String gasName;//气体检测人id
    public String leading;//负责人id
    public String leadingName;//负责人id
    public String guardian;//监护人id
    public String guardianName;//监护人id
    public String auditor;//审核人id
    public String auditorName;//审核人id
    public String approver;//批准人id
    public String approverName;//批准人id
    public String createId;//创建id
    public long createTime;//创建时间
    public int status;//状态状态(0:新建，1:撤销，2：负责人开工扫描，3：开工，4:负责人结束扫描,5:完成)
    public int isDanger;
    public String orgId;
}
