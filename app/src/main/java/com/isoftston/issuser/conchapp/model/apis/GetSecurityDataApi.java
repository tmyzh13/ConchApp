package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.MessageListBean;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/17.
 */

public interface GetSecurityDataApi {

    @POST(Urls.GET_SECURITY_DATAS)
    Observable<BaseData<MessageListBean>> getSecurityData(@Query("language") String language,
                                                          @Query("yhlx") String yhlx,
                                                          @Query("condition") String condition,
                                                          @Query("pageNo") int pageNo);
}
