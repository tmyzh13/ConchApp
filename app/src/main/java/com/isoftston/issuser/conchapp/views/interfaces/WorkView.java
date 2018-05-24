package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DangerWorkTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeRequstBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkCountBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/18.
 */

public interface WorkView extends BasePaginationView {
    void renderData(WorkBean workBean);

    void getWorkListInfo(List<WorkBean> list);

    void getWorkList(List<WorkDetailBean> list);

    void getWorkError();

    void addWorkSuccess();

    void getDangerWorkTypeResult(List<DangerTypeBean> list);

    void getDeviceTypeResult(List<DeviceTypeBean> list);

    void getDeviceDetailSuccess(List<DeviceDetailBean> list);

    void getWorkCountSuccess(List<WorkCountBean> list);
}
