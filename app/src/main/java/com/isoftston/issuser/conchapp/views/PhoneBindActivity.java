package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.MainActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.presenter.BindPhonePresenter;
import com.isoftston.issuser.conchapp.presenter.LoginPresenter;
import com.isoftston.issuser.conchapp.utils.MyCountDownTimer;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.BindPhoneView;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

public class PhoneBindActivity extends BaseActivity<BindPhoneView,BindPhonePresenter> implements BindPhoneView {


    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.et_acount)
    EditText phoneNum;
    @Bind(R.id.et_code)
    EditText validNum;
    @Bind(R.id.tv_get_code)
    TextView tv_get_code;

    private MyCountDownTimer timer;
    private int isPhoneNull;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_bind;
    }

    public static Intent getLaucner(Context context,int isPhoneNull,String phone) {
        Intent intent =new Intent(context,PhoneBindActivity.class);
        Bundle bundle =new Bundle();
        bundle.putInt("isPhoneNull",isPhoneNull);
        bundle.putString("phone",phone);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setBarColor(getResources().getColor(R.color.transparent_black));
        tv_title.setTextColor(getResources().getColor(R.color.text_color));
        nav.setColorRes(R.color.white);
        nav.showBack(2);
        Bundle bundle=getIntent().getExtras();
        isPhoneNull = bundle.getInt("isPhoneNull",0);
        if (isPhoneNull == 1){
            nav.setNavTitle(getString(R.string.bind_phone));
        }else{
            nav.setNavTitle(getString(R.string.change_bind_phone));
            phone = bundle.getString("phone");
            phoneNum.setFocusable(false);
            phoneNum.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
        }
    }

    @Override
    protected BindPhonePresenter createPresenter() {
        return new BindPhonePresenter();
    }


    @Override
    public void sendValidNumSuccess() {
        ToastMgr.show(getString(R.string.forget_password_send_valid_success));
    }

    @Override
    public void sendErrorMessage(String msg) {
        timer.onFinish();
    }

    @Override
    public void bindSuccess() {
        ToastUtils.showtoast(getViewContext(),getString(R.string.bind_success));
        finish();
    }

    @OnClick(R.id.tv_send)
    public void bindPhone(){
        if (isPhoneNull == 1){
            presenter.bindPhone(phoneNum.getText().toString(),validNum.getText().toString());
        }else{
            presenter.changeBindPhone(phone,validNum.getText().toString());
        }
    }

    @OnClick(R.id.tv_get_code)
    public void getCode(){
        if (isPhoneNull == 1){
            String phoneCode=phoneNum.getText().toString();
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$");
            Matcher m = p.matcher(phoneCode);
            Log.i("test","--test--"+m.matches());
            if (TextUtils.isEmpty(phoneCode)){
                ToastMgr.show(R.string.tips);
                return;
            }
            if (!m.matches()){
                ToastMgr.show(getString(R.string.phone_check));
                return;
            }
        }
        timer = new MyCountDownTimer(getViewContext(),tv_get_code, 60 * 1000, 1000,true);
        timer.start();
        //调接口
        if (isPhoneNull == 1){
            presenter.sendValidNum(phoneNum.getText().toString());
        }else {
            presenter.getCode(phone);
        }

    }

}
