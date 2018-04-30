package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.corelibs.base.BaseView;
import com.isoftston.issuser.conchapp.model.bean.CheckAllDevicesBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/16.
 */

public interface CheckView extends BasePaginationView {

    void renderDatas(boolean reload,List<DeviceBean> list);

    void checkDeviceResult(DeviceBean bean);

    void checkDeviceResultError();

    void CheckAllDeviceResult(List<DeviceBean> deviceListBean,String total);

    void setUserInfo(UserInfoBean userInfo);

    void setDescription(String description);

    void reLogin();
}
