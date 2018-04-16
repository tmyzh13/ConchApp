package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.corelibs.base.BaseView;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/16.
 */

public interface CheckView extends BasePaginationView {

    void renderDatas(boolean reload,List<DeviceBean> list);

    void checkDeviceResult(DeviceBean bean);
    void checkDeviceResultError();
}
