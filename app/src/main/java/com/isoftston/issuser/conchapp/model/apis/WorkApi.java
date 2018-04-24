package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.DangerWorkTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceNameBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceNameCodeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeRequstBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.NewWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.WorkTypeRequestBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/18.
 */

public interface WorkApi {
    //获取作业信息
    @POST(Urls.GET_WORK_INFO)
    Observable<BaseData<WorkListBean>> getWorkInfo(@Header("Access-Token") String token,@Body WorkListRequestBean bean);

    @POST(Urls.GET_WORK_TYPE_INFO)
    Observable<BaseData<WorkListBean>> getWorkTypeInfo(@Body WorkTypeRequestBean bean);

    @POST(Urls.ADD_WORK)
    Observable<BaseData> addWork(@Header("Access-Token") String token,@Body NewWorkBean bean);

    @POST(Urls.FIX_WORK)
    Observable<BaseData> fixWork(@Header("Access-Token") String token,@Body FixWorkBean bean);

    @POST(Urls.DANGER_WORK_TYPE)
    Observable<BaseData<DangerWorkTypeBean>> dangerWorkType(@Header("Access-Token") String token, @Body FixWorkBean bean);

    @POST(Urls.DEVICE_TYPE)
    Observable<BaseData<DeviceTypeRequstBean>> deviceType(@Header("Access-Token") String token, @Body FixWorkBean bean);

    @POST(Urls.GET_EQUPMENT_INFO_BY_TYPE)
    Observable<BaseData<DeviceNameCodeBean>> deviceName(@Header("Access-Token") String token, @Body DeviceNameBean bean);
}
