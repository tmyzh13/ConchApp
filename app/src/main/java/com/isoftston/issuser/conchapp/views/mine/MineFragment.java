package com.isoftston.issuser.conchapp.views.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.UserPresenter;
import com.isoftston.issuser.conchapp.utils.VersionUtil;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class MineFragment extends BaseFragment<UserView, UserPresenter> implements UserView, View.OnClickListener {
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
    @Bind(R.id.version_tv)
    TextView versionTv;//版本

    private boolean isMan;
    private UserInfoBean userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        clicks();
        showUserInfo();
        versionTv.setText("V" + VersionUtil.getVersionName(getActivity()));
    }

    private void showUserInfo() {
        presenter.getUserInfo("cn","xxx");
        if (isMan) {
            headIv.setImageResource(R.mipmap.man_head);
        } else {
            headIv.setImageResource(R.mipmap.woman_head);
        }
//        userNameTv.setText();
//        userCompanyTv.setText();
//        userDepartmentTv.setText();
    }

    private void clicks() {
        mIndividualCenterLayout.setOnClickListener(this);
        mAboutUsLayout.setOnClickListener(this);
        mOpinionFeedbackLayout.setOnClickListener(this);
        mSettingsLayout.setOnClickListener(this);
        mLogOffLayout.setOnClickListener(this);
        mLogOffLayout.setOnClickListener(this);
    }


    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.individual_center:
                startActivity(IndividualCenterActivity.getLauncher(getActivity(),userInfo));
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
            case R.id.log_off:
                PreferencesHelper.remove(Constant.LOGIN_STATUE);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {

    }

    @Override
    public void getUserInfoError() {

    }
}
