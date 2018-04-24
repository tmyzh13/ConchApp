package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.RequestWorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
import com.isoftston.issuser.conchapp.model.bean.RevokeJobBody;
import com.isoftston.issuser.conchapp.model.bean.SubmitJobBody;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailRequestBean;


import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/17.
 */

public interface WorkDetailApi {

    //查询作业详细信息
    @POST(Urls.GET_WORK_DETAIL_INFO)
    Observable<BaseData<WorkDetailRequestBean>> getWorkDetailInfo(@Header("Access-Token") String token, @Body RequestWorkDetailBean bean);

    //撤销作业
    @POST(Urls.CANCEL_JOB)
    Observable<BaseData<ResponseDataBean>> cancelJob(@Header("Access-Token") String token, @Body RevokeJobBody body);

    //提交作业
    @POST(Urls.SUBMIT_JOB)
    Observable<BaseData<ResponseDataBean>> submitJob(@Header("Access-Token") String token, @Body SubmitJobBody submitJobBody);

    @POST(Urls.GET_MY_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Header("Access-Token") String token, @Body UserBean bean);
}