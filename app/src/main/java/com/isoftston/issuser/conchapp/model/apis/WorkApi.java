package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/18.
 */

public interface WorkApi {
    //查询作业详细信息
    @POST(Urls.GET_WORK_INFO)
    Observable<BaseData<WorkBean>> getWorkInfo(@Query("language") String language);
}
