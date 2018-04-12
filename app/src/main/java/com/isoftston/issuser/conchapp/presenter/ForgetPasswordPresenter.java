package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.ForgetPasswordView;

/**
 * Created by issuser on 2018/4/12.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {
    @Override
    public void onStart() {

    }

    /**
     * 发送忘记密码请求
     */
    public void sendRequst(String email){
        if(Tools.validateEmail(email)){
            view.sendActionSuccess();
        }else{
            ToastMgr.show(getString(R.string.forget_password_email_error));
        }
    }


}
