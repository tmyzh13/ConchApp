package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.CheckPresenter;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yizh
 */
public class WebActivity extends BaseActivity<CheckView,CheckPresenter> implements CheckView{

    private Context context =WebActivity.this;
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.web)
    WebView web;
    @Bind(R.id.progress)
    ProgressBar progress;

    private static String WEB_TITLE="title";
    private static String WEB_URL="url";
    private static String WEB_CONTENT = "webContent";
    private String webContent="";

    private WebViewClient mClient=new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(!Tools.isNull(url)){
                view.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            }
//                view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            忽略该证书错误
            handler.proceed();
        }
    };

    private WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setNewProgress(newProgress);
            super.onProgressChanged(view, newProgress);

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
//            if(!TextUtils.isEmpty(getIntent().getStringExtra("title")))
//                nav.setNavTitle(title);
            super.onReceivedTitle(view, title);

        }
    };

    public static Intent getLauncher(Context context,String title,String webContent){
        Log.e("yzh","WebActivity");
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra(WEB_TITLE,title);
        intent.putExtra(WEB_CONTENT,webContent);
        return intent;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.e("yzh","init");
        Map<String,String> request = new HashMap<>();
        request.put("descId",getIntent().getStringExtra(WEB_CONTENT));
        presenter.getDescription(request);
        nav.setNavTitle(getIntent().getStringExtra(WEB_TITLE));
        web.setWebViewClient(mClient);
        web.setWebChromeClient(mChromeClient);
        web.getSettings().setJavaScriptEnabled(true);
//        if(!TextUtils.isEmpty(getIntent().getStringExtra(WEB_URL))){
//
//                web.loadDataWithBaseURL(null, getIntent().getStringExtra("url"), "text/html", "utf-8", null);
////                web.loadUrl(getIntent().getStringExtra("url"));
//
//        }
        if(!TextUtils.isEmpty(webContent)){
            web.loadData(webContent,"text/html", "utf-8");
        }
    }

    @Override
    protected CheckPresenter createPresenter() {
        return new CheckPresenter();
    }

    private void setNewProgress(int newProgress){
        progress.setVisibility(View.VISIBLE);
        progress.setProgress(newProgress);
        if(newProgress >= 100)
            progress.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        //web.goBack();
        super.onBackPressed();
    }

    @Override
    public void setDescription(String description) {
        webContent = description;
        if(!TextUtils.isEmpty(webContent)){
            web.loadData(webContent, "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void renderDatas(boolean reload, List<DeviceBean> list) {

    }

    @Override
    public void checkDeviceResult(DeviceBean bean) {

    }

    @Override
    public void checkDeviceResultError() {

    }

    @Override
    public void CheckAllDeviceResult(List<DeviceBean> deviceListBean, String total) {

    }

    @Override
    public void setUserInfo(UserInfoBean userInfo) {

    }

    @Override
    public void reLogin() {
        ToastUtils.showtoast(this,getString(R.string.re_login));
        PreferencesHelper.saveData(Constant.LOGIN_STATUE,"");
        startActivity(LoginActivity.getLauncher(this));
    }
}
