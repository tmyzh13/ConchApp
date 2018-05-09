package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;

/**
 * Created by issuser on 2018/4/9.
 */

public interface LoginView extends BaseView {

    void loginSuccess(String data);
    void changePwdSuccess();
    void getCodeSuccess();
    void returnTag(boolean isSuccess,String tag);
    void getSafeChoiceList(SecuritySearchBean bean);
    void getServerVersionCode(String serverCode);
}
