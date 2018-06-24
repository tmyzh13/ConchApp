package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/17.
 */

public interface ConchNewsApi {
    @POST(Urls.GET_CONCH_NEWS)
    Observable<BaseData> getConchNews(@Query("language") String language, @Query("pageNo") String pageNo);
}
