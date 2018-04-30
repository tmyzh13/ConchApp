package com.isoftston.issuser.conchapp.constants;

/**
 * 接口地址
 * Created by john on 2017/11/7.
 */

public class Urls {
  //  public static final String ROOT="http://10.0.11.208:8099/conch/";//客户环境
    public static final String ROOT="http://220.180.131.37:18000/conch/";//客户环境
//    public static final String ROOT="http://10.28.124.196:8099/conch/";//测试环境
 //   public static final String ROOT="http://10.28.124.188:8099/conch/";
//    public static final String ROOT="http://10.28.124.116:8099/conch/";
//    public static final String ROOT="http://10.28.124.196:8099/conch/";
//    public static final String ROOT="http://10.28.124.195:8099/conch/";//田调试用4.26

    //Appkey
    public static final String APPKEY="5ad6ae01f29d981e84000090";
    //Umeng Message Secret
    public static final String MessageSecret="e520eb3804e7fb231c773620b5a478ad";
    //登录
    public static final String PUSH_TAG = "infoPush/isRecesived";


    //登录
    public static final String LOGIN = "login";
    //忘记密码
    public static final String FORGET_PASSWORD="";
    //获取验证码
    public static final String GET_CODE="";
    //信息列表界面 获取信息列表数据
    public static  final String GET_MESSAGE_LIST="info/getSpwsYHs";

    //信息列表界面 信息详情
    public static  final String GET_MESSGAE_DETAIL_INFO="info/getSpwsYH";

    //信息列表搜索
    public static  final String SEARCH_MESSGAE_INFO="info/searchInfos";

    //信息列表界面 违章隐患安全信息查询
    public static  final String QUERY_MESSGAE_INFO="info/getSpwsYH";
    //获取所有设备
    public static final String GET_DEVICES = "device/currentExamine";
    //查询设备说明信息
    public static final String CHECK_DEVICE_DESCRIPTION="device/searchDescription";
    //扫面之后根据信息来获取设备信息
    public static final String CHECK_DEVICE="device/currentExamine";
    //查询安全信息列表数据
    public static final String GET_CONCH_NEWS="info/getConchNews";
    //（MessageFragment）信息详情界面
    public static final String GET_MESSAGE_DETAIL_INFO="home/list";
    //获取安全信息列表（隐患违章我的）
    public static final String GET_SECURITY_DATAS="info/conditionsearch";

    //违章界面 添加违章信息
    public static final String ADD_WZ_MESSAGE="safety/addBreakRule";

    //违章界面 添加隐患信息
    public static final String ADD_YH_MESSAGE="safety/addHiddenDanger";
    //违章界面 获取信息列表数据
    public static final String GET_SAFE_MESSAGE_LIST="safety/getMyYHs";
    //（作业界面）获取作业信息
    public static final String GET_WORK_INFO="job/getJobType";
    //获取作业列表
    public static final String GET_WORK_TYPE_INFO="job/queryJob";
    //作业详细信息查询
    public static final String GET_WORK_DETAIL_INFO="job/getJob";
    //新建作业
    public static final String ADD_WORK="job/createJob ";
    //修改作业
    public static final String FIX_WORK="job/modifyJob";
    //撤销作业
    public static final String CANCEL_JOB="job/cancelJob";
    //提交作业
    public static final String SUBMIT_JOB="job/submitJob";
    //查询当前用户信息
    public static final String GET_MY_INFO="info/getMyInfo";
    //意见反馈
    public static final String ADD_FEEDBACK="info/addFeedBack";

    //检维界面查询所有设备
    public static final String GET_ALL_DEVICES_INFO="device/totalExamine";
    //检维界面条件查询设备
    public static final String GET_CONDITION_DEVICE_INFO="device/conditionSearch";
    //检维界面查询一条设备
    public static final String GET_ONE_DEVICE_INFO="device/searchOneEquip";
    //检维界面查看描述详情
    public static final String GET_DEVICE_DESCRIPTION="device/searchDescription";
    //更改密码
    public static final String CHANGE_PWD="updatePassword";
    //上传照片
    public static final String UPLOAD_IMAGE="upload/img";
    //查询天气
    public static final String QUERY_WEATHER="url:/getWeather";
    //搜素用户
    public static final String SEARCH_USER_ORG_LIST="job/getOrgUserList";

    // 获取作业设备类型
    public static final String GET_EQUPMENT_TYPE="device/getType";
    // 通过设备类型获取设备信息
    public static final String GET_EQUPMENT_INFO_BY_TYPE="device/totalExamine ";
    //危险作业类别
    public static final String DANGER_WORK_TYPE="dictionaries/WXZYLB";
    //设备类型
    public static final String DEVICE_TYPE="device/getType";
    //隐患界面 发现单位
    public static final String FIND_COMPANY="safety/searchParam";

    //隐患界面 发现单位继续递归
    public static final String FIND_COMPANY_NEXT="safety/searchDepartments";

    //天气
    public static final String WEATHER = "http://wthrcdn.etouch.cn/weather_mini";
}
