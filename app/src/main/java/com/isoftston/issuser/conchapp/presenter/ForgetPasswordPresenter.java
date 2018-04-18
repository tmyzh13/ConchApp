package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.apis.ForgetPasswordApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.ForgetPasswordRequstBean;
import com.isoftston.issuser.conchapp.utils.MD5Utils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.ForgetPasswordView;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/4/12.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {

    private ForgetPasswordApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ForgetPasswordApi.class);
    }

    @Override
    public void onStart() {

    }

    /**
     * 发送忘记密码请求
     */
    public void sendRequst(String email,String code,String password){
        if(Tools.validateEmail(email)){
            view.showLoading();
            ForgetPasswordRequstBean bean=new ForgetPasswordRequstBean();
            bean.code=code;
            bean.phone=email;
            bean.password= MD5Utils.encode(password);
            bean.language=Tools.getLocalLanguage(getContext());
            api.forgetPassword(bean)
                    .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                    .subscribe(new ResponseSubscriber<BaseData>(view) {
                        @Override
                        public void success(BaseData baseData) {
                            view.sendActionSuccess();
                        }
                    });
        }else{
            ToastMgr.show(getString(R.string.forget_password_email_error));
        }
    }


}
