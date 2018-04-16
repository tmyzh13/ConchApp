package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

public class OpinionFeedbackActivity extends BaseActivity {
    private static final String TAG = "OpinionFeedbackActivity";
    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.feedback_text)
    EditText mFeedbackView;
    @Bind(R.id.feedback_text_show_num)
    TextView mSubmitView;
    @Bind(R.id.submit_view)
    TextView mNumTextShow;

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
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.submit_view)
    public void submit() {
        if (mFeedbackView.getText().length() <= 0) {
            return;
        }
        //上传反馈信息
        //todo
    }
}
