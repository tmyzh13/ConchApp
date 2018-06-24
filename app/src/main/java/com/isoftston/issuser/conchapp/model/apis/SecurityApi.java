package com.isoftston.issuser.conchapp.model.apis;

import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.AddYHBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestOrgBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.UpdateZgtpBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;

import java.io.File;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/20.
 */

public interface SecurityApi {
    @POST(Urls.ADD_WZ_MESSAGE)
    Observable<BaseData> addWZMessage(@Header("Access-Token") String token,@Body AddYHBean bean );
    @POST(Urls.ADD_YH_MESSAGE)
    Observable<BaseData> addYHMessage(@Header("Access-Token") String token,@Body AddYHBean bean );
    @POST(Urls.GET_SAFE_MESSAGE_LIST)
    Observable<BaseData<SafeListBean>> getSefeMessageList(@Header("Access-Token") String token, @Body SafeRequestBean bean );
    @POST(Urls.UPLOAD_IMAGE)
    Observable<BaseData> uploadImage(@Header("Access-Token") String token, @Body File file );
    @POST(Urls.FIND_COMPANY)
    Observable<BaseData<SecuritySearchBean>> findCompanyList(@Header("Access-Token") String token, @Body SafeRequestBean bean);
    @POST(Urls.FIND_COMPANY_NEXT)
    Observable<BaseData<List<OrgBean>>> findCompanyListNext(@Header("Access-Token") String token, @Body SafeRequestOrgBean bean);
    //获取用户信息
    @POST(Urls.GET_MY_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Header("Access-Token") String token, @Body UserBean bean);
    @POST(Urls.CHECK_UPLOAD_IMG)
    Observable<BaseData> checkUpload(@Header("Access-Token")String token1, @Body UpdateZgtpBean updateZgtpBean);
}
