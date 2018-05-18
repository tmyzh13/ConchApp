package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.google.gson.JsonObject;
import com.isoftston.issuser.conchapp.model.apis.PhoneBindApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.BindPhoneBean;
import com.isoftston.issuser.conchapp.model.bean.GetCodeBean;
import com.isoftston.issuser.conchapp.model.bean.LoginRequestBean;
import com.isoftston.issuser.conchapp.utils.MD5Utils;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.BindPhoneView;
import com.trello.rxlifecycle.ActivityEvent;

public class BindPhonePresenter extends BasePresenter<BindPhoneView> {

    PhoneBindApi api;

    private String TAG = BindPhonePresenter.class.getSimpleName();

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(PhoneBindApi.class);
    }

    @Override
    public void onStart() {

    }


    public void bindPhone(String account, final String password) {
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        BindPhoneBean bean = new BindPhoneBean();
        bean.setPhone(account);
        bean.setCode(password);
        view.showLoading();
        api.bindPhone(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.hideLoading();
                        if(baseData.code == 1){
                            view.bindSuccess();
                        }else {
                            view.sendErrorMessage(baseData.mess);
                        }
                    }
                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        view.hideLoading();
                        if (status==0){
                            view.sendErrorMessage(message);
                        }
                        return super.operationError(baseData, status, message);
                    }
                });
    }

    /**
     * 绑定手机发送验证码
     *
     * @param phone
     */
    public void sendValidNum(String phone) {
        GetCodeBean bean = new GetCodeBean();
        bean.phone = phone;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.getValidNum(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.sendValidNumSuccess();
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        if (status==0){
                            view.sendErrorMessage(message);
                        }
                        return super.operationError(baseData, status, message);
                    }
                });
    }

    /**
     * 更换新手机发送验证码
     *
     * @param phone
     */
    public void getCode(String phone) {
        GetCodeBean bean = new GetCodeBean();
        bean.phone = phone;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.getCode(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.sendValidNumSuccess();
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        if (status==0){
                            view.sendErrorMessage(message);
                        }
                        return super.operationError(baseData, status, message);
                    }
                });
    }

    public void changeBindPhone(String account, final String password) {
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        BindPhoneBean bean = new BindPhoneBean();
        bean.setPhone(account);
        bean.setCode(password);
        bean.setPhoneCode(Tools.getIMEI(getContext()));
        view.showLoading();
        api.changeBindPhone(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.hideLoading();
                        if(baseData.code == 1){
                            view.bindSuccess();
                        }else {
                            view.sendErrorMessage(baseData.mess);
                        }
                    }
                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        view.hideLoading();
                        if (status==0){
                            view.sendErrorMessage(message);
                        }
                        return super.operationError(baseData, status, message);
                    }
                });
    }
}
