package com.isoftston.issuser.conchapp.views.mine;

import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

public class IndividualCenterActivity extends BaseActivity {
    @Bind(R.id.nav)
    NavBar nav;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_individual_center;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.work_point_information));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
