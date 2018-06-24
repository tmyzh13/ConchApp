package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.presenter.ForgetPasswordPresenter;
import com.isoftston.issuser.conchapp.utils.MyCountDownTimer;
import com.isoftston.issuser.conchapp.views.interfaces.ForgetPasswordView;
import com.isoftston.issuser.conchapp.weight.NavBar;
import com.umeng.message.PushAgent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/12.
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordView,ForgetPasswordPresenter> implements ForgetPasswordView {

    @Bind(R.id.nav)
    NavBar nav;
//    @Bind(R.id.et_acount)
//    EditText et_email;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_hint)
    TextView tv_hint;
    @Bind(R.id.et_code)
    EditText et_code;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.tv_get_code)
    TextView tv_get_code;
    @Bind(R.id.et_acount)
    EditText et_phone;


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

    //密码确认
    @Bind(R.id.pwd_value_confirm)
    EditText pwd_value_again;

    @Bind(R.id.tv_send)
    TextView tv_send;

    private Boolean isValid = false;

    private Context context=ForgetPasswordActivity.this;
    private MyCountDownTimer timer;

    public static Intent getLaucner(Context context){
        Intent intent =new Intent(context,ForgetPasswordActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        PushAgent.getInstance(context).onAppStart();
        setBarColor(getResources().getColor(R.color.transparent_black));
        tv_title.setTextColor(getResources().getColor(R.color.text_color));
        nav.setColorRes(R.color.white);
        nav.setNavTitle(getString(R.string.forget_password));
        nav.showBack(2);
        String text =getString(R.string.forget_password_hint);
        SpannableStringBuilder style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_light_blue)),5,10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置textview的背景颜色
        tv_hint.setText(style);
        addPwdListen();
    }

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    public void sendActionSuccess() {
        ToastMgr.show(getString(R.string.forget_password_send_success));
        finish();
    }

    @Override
    public void sendValidNumSuccess() {
        ToastMgr.show(getString(R.string.forget_password_send_valid_success));
    }

    @Override
    public void sendErrorMessage(String msg) {
        timer.onFinish();
    }

    @OnClick(R.id.tv_send)
    public void sendEmail(){
        presenter.sendRequst(et_phone.getText().toString(),et_code.getText().toString(),et_password.getText().toString());
    }

    @OnClick(R.id.tv_get_code)
    public void getCode(){
        String phoneCode=et_phone.getText().toString();
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$");
        Matcher m = p.matcher(phoneCode);
        Log.i("test","--test--"+m.matches());
        if (TextUtils.isEmpty(phoneCode)){
            ToastMgr.show(R.string.tips);
            return;
        }
        if (!m.matches()){
            ToastMgr.show("请输入正确的手机号码");
            return;
        }
        timer = new MyCountDownTimer(context,tv_get_code, 60 * 1000, 1000,true);
        timer.start();
        //调接口
        presenter.sendValidNum(et_phone.getText().toString());
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

    private void addPwdListen() {
        et_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isValid = false;

                if (s.length() == 0) {
                    view_low.setImageResource(R.mipmap.security_grey);
                    view_middle.setImageResource(R.mipmap.security_grey);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.INVISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.INVISIBLE);
                    et_password.setTextAppearance(getApplicationContext(), R.style.edit_normal);
                    return;
                }

                Pattern pattern = Pattern.compile(Constant.PASSWORD_STYLE);
                Matcher matcher = pattern.matcher(s.toString());

                if (!matcher.find()) {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_grey);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.VISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.VISIBLE);
                    et_password.setTextAppearance(getApplicationContext(), R.style.edit_normal);
                    et_password.setBackgroundResource(R.drawable.et_login_bg_error);
                    return;
                }

                Integer security = getPwdSecurity(s);
                if (0 == security) {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_grey);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.VISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.VISIBLE);
                    et_password.setTextAppearance(getApplicationContext(), R.style.edit_normal);
                    et_password.setBackgroundResource(R.drawable.et_login_bg);
                    isValid = true;
                } else if (1 == security) {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_green);
                    view_high.setImageResource(R.mipmap.security_grey);
                    tv_low.setVisibility(View.INVISIBLE);
                    tv_medium.setVisibility(View.VISIBLE);
                    tv_high.setVisibility(View.INVISIBLE);
                    ll_error.setVisibility(View.INVISIBLE);
                    et_password.setTextAppearance(getApplicationContext(), R.style.edit_normal);
                    et_password.setBackgroundResource(R.drawable.et_login_bg);
                    isValid = true;
                } else if (2 == security) {
                    view_low.setImageResource(R.mipmap.security_green);
                    view_middle.setImageResource(R.mipmap.security_green);
                    view_high.setImageResource(R.mipmap.security_green);
                    tv_low.setVisibility(View.INVISIBLE);
                    tv_medium.setVisibility(View.INVISIBLE);
                    tv_high.setVisibility(View.VISIBLE);
                    ll_error.setVisibility(View.INVISIBLE);
                    et_password.setTextAppearance(getApplicationContext(), R.style.edit_normal);
                    et_password.setBackgroundResource(R.drawable.et_login_bg);
                    isValid = true;
                }

                tv_send.setEnabled(false);
                tv_send.setBackgroundResource(R.drawable.tv_write_bg_disable);
                if (s.toString().equals(pwd_value_again.getText().toString()) && (s.length() != 0) && (isValid)) {
                    tv_send.setEnabled(true);
                    tv_send.setBackgroundResource(R.drawable.tv_write_bg);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pwd_value_again.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_send.setEnabled(false);
                tv_send.setBackgroundResource(R.drawable.tv_write_bg_disable);
                if (s.toString().equals(et_password.getText().toString()) && (s.length() != 0) && (isValid)) {
                    tv_send.setEnabled(true);
                    tv_send.setBackgroundResource(R.drawable.tv_write_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
