package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListsBean;
import com.isoftston.issuser.conchapp.model.bean.WorkTypeRequestBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/24.
 */

public interface SearchUserApi {
    @POST(Urls.SEARCH_USER_ORG_LIST)
    Observable<BaseData<ResponseUserBean>> searchUser(@Header("Access-Token") String token, @Body SearchUserBean bean);

    @POST(Urls.SEARCH_MESSGAE_INFO)
    Observable<BaseData<EachMessageInfoBean>> searchMessageListInfo(@Header("Access-Token") String token, @Body MessageQueryBean bean);

    @POST(Urls.GET_SECURITY_SEACHER)
    Observable<BaseData<SafeListBean>> searchSafeMessageListInfo(@Header("Access-Token") String token, @Body SafeRequestBean bean);

    @POST(Urls.FIX_WORK_SEACHER)
    Observable<BaseData<WorkListsBean>> searchWorkMessage(@Header("Access-Token") String token, @Body WorkTypeRequestBean bean);

    @POST(Urls.GET_CONDITION_DEVICE_INFO)
    Observable<BaseData<DeviceListBean>> searchDeviceMessage(@Header("Access-Token") String token, @Body SafeRequestBean bean);

}
