package com.isoftston.issuser.conchapp.views.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.presenter.UserPresenter;
import com.isoftston.issuser.conchapp.utils.VersionUtil;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;
import com.isoftston.issuser.conchapp.views.work.ScanCodeActivity;

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

    private boolean isMan=false;
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
        ((BaseActivity)getActivity()).getLoadingDialog().show();
        presenter.getUserInfo();
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
//                startActivity(ScanCodeActivity.getLauncher(getActivity(),new WorkBean()));
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
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {
        hideLoading();
        userInfo=userInfoBean;
        if (userInfoBean.getSex()!=null){
            if (userInfoBean.getSex().equals(getContext().getResources().getString(R.string.sex_female))) {
                headIv.setImageResource(R.mipmap.woman_head);
            } else {
                headIv.setImageResource(R.mipmap.man_head);
            }
        }
        userNameTv.setText(userInfo.getRealName());
        userCompanyTv.setText(userInfo.getOrgName());
    }

    @Override
    public void getUserInfoError() {
        ((BaseActivity)getActivity()).getLoadingDialog().dismiss();
    }

    @Override
    public void addFeedBackSuccess() {

    }

    @Override
    public void updatePwdSuccess() {

    }

    @Override
    public void updatePwdError(){

    }
}
