package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/4/9.
 */

public interface LoginView extends BaseView {

    void loginSuccess(String data);
    void changePwdSuccess();
    void getCodeSuccess();
}
