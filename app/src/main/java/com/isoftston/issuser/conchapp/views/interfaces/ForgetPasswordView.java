package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/4/12.
 */

public interface ForgetPasswordView extends BaseView {

    void sendActionSuccess();

    void sendValidNumSuccess();

    void sendErrorMessage(String msg);
}
