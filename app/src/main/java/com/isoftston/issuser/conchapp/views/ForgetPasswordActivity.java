package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.presenter.ForgetPasswordPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.ForgetPasswordView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/12.
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordView,ForgetPasswordPresenter> implements ForgetPasswordView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_email)
    EditText et_email;
    @Bind(R.id.tv_hint)
    TextView tv_hint;

    private Context context=ForgetPasswordActivity.this;

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
        nav.setColorRes(R.color.transparent);
        setBarColor(getResources().getColor(R.color.transparent_black));

        String text =getString(R.string.forget_password_hint);
        SpannableStringBuilder style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_light_blue)),5,10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置textview的背景颜色
        tv_hint.setText(style);
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

    @OnClick(R.id.tv_send)
    public void sendEmail(){
        presenter.sendRequst(et_email.getText().toString());
    }
}
