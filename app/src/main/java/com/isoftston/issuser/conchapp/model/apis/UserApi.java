package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.AddFeedBackRequestBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.ChangePwdRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.UserRequestBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/17.
 */

public interface UserApi {
    //获取用户信息
    @POST(Urls.GET_MY_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Header("Access-Token") String token,@Body UserBean bean);
    //意见反馈
    @POST(Urls.ADD_FEEDBACK)
    Observable<BaseData> addFeedBack(@Header("Access-Token") String token,@Body AddFeedBackRequestBean bean);

    @POST(Urls.CHANGE_PWD)
    Observable<BaseData> changePwd(@Header("Access-Token") String token,@Body ChangePwdRequestBean bean);

}
