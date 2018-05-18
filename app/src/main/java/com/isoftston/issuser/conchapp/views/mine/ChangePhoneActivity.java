package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.presenter.BindPhonePresenter;
import com.isoftston.issuser.conchapp.utils.MyCountDownTimer;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.PhoneBindActivity;
import com.isoftston.issuser.conchapp.views.interfaces.BindPhoneView;
import com.isoftston.issuser.conchapp.weight.NavBar;
import com.umeng.message.PushAgent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/12.
 */

public class ChangePhoneActivity extends BaseActivity<BindPhoneView,BindPhonePresenter> implements BindPhoneView {

    private final int REQUEST_CODE = 100;
    private final int RESULT_CODE = 10;

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
    @Bind(R.id.tv_get_code)
    TextView tv_get_code;
    @Bind(R.id.et_acount)
    EditText et_phone;

    @Bind(R.id.tv_send)
    TextView tv_send;

    private Context context=ChangePhoneActivity.this;
    private MyCountDownTimer timer;

    public static Intent getLaucner(Context context){
        Intent intent =new Intent(context,ChangePhoneActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        PushAgent.getInstance(context).onAppStart();
        setBarColor(getResources().getColor(R.color.transparent_black));
        tv_title.setTextColor(getResources().getColor(R.color.text_color));
        nav.setColorRes(R.color.white);
        nav.setNavTitle(getString(R.string.change_phone));
        nav.showBack(2);
        String text =getString(R.string.forget_password_hint);
        SpannableStringBuilder style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_light_blue)),5,10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置textview的背景颜色
        tv_hint.setText(style);
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
        setResult(RESULT_CODE);
        finish();
    }

    @OnClick(R.id.tv_send)
    public void updatePhone(){
        presenter.bindPhone(et_phone.getText().toString(),et_code.getText().toString());
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
}
