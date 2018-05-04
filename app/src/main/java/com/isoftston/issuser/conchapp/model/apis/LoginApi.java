package com.isoftston.issuser.conchapp.model.apis;

import com.google.gson.JsonObject;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CodeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.ForgetPwdRequestBean;
import com.isoftston.issuser.conchapp.model.bean.LoginRequestBean;
import com.isoftston.issuser.conchapp.model.bean.PushBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/16.
 */

public interface LoginApi {
    //登录
    @POST(Urls.LOGIN)
    Observable<BaseData<JsonObject>> login(@Body LoginRequestBean bean);
    //忘记密码
    @POST(Urls.FORGET_PASSWORD)
    Observable<BaseData> forgetPwd(@Body ForgetPwdRequestBean bean);
    //获取验证码
    @POST(Urls.GET_CODE)
    Observable<BaseData> getCode(@Body CodeRequestBean bean);
    @POST(Urls.PUSH_TAG)
    Observable<BaseData<PushBean>> getPushTag(@Header("Access-Token") String token1);

    @POST(Urls.FIND_COMPANY)
    Observable<BaseData<SecuritySearchBean>> findCompanyList(@Header("Access-Token") String token, @Body SafeRequestBean bean);

}
