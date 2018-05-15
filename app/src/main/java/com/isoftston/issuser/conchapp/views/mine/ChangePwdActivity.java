package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.corelibs.base.BaseActivity;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.UserPresenter;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;


public class ChangePwdActivity extends BaseActivity<UserView, UserPresenter> implements UserView, View.OnClickListener {
    public static String TAG = ChangePwdActivity.class.getSimpleName();

    //密码输入
    @Bind(R.id.pwd_value)
    EditText pwd_value;

    //密码确认
    @Bind(R.id.pwd_value_confirm)
    EditText pwd_value_again;

    //密码错误提示
    @Bind(R.id.pwd_error)
    TextView pwd_error;

    //密码强度
    @Bind(R.id.view_low)
    ImageView view_low;

    @Bind(R.id.view_middle)
    ImageView view_middle;

    @Bind(R.id.view_high)
    ImageView view_high;

    @Bind(R.id.pwd_low)
    TextView tv_low;

    @Bind(R.id.pwd_medium)
    TextView tv_medium;

    @Bind(R.id.pwd_high)
    TextView tv_high;

    //密码错误框
    @Bind(R.id.ll_error)
    LinearLayout ll_error;

    //确定
    @Bind(R.id.tv_login)
    TextView tv_login;

    @OnClick(R.id.tv_login)
    public void updatePwd()
    {
        presenter.updatePwd(pwd_value_again.getText().toString());
        return;
    }

    private Boolean isValid = false;

    private void addPwdListen(){
        pwd_value.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isValid = false;

                if(s.length() == 0)
                {
                    view_low.setImageResource(R.mipmap.security_grey);
                    view_middle.setImageResource(R.mipmap.security_grey);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.INVISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.INVISIBLE);
                    pwd_value.setTextAppearance(getApplicationContext(),R.style.edit_normal);
                    return;
                }

                String regex = "(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s.toString());

                if(!matcher.find())
                {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_grey);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.VISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.VISIBLE);
                    pwd_value.setTextAppearance(getApplicationContext(),R.style.edit_normal);
                    pwd_value.setBackgroundResource(R.drawable.et_login_bg_error);
                    return;
                }

                Integer security = getPwdSecurity(s);
                if(0 == security)
                {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_grey);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.VISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.VISIBLE);
                    pwd_value.setTextAppearance(getApplicationContext(),R.style.edit_normal);
                    pwd_value.setBackgroundResource(R.drawable.et_login_bg_error);
                }
                else if(1 == security)
                {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_green);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.INVISIBLE);
                    tv_medium.setVisibility(View.VISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.INVISIBLE);
                    pwd_value.setTextAppearance(getApplicationContext(),R.style.edit_normal);
                    pwd_value.setBackgroundResource(R.drawable.et_login_bg_error);
                }
                else if(2 == security)
                {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_green);
                    view_high.setImageResource(R.mipmap.security_green);
                    tv_low.setVisibility(View.INVISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.VISIBLE);
                    ll_error.setVisibility(View.INVISIBLE);
                    pwd_value.setTextAppearance(getApplicationContext(),R.style.edit_normal);
                    pwd_value.setBackgroundResource(R.drawable.et_login_bg);
                    isValid = true;
                }

                tv_login.setEnabled(false);
                tv_login.setBackgroundResource(R.drawable.tv_write_bg_disable);
                if(s.toString().equals(pwd_value_again.getText().toString()) && (s.length() != 0) && (isValid))
                {
                    tv_login.setEnabled(true);
                    tv_login.setBackgroundResource(R.drawable.tv_write_bg);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pwd_value_again.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_login.setEnabled(false);
                tv_login.setBackgroundResource(R.drawable.tv_write_bg_disable);
                if(s.toString().equals(pwd_value.getText().toString()) && (s.length() != 0) && (isValid))
                {
                    tv_login.setEnabled(true);
                    tv_login.setBackgroundResource(R.drawable.tv_write_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private Integer getPwdSecurity(CharSequence s)
    {
        if(s.length() <= 8)
        {
            return 0;
        }
        else if((s.length() > 8) && (s.length() < 10))
        {
            return 1;
        }
        else if(s.length() >= 10)
        {
            return 2;
        }

        return 0;
    }

    public static Intent getLauncher(Context context, UserInfoBean userInfo) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addPwdListen();
        tv_login.setEnabled(false);
        tv_login.setBackgroundResource(R.drawable.tv_write_bg_disable);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }



    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {

    }

    @Override
    public void getUserInfoError() {

    }

    @Override
    public void addFeedBackSuccess() {

    }

    @Override
    public void updatePwdSuccess() {
        ToastUtils.showtoast(ChangePwdActivity.this, getString(R.string.update_pwd_success));
        PreferencesHelper.remove(Constant.LOGIN_STATUE);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updatePwdError(){
        ToastUtils.showtoast(ChangePwdActivity.this, getString(R.string.update_pwd_failure));
        finish();
    }
}
