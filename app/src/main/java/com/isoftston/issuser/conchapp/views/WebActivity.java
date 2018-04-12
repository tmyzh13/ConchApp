package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;



import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

/**
 * Created by yizh
 */
public class WebActivity extends BaseActivity {

    private Context context;
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.web)
    WebView web;
    @Bind(R.id.progress)
    ProgressBar progress;

    private static String WEB_TITLE="title";
    private static String WEB_URL="url";

    private WebViewClient mClient=new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(getIntent().getStringExtra("title").equals("广告详情")){
                view.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            }else{
                view.loadUrl(url);
            }
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
            if(!TextUtils.isEmpty(getIntent().getStringExtra("title")))
                nav.setNavTitle(title);
            super.onReceivedTitle(view, title);

        }
    };

    public static Intent getLauncher(Context context,String title,String url){
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra(WEB_TITLE,title);
        intent.putExtra(WEB_URL,url);
        return intent;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getIntent().getStringExtra("title"));
        web.setWebViewClient(mClient);
        web.setWebChromeClient(mChromeClient);
        web.getSettings().setJavaScriptEnabled(true);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("url"))){

            if(getIntent().getStringExtra("title").equals("广告详情")){
                web.loadDataWithBaseURL(null, getIntent().getStringExtra("url"), "text/html", "utf-8", null);
            }else{
                web.loadUrl(getIntent().getStringExtra("url"));
            }

        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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

}
