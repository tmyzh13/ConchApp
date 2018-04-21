package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.AddWZMessageRequestBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/20.
 */

public interface SecurityApi {
    @POST(Urls.ADD_WZ_MESSAGE)
    Observable<BaseData> addWZMessage(@Body AddWZMessageRequestBean bean );
    @POST(Urls.ADD_YH_MESSAGE)
    Observable<BaseData> addYHMessage(@Body AddWZMessageRequestBean bean );
    @GET(Urls.GET_SAFE_MESSAGE_LIST)
    Observable<BaseData<SafeListBean>> getSefeMessageList(@Body SafeRequestBean bean );
}
