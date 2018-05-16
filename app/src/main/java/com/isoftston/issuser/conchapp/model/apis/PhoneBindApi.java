package com.isoftston.issuser.conchapp.model.apis;

import com.google.gson.JsonObject;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.BindPhoneBean;
import com.isoftston.issuser.conchapp.model.bean.GetCodeBean;
import com.isoftston.issuser.conchapp.model.bean.LoginRequestBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface PhoneBindApi {
    //绑定手机号
    @POST(Urls.BIND_PHONE)
    Observable<BaseData<JsonObject>> bindPhone(@Header("Access-Token") String token, @Body BindPhoneBean bean);

    @POST(Urls.GET_CODE)
    Observable<BaseData> getCode(@Header("Access-Token") String token, @Body GetCodeBean bean);
}
