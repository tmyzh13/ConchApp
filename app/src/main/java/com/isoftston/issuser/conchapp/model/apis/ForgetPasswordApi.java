package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.ForgetPasswordRequstBean;
import com.isoftston.issuser.conchapp.model.bean.GetCodeBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/16.
 */

public interface ForgetPasswordApi {

    @POST(Urls.FORGET_PASSWORD)
    Observable<BaseData> forgetPassword(@Body ForgetPasswordRequstBean bean);

    @POST(Urls.GET_CODE)
    Observable<BaseData> getCode(@Body GetCodeBean bean);
}
