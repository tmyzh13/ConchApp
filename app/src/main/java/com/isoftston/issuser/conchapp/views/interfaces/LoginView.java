package com.isoftston.issuser.conchapp.views.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.corelibs.base.BaseView;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;

/**
 * Created by issuser on 2018/4/9.
 */

public interface LoginView extends BaseView {

    void loginSuccess(String data);
    void changePwdSuccess();
    void getCodeSuccess();
    void returnTag(String isSuccess,String tag);
    void getSafeChoiceList(SecuritySearchBean bean);
    void getServerVersionCode(String serverCode);

    void loginSuccessEx(Boolean newPhone, Boolean phoneIsNull, String phone, JSONObject jsonObject);
}
