package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.AirResponseBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.QueryMessageRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WeatherResponseBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by issuser on 2018/4/20.
 */

public interface MessageApi {
    //@Header("Set-Cookie") String cookie,
    @POST(Urls.GET_MESSAGE_LIST)
    Observable<BaseData<MessageListInfoBean>> getMessageListInfo(@Header("Access-Token") String token, @Body MessageListRequestBean bean);

    //@Header("Set-Cookie") String cookie,
    @POST(Urls.GET_MESSAGE_LIST)
    Observable<BaseData<EachMessageInfoBean>> getEachMessageListInfo(@Header("Access-Token") String token, @Body MessageListRequestBean bean);

    @POST(Urls.GET_MESSGAE_DETAIL_INFO)
    Observable<BaseData<MessageDetailBean>> getMessageDetailInfo(@Header("Access-Token") String token,@Body MessageDetailRequestBean bean);

    @POST(Urls.QUERY_MESSGAE_INFO)
    Observable<BaseData<MessageQueryBean>> queryMessageInfo(@Header("Access-Token") String token,@Body QueryMessageRequestBean bean);

    //获取用户信息
    @POST(Urls.GET_MY_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Header("Access-Token") String token, @Body UserBean bean);

    //获取天气
    @GET(Urls.WEATHER)
    Observable<WeatherResponseBean> getWeatherInfo(@Query("id") String cityCode);

    //获取天气质量
    @GET(Urls.AIR)
    Observable<AirResponseBean> getAirInfo(@Query("id") String cityEnName);
}
