package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.QueryMessageRequestBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/20.
 */

public interface MessageApi {
    @POST(Urls.GET_MESSAGE_LIST)
    Observable<BaseData<MessageListInfoBean>> getMessageListInfo(@Body MessageListRequestBean bean );

    @POST(Urls.GET_MESSGAE_DETAIL_INFO)
    Observable<BaseData<MessageDetailBean>> getMessageDetailInfo(@Body MessageDetailRequestBean bean);

    @POST(Urls.QUERY_MESSGAE_INFO)
    Observable<BaseData<MessageQueryBean>> queryMessageInfo(@Body QueryMessageRequestBean bean);
}
