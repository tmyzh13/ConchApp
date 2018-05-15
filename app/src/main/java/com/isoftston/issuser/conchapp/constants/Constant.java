package com.isoftston.issuser.conchapp.constants;

/**
 * 一些用于记录的关键字
 * Created by john on 2017/11/7.
 */

public class Constant {




    //记住密码
    public static final String REMEMBER="remember";
    public static final String LOGIN_PWD="login_pwd";
    //是否登录过了
    public static final String LOGIN_STATUE="login_staue";
    //检修人员
    public static final String CHECK_PEOPLE="check_people";
    public static final String CHECK_PEOPLE_ID="check_people_id";

    public static final String CHECK_DEVICE_TYPE="check_type";

    public static final String CHECK_DEVICE_ID="check_id";
    //临时图片集合
    public static final String TEMP_PIC_LIST="temp_pic_list";

    public static final String ORG_ID="ORG_ID";
    public static final String ORG_NAME="ORG_NAME";

    public static final String AREA_ID="AREA_ID";
    public static final String LOCATION_NAME = "loaction_name";

    public static final String  MESSAGE_SEARCH = "message_search";
    public static final String  SAFE_SEARCH = "message_safe";
    public static final String  WORK_SEARCH = "work_search";
    public static final String  CHECK_SEARCH = "check_search";
    //发现单位
    public static final String FIND_COMPANY_ID="find_company_id";
    public static final String FIND_COMPANY_NAME="find_company_name";

    //隐患单位
    public static final String DANGER_COMPANY_ID="danger_company_id";
    public static final String DANGER_COMPANY_NAME="danger_company_name";
    public static final String  PUSH_MESSAGE = "push_message";
    public static final String PASSWORD_STYLE = "(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}";
}
