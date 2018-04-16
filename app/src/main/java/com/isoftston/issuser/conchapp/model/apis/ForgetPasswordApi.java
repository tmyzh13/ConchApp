package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/16.
 */

public interface ForgetPasswordApi {

    @POST(Urls.FORGET_PASSWORD)
    Observable<BaseData> forgetPassword(@Query("account") String account);
}
