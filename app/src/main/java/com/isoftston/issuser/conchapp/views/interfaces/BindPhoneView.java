package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BaseView;

public interface BindPhoneView extends BaseView {
    void sendValidNumSuccess();

    void sendErrorMessage(String message);

    void bindSuccess();
}
