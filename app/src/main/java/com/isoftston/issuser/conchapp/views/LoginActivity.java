package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.MainActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.presenter.LoginPresenter;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.litepal.crud.callback.SaveCallback;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView {


    @Bind(R.id.et_account)
    EditText et_login;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.view_statue)
    View view_statue;
    @Bind(R.id.tv_language)
    TextView tv_language;

    private Context context=LoginActivity.this;

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
        ViewGroup.LayoutParams lp =view_statue.getLayoutParams();
        lp.height= Tools.getStatueHeight(context);
        view_statue.setLayoutParams(lp);

        setBarColor(getResources().getColor(R.color.transparent_black));
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
        startActivity(MainActivity.getLauncher(context));
    }

    @OnClick(R.id.tv_language)
    public void changeLanguage(){
        //中英文切换
    }

    @OnClick(R.id.tv_find_password)
    public void findPassword(){
        //进入找回密码界面
        startActivity(ForgetPasswordActivity.getLaucner(context));
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //登录页面的返回直接推出app
            AppManager.getAppManager().appExit();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
