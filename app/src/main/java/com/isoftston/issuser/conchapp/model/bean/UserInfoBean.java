package com.isoftston.issuser.conchapp.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by issuser on 2018/4/17.
 */

public class UserInfoBean implements Serializable {
    public int id;//主键
    public String birthday;
    public String browser;//用户使用浏览器类型
    public String code;//员工代码
    public String fDisabled;//是否禁用
    public String email;
    public Date loginDate;//最后登录时间
    public String mobilePhone;//手机
    public String officePhone;//办公电话
    //    public Decimal oder;//显示顺序
    public int pageSize;//每页记录数
    public String proxyEnabled;//是否启用代理
    public String realName;//真名
    public String sex;//性别
    public String signaureFile;//签名文件路径
    public String status;//状态(1:在线，2:离线，0：禁用)
    public String userKey;//用户验证唯一标识
    public String userName;//用户登录名
    public String orgId;//所属部门Id
    public String jobTitleName;
    public String education;//文化程度
    public String factoryTime;//入厂时间
    public String jobName;//岗位名称
    public String factoryId;//所属公司ID
}
