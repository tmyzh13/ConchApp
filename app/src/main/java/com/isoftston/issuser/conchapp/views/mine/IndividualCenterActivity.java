package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.UserPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;

import butterknife.Bind;

public class IndividualCenterActivity extends BaseActivity implements View.OnClickListener {
    public static String TAG = "IndividualCenterActivity";
    @Bind(R.id.iv_back)
    ImageView backIv;
    @Bind(R.id.tv_title)
    TextView titleTv;
    @Bind(R.id.sure_tv)
    TextView sureTv;

    @Bind(R.id.user_name)
    EditText userNameEt;
    @Bind(R.id.user_role)
    TextView sureRoleTv;//角色/监控人员
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.radio_button_man)
    RadioButton radioButtonMan;
    @Bind(R.id.radio_button_woman)
    RadioButton radioButtonWoman;
    @Bind(R.id.user_account)
    TextView userAccountTv;//账号
    @Bind(R.id.show_pwd_layout)
    RelativeLayout showPwdLayout;//显示、隐藏密码
    @Bind(R.id.user_pwd)
    EditText userPwdEt;
    @Bind(R.id.img_edit_7)
    ImageView userPhoneNumIv;//加手机号
    @Bind(R.id.user_phoneNum)
    EditText userPhoneNumEt;
    @Bind(R.id.user_company)
    TextView userCompanyTv;
    @Bind(R.id.user_department)
    TextView userDepartmentTv;

    private boolean isShowPwd = false;
    private UserInfoBean userInfo;

    public static Intent getLauncher(Context context, UserInfoBean userInfo) {
        Intent intent = new Intent(context, IndividualCenterActivity.class);
        intent.putExtra("userInfo", userInfo);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_individual_center;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        titleTv.setText(getString(R.string.individual_center));
        showUserInfo();
        clicks();
        changeIcon();
    }

    /**
     * 获取用户信息并显示
     */
    private void showUserInfo() {
//        userInfo = (UserInfoBean) getIntent().getSerializableExtra("userInfo");
    }

    private void changeIcon() {
        userPhoneNumEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s != null && s.length() != 0) {
                    userPhoneNumIv.setImageResource(R.mipmap.edite);
                } else {
                    userPhoneNumIv.setImageResource(R.mipmap.addcolumn);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    userPhoneNumIv.setImageResource(R.mipmap.edite);
                } else {
                    userPhoneNumIv.setImageResource(R.mipmap.addcolumn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void clicks() {
        backIv.setOnClickListener(this);
        sureTv.setOnClickListener(this);
        showPwdLayout.setOnClickListener(this);
        boolean isMan = false;
        if (isMan) {
            radioButtonMan.setChecked(true);
        } else {
            radioButtonWoman.setChecked(true);
        }
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.sure_tv:
                break;
            case R.id.show_pwd_layout:
                isShowPwd = !isShowPwd;
                if (isShowPwd) {
                    //显示密码
                    userPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    userPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            default:
                break;
        }
    }
}
