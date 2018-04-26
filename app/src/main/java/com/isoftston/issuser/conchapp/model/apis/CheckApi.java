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
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/16.
 */

public interface CheckApi {

    @POST(Urls.GET_DEVICES)
    Observable<BaseData<DeviceListBean>> getDevices(@Header("Access-Token") String token,@Query("userId") String userID,@Query("pageNo") String pageNo);

    @POST(Urls.CHECK_DEVICE)
    Observable<BaseData<DeviceBean>> checkDevices(@Header("Access-Token") String token,@Body CheckDeviceRequestBean bean);

    @POST(Urls.CHECK_DEVICE_DESCRIPTION)
    Observable<BaseData<DeviceBean>> getDeviceInfo(@Body CheckBean bean);

    @POST(Urls.GET_ALL_DEVICES_INFO)
    Observable<BaseData<DeviceListBean>> getAllDeviceInfo(@Header("Access-Token") String token, @Body CheckAllDeviceBean bean );

    @POST(Urls.GET_CONDITION_DEVICE_INFO)
    Observable<BaseData<DeviceListBean>> getConditionDeviceInfo(@Body CheckConditionDeviceBean bean );

    @POST(Urls.GET_ONE_DEVICE_INFO)
    Observable<BaseData<DeviceBean>> getOneDeviceInfo(@Header("Access-Token") String token,@Body CheckOneDeviceBean bean );

    //获取用户信息
    @POST(Urls.GET_MY_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Header("Access-Token") String token, @Body UserBean bean);

}
