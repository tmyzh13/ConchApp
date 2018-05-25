package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CompanyBean;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface CompanyApi {

    @POST(Urls.GET_COMPANY)
    Observable<BaseData<List<CompanyBean>>> findCompanyList(@Header("Access-Token") String token, @Body CompanyBean bean );
    @POST(Urls.UPDATE_COMPANY)
    Observable<BaseData> updateUserCompany(@Header("Access-Token")String token1,@Body UserInfoBean bean);
}
