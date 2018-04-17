package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;


import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/17.
 */

public interface WorkDetailApi {

    //查询作业详细信息
    @POST(Urls.GET_WORK_DETAIL_INFO)
    Observable<BaseData<WorkDetailBean>> getWorkDetailInfo(@Query("language") String language, @Query("id") long id);

    //撤销作业
    @POST(Urls.CANCEL_JOB)
    Observable<BaseData<WorkDetailBean>> cancelJob(@Query("id") long id);

    //提交作业
    @POST(Urls.SUBMIT_JOB)
    Observable<BaseData<WorkDetailBean>> submitJob(@Query("id") long id, @Query("code") String cod, @Query("imgs") String imgs);
}