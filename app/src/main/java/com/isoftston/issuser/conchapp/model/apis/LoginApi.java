package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/16.
 */

public interface LoginApi {

    @POST(Urls.LOGIN)
    Observable<BaseData> login(@Query("accout") String account, @Query("password") String password);
}
