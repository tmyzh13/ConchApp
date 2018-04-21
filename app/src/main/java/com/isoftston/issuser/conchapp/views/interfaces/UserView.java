package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;

/**
 * Created by issuser on 2018/4/17.
 */

public interface UserView extends BasePaginationView {
    void getUserInfo(UserInfoBean userInfoBean);

    void getUserInfoError();

    void addFeedBackSuccess();
}
