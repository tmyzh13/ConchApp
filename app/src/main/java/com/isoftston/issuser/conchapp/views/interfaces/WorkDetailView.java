package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailRequestBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/17.
 */

public interface WorkDetailView extends BasePaginationView {
    void renderData(WorkDetailBean workDetailBean);

    void getWorkDetailInfo(WorkDetailRequestBean workDetailRequestBean);

    void responseError(int type);
    void revokeJob(ResponseDataBean responseDataBean);
    void submitJob(ResponseDataBean responseDataBean);

    void getUserInfo(UserInfoBean userInfoBean);
}
