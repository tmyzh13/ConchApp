package com.isoftston.issuser.conchapp.views.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/12.
 */

public class CheckDeviceDetailActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,CheckDeviceDetailActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_device_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.danger_work));
        navBar.setColorRes(R.color.transparent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
