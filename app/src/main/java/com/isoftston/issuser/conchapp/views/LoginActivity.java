package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.presenter.LoginPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.et_account)
    EditText et_login;
    @Bind(R.id.et_password)
    EditText et_password;


    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,LoginActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.colorPrimary);

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick(R.id.tv_login)
    public void loginAction(){
        presenter.loginAction(et_login.getText().toString().trim(),et_password.getText().toString().trim());
    }

    @Override
    public void loginSuccess() {

    }
}
