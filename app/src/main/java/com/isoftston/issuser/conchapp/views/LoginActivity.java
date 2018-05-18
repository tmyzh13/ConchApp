package com.isoftston.issuser.conchapp.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
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
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.presenter.LoginPresenter;
import com.isoftston.issuser.conchapp.utils.LanguageUtil;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.utils.UpdateManager;
import com.isoftston.issuser.conchapp.utils.VersionUtils;
import com.isoftston.issuser.conchapp.views.interfaces.LoginView;
import com.isoftston.issuser.conchapp.views.mine.ChangePwdActivity;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView {

    private final String TAG =  LoginActivity.class.getSimpleName();

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
        if (SharePrefsUtils.getValue(getViewContext(),"app_language","").equals("en")){
            LanguageUtil.set(getViewContext(),true);

        }else {
            LanguageUtil.set(getViewContext(),false);
        }
        if (!TextUtils.isEmpty(PreferencesHelper.getData(Constant.LOGIN_STATUE))){
            startActivity(MainActivity.getLauncher(context));
            finish();
        }
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ViewGroup.LayoutParams lp =view_statue.getLayoutParams();
        lp.height= Tools.getStatueHeight(context);
        view_statue.setLayoutParams(lp);
        setBarColor(getResources().getColor(R.color.transparent_black));
        presenter.getServerVersion();
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

//        LoginUserBean loginUserBean=new LoginUserBean();
//        loginUserBean.setUsername(username);
//        loginUserBean.setPassword(MD5Utils.encode(password));
//        loginUserBean.save();

        //presenter.getPushTag();



    }

    @Override
    public void changePwdSuccess() {

    }

    @Override
    public void getCodeSuccess() {

    }

    @Override
    public void returnTag(String isSuccess,String tag) {
        if("false".equals(isSuccess)){
            deleteTag(tag);
        }else{
            addTag(tag);
        }
    }

    @Override
    public void getSafeChoiceList(SecuritySearchBean bean) {

    }

    @Override
    public void getServerVersionCode(String serverCode) {
        int clientVersion = getClientVersion();
        Log.i(TAG,"client ver:" + clientVersion + ",server ver:" + serverCode);
        int compareCode = VersionUtils.compareVersions(String.valueOf(clientVersion),serverCode);
        if(compareCode == 1)
        {
            //强制更新
            UpdateManager um = new UpdateManager(this);
            um.showNoticeDialog(serverCode);
        }
    }

    public void bindPhone(int isPhoneNull,String phone) {
        startActivity(PhoneBindActivity.getLaucner(context,isPhoneNull,phone));
    }

    @Override
    public void loginSuccessEx(Boolean newPhone, Boolean phoneIsNull,String phone) {
        boolean isMatch = Pattern.matches(Constant.PASSWORD_STYLE, et_password.getText().toString().trim());

        if(!isMatch)
        {
            startActivity(ChangePwdActivity.getLauncher(context,null));
            return;
        }

        if(phoneIsNull)
        {
            bindPhone(1,phone);
            return;
        }
        else if(newPhone)
        {
            bindPhone(0,phone);
            return;
        }

        PreferencesHelper.saveData(Constant.LOGIN_STATUE,"1");
        startActivity(MainActivity.getLauncher(context));
        ToastUtils.showtoast(context,getString(R.string.login_success));
        finish();
    }


    private void addTag(String tag) {
       // final String tag = username;
        if (TextUtils.isEmpty(tag)) {
            //Toast.makeText(this, "请先输入tag", Toast.LENGTH_SHORT).show();
            return;
        }
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

                Log.i("isSuccess", String.valueOf(isSuccess));
            /*handler.post(new Runnable() {
                @Override
                public void run() {
                    inputTag.setText("");
                    if (isSuccess) {
                        sharedPref.edit().putInt(TAG_REMAIN, result.remain).apply();
                        tagRemain.setText(String.valueOf(result.remain));
                        PushDialogFragment.newInstance(0, 1, getString(R.string.push_add_success), tag).show(
                                getFragmentManager(), "addTag");
                    } else {
                        PushDialogFragment.newInstance(0, 0, getString(R.string.push_add_fail), tag).show(
                                getFragmentManager(), "addTag");
                    }
                }
            });*/
            }
        }, tag);
    }


    private void deleteTag(String tag) {
       // final String tag = inputTag.getText().toString();
        if (TextUtils.isEmpty(tag)) {
            //Toast.makeText(this, "请先输入tag", Toast.LENGTH_SHORT).show();
            return;
        }
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
             /*   handler.post(new Runnable() {
                    @Override
                    public void run() {
                        inputTag.setText("");
                        if (isSuccess) {
                            sharedPref.edit().putInt(TAG_REMAIN, result.remain).apply();
                            tagRemain.setText(String.valueOf(result.remain));
                            PushDialogFragment.newInstance(0, 1,
                                    getString(R.string.push_delete_success), tag).show(getFragmentManager(), "deleteTag");
                        } else {
                            PushDialogFragment.newInstance(0, 0,
                                    getString(R.string.push_delete_fail), tag).show(getFragmentManager(), "deleteTag");
                        }
                    }
                });*/
            }
        }, tag);
    }

    @OnClick(R.id.tv_language)
    public void changeLanguage(){
        //中英文切换
        if (SharePrefsUtils.getValue(getViewContext(),"app_language","").equals("en")){
            SharePrefsUtils.putValue(getViewContext(),"app_language","zh");

        }else {
            SharePrefsUtils.putValue(getViewContext(),"app_language","en");
        }

        Intent intent = new Intent(this, LoginActivity.class);

        //开始新的activity同时移除之前所有的activity

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

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

    private int getClientVersion()
    {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }
}
