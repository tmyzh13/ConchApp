package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.NewWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.WorkTypeRequestBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/18.
 */

public interface WorkApi {
    //获取作业信息
    @POST(Urls.GET_WORK_INFO)
    Observable<BaseData<WorkListBean>> getWorkInfo(@Body WorkListRequestBean bean);

    @POST(Urls.GET_WORK_TYPE_INFO)
    Observable<BaseData<WorkListBean>> getWorkTypeInfo(@Body WorkTypeRequestBean bean);

    @POST(Urls.ADD_WORK)
    Observable<BaseData> addWork(@Body NewWorkBean bean);

    @POST(Urls.FIX_WORK)
    Observable<BaseData> fixWork(@Body FixWorkBean bean);

}
