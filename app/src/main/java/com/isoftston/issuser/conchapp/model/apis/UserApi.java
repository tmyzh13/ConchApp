package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.AddFeedBackRequestBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.UserRequestBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/17.
 */

public interface UserApi {
    //获取用户信息
    @POST(Urls.GET_MY_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Body UserRequestBean bean);
    //意见反馈
    @POST(Urls.ADD_FEEDBACK)
    Observable<BaseData> addFeedBack(@Body AddFeedBackRequestBean bean);

}
