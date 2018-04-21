package com.isoftston.issuser.conchapp.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.apis.LoginApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CodeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.ForgetPwdRequestBean;
import com.isoftston.issuser.conchapp.model.bean.LoginRequestBean;
import com.isoftston.issuser.conchapp.utils.MD5Utils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/4/9.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    LoginApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(LoginApi.class);
    }

    @Override
    public void onStart() {

    }

    public void loginAction(String account,String password){
        if(checkLoginInput(account,password)){
            LoginRequestBean bean =new LoginRequestBean();
//            bean.language= Tools.getLocalLanguage(getContext());
            bean.userName=account;
            bean.password= password;
            bean.phoneType=Tools.getPhoneType();
            bean.phoneCode=Tools.getIMEI(getContext());
            api.login(bean)
                    .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                    .subscribe(new ResponseSubscriber<BaseData>(view) {
                        @Override
                        public void success(BaseData baseData) {
                            PreferencesHelper.saveData(Constant.LOGIN_STATUE,"1");
                            view.loginSuccess((String) baseData.data);
                        }
                    });

        }
    }
    public void forgetPwd(String phonenum,String yzm,String newpwd){
        ForgetPwdRequestBean bean=new ForgetPwdRequestBean();
        bean.phoneNume=phonenum;
        bean.yzm=yzm;
        bean.newpwd=newpwd;
        api.forgetPwd(bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.changePwdSuccess();
                    }
                });
    }
    public void getCode(String content){
        CodeRequestBean bean=new CodeRequestBean();
        bean.phonenum=content;
        api.getCode(bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.getCodeSuccess();
                    }
                });
    }

    private boolean checkLoginInput(String account,String password){
        if(TextUtils.isEmpty(account)){
            ToastMgr.show(getContext().getString(R.string.login_account_empty));
            return false;
        }

        if(TextUtils.isEmpty(password)){
            ToastMgr.show(getContext().getString(R.string.login_password_empty));
            return false;
        }

        return true;
    }
}
