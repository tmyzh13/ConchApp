package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.apis.WorkDetailApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.DangerWorkTypeBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.RequestWorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
import com.isoftston.issuser.conchapp.model.bean.RevokeJobBody;
import com.isoftston.issuser.conchapp.model.bean.SubmitJobBody;
import com.isoftston.issuser.conchapp.model.bean.UploadWxzyspdRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailRequestBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListRequestBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkDetailView;

import retrofit2.http.Body;

/**
 * Created by issuser on 2018/4/17.
 */

public class WorkDetailPresenter extends BasePresenter<WorkDetailView> {
    private WorkDetailApi api;

    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(WorkDetailApi.class);
    }

    /**
     * 默认获取数据
     * @param jobId 作业id
     */
    public void getWorkDetailInfo(String jobId) {
        RequestWorkDetailBean bean = new RequestWorkDetailBean();
        bean.jobId = jobId;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        view.showLoading();
        api.getWorkDetailInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkDetailRequestBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkDetailRequestBean>>(view) {

                    @Override
                    public void success(BaseData<WorkDetailRequestBean> workDetailBeanBaseData) {
                        Log.e("dp","----success");
                        view.getWorkDetailInfo(workDetailBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.responseError(0);
                        super.onError(e);

                    }

//                    @Override
//                    public boolean operationError(BaseData<WorkDetailRequestBean> workDetailBeanBaseData, int status, String message) {
//                        view.responseError(0);
//                        return super.operationError(workDetailBeanBaseData, status, message);
//                    }
                });
    }

    /**
     * 撤销
     * @param jobId
     */
    public void revokeJob( String jobId){
        RevokeJobBody body = new RevokeJobBody();
        body.jobId = jobId;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.cancelJob(token1,body)
                .compose(new ResponseTransformer<BaseData<ResponseDataBean>>(this.<BaseData<ResponseDataBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<ResponseDataBean>>(view) {
                    @Override
                    public void success(BaseData<ResponseDataBean> responseDataBeanBaseData) {
                        view.revokeJob(responseDataBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.responseError(1);
                    }
                });
    }

    /**
     *
     * @param
     * @param
     * @param  ，以逗号分隔开的
     */
    public void submitJob(SubmitJobBody bean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
       api.submitJob(token1,bean)
               .compose(new ResponseTransformer<BaseData<ResponseDataBean>>(this.<BaseData<ResponseDataBean>>bindToLifeCycle()))
               .subscribe(new ResponseSubscriber<BaseData<ResponseDataBean>>(view) {
                   @Override
                   public void success(BaseData<ResponseDataBean> responseDataBeanBaseData) {
                       view.submitJob(responseDataBeanBaseData.data);
                   }

                   @Override
                   public void onError(Throwable e) {
                       super.onError(e);
                       view.responseError(2);
                   }
               });
    }

    public void getUserInfo(){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        UserBean bean=new UserBean();
        view.showLoading();
        api.getUserInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>(view) {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        view.getUserInfo(userInfoBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public boolean operationError(BaseData<UserInfoBean> userInfoBeanBaseData, int status, String message) {
                        return super.operationError(userInfoBeanBaseData, status, message);
                    }
                });
    }

    public void getWorkInfo() {
        WorkListRequestBean bean=new WorkListRequestBean();
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.getWorkInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkListBean>>(view) {
                    @Override
                    public void success(BaseData<WorkListBean> workBeanBaseData) {
                        if (workBeanBaseData.data.list.size() == 0){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getWorkListInfo(workBeanBaseData.data.list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                });
    }

    public void uploadWxzyspd(UploadWxzyspdRequestBean param) {
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.uploadWxzyspd(token1,param)
            .compose(new ResponseTransformer<>(this.<BaseData<ResponseDataBean>>bindToLifeCycle()))
            .subscribe(new ResponseSubscriber<BaseData<ResponseDataBean>>(view) {
                @Override
                public void success(BaseData<ResponseDataBean> responseDataBeanBaseData) {
                    view.uploadWxzyspdSuccess(responseDataBeanBaseData.data);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.responseError(3);
                }
            });
    }

}
