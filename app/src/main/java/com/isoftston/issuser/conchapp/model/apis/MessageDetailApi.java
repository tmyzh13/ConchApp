package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/18.
 */

public interface MessageDetailApi {
    //查询作业详细信息
    @POST(Urls.GET_MESSAGE_DETAIL_INFO)
    Observable<BaseData<MessageDetailBean>> getMessageDetailInfo(@Query("language") String language,
                                                                 @Query("token")String token,
                                                                 @Query("type")String type,
                                                                 @Query("id")String id);
}
