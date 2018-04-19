package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CheckAllDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.CheckAllDevicesBean;
import com.isoftston.issuser.conchapp.model.bean.CheckBean;
import com.isoftston.issuser.conchapp.model.bean.CheckConditionDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.CheckDeviceRequestBean;
import com.isoftston.issuser.conchapp.model.bean.CheckOneDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/16.
 */

public interface CheckApi {

    @POST(Urls.GET_DEVICES)
    Observable<BaseData<DeviceListBean>> getDevices(@Query("userId") String userID,@Query("pageNo") String pageNo);

    @POST(Urls.CHECK_DEVICE)
    Observable<BaseData<DeviceBean>> checkDevices(@Body CheckDeviceRequestBean bean);
//{"userid":"1","pageNo":"1","itemId":""}
    @POST(Urls.CHECK_DEVICE_DESCRIPTION)
    Observable<BaseData<DeviceBean>> getDeviceInfo(@Body CheckBean bean);

    @POST(Urls.GET_ALL_DEVICES_INFO)
    Observable<BaseData<DeviceListBean>> getAllDeviceInfo(@Body CheckAllDeviceBean bean );

    @POST(Urls.GET_CONDITION_DEVICE_INFO)
    Observable<BaseData<DeviceListBean>> getConditionDeviceInfo(@Body CheckConditionDeviceBean bean );

    @POST(Urls.GET_ONE_DEVICE_INFO)
    Observable<BaseData<DeviceBean>> getOneDeviceInfo(@Body CheckOneDeviceBean bean );

}
