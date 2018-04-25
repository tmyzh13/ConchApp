package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.UserApi;
import com.isoftston.issuser.conchapp.model.bean.AddFeedBackRequestBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.ChangePwdRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.UserRequestBean;
import com.isoftston.issuser.conchapp.utils.MD5Utils;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;

/**
 * Created by issuser on 2018/4/17.
 */

public class UserPresenter extends PagePresenter<UserView> {
    private UserApi api;
    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(UserApi.class);
    }

    /**
     * @param
     * @param
     */
   public void getUserInfo(){
       String token= SharePrefsUtils.getValue(getContext(),"token",null);
       Log.e("yzh","token--"+token);
       String token1=token.replaceAll("\"","");
       UserBean bean=new UserBean();
        api.getUserInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>() {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        view.getUserInfo(userInfoBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getUserInfoError();
                    }

                    @Override
                    public boolean operationError(BaseData<UserInfoBean> userInfoBeanBaseData, int status, String message) {
                        view.getUserInfoError();
                        return super.operationError(userInfoBeanBaseData, status, message);
                    }
                });
    }
    public void  addFeedBack(String content){
        AddFeedBackRequestBean bean=new AddFeedBackRequestBean();
        bean.content=content;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.addFeedBack(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData userInfoBeanBaseData) {
                        view.addFeedBackSuccess();
                    }


                });

    }
    public void updatePwd(String password){
        ChangePwdRequestBean bean=new ChangePwdRequestBean();
        bean.password= MD5Utils.encode(password);
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.changePwd(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData userInfoBeanBaseData) {
                        view.updatePwdSuccess();
                    }


                });
    }
}
