package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;

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
}
