package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.UserPresenter;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

public class OpinionFeedbackActivity extends BaseActivity<UserView, UserPresenter> implements UserView {
    private static final String TAG = "OpinionFeedbackActivity";
    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.feedback_text)
    EditText mFeedbackView;
    @Bind(R.id.feedback_text_show_num)
    TextView mNumTextShow;
    @Bind(R.id.submit_view)
    TextView mSubmitView;

    private static final int MAX_LENGTH = 150;//最大输入字符数150 
    private int Rest_Length = MAX_LENGTH;


    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, OpinionFeedbackActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opinion_feedback;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.feed_back));
        mNumTextShow.setText(Html.fromHtml("<font color=\"red\">" + MAX_LENGTH + "/" + MAX_LENGTH + "</font>"));
        mFeedbackView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mNumTextShow.setText(Html.fromHtml("您还可以输入:" + "<font color=\"red\">" + Rest_Length + "/" + MAX_LENGTH + "</font>"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Rest_Length > 0) {
                    Rest_Length = MAX_LENGTH - mFeedbackView.getText().length();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNumTextShow.setText(Html.fromHtml("<font color=\"red\">" + Rest_Length + "/" + MAX_LENGTH + "</font>"));
            }
        });
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @OnClick(R.id.submit_view)
    public void submit() {
        if (mFeedbackView.getText().length() <= 0) {
            return;
        }
        presenter.addFeedBack(mFeedbackView.getText().toString());
        //上传反馈信息
        //todo
    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {

    }

    @Override
    public void getUserInfoError() {

    }

    @Override
    public void addFeedBackSuccess() {
        ToastUtils.showtoast(this,getResources().getString(R.string.submit_success));
        finish();

    }

    @Override
    public void updatePwdSuccess() {

    }

    @Override
    public void updatePwdError() {

    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }
}
