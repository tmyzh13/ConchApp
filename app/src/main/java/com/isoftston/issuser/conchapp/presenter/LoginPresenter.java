package com.isoftston.issuser.conchapp.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;

/**
 * Created by issuser on 2018/4/9.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    @Override
    public void onStart() {

    }

    public void loginAction(String account,String password){
        if(checkLoginInput(account,password)){

            PreferencesHelper.saveData(Constant.LOGIN_STATUE,"1");
            view.loginSuccess();
        }
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
