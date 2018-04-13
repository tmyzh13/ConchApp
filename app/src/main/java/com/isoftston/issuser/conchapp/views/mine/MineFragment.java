package com.isoftston.issuser.conchapp.views.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";

    @Bind(R.id.head_iv)
    CircleImageView headIv;//头像
    @Bind(R.id.user_name_tv)
    TextView userNameTv;//名字
    @Bind(R.id.user_company_tv)
    TextView userCompanyTv;//公司
    @Bind(R.id.user_department_tv)
    TextView userDepartmentTv;//部门
    @Bind(R.id.individual_center)
    LinearLayout mIndividualCenterLayout;//个人中心Btn
    @Bind(R.id.about_us)
    LinearLayout mAboutUsLayout;//关于我们Btn
    @Bind(R.id.opinion_feedback)
    LinearLayout mOpinionFeedbackLayout;//意见反馈
    @Bind(R.id.settings)
    LinearLayout mSettingsLayout;//设置
    @Bind(R.id.log_off)
    RelativeLayout mLogOffLayout;//退出Btn


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        clicks();
    }

    private void clicks() {
        mIndividualCenterLayout.setOnClickListener(this);
        mAboutUsLayout.setOnClickListener(this);
        mOpinionFeedbackLayout.setOnClickListener(this);
        mSettingsLayout.setOnClickListener(this);
        mLogOffLayout.setOnClickListener(this);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.individual_center:

                break;
            case R.id.about_us:
                startActivity(AboutUsActivity.getLauncher(getActivity()));
                break;
            case R.id.opinion_feedback:
                startActivity(OpinionFeedbackActivity.getLauncher(getActivity()));
                break;
            case R.id.settings:
                startActivity(SettingsActivity.getLauncher(getActivity()));
                break;
            default:
                break;
        }
    }
}
