package com.isoftston.issuser.conchapp.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.common.AppManager;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.MainActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.LoginUserBean;
import com.isoftston.issuser.conchapp.presenter.LoginPresenter;
import com.isoftston.issuser.conchapp.utils.MD5Utils;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;

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
    private String username;
    private String password;

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
        username = et_login.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //如果没有授权，则请求授权
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }else{
            presenter.loginAction(username, password);
        }

    }
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA) {
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //授权失败
                ToastMgr.show(getString(R.string.check_manager_open_pemission));
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void loginSuccess(String data) {
        LoginUserBean loginUserBean=new LoginUserBean();
        loginUserBean.setUsername(username);
        loginUserBean.setPassword(MD5Utils.encode(password));
        loginUserBean.save();
        SharePrefsUtils.putValue(context,"token",data);
        startActivity(MainActivity.getLauncher(context));
        ToastUtils.showtoast(context,getString(R.string.login_success));
    }

    @Override
    public void changePwdSuccess() {

    }

    @Override
    public void getCodeSuccess() {

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
