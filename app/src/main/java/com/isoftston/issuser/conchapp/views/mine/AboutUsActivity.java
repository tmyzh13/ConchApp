package com.isoftston.issuser.conchapp.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.views.security.AddHiddenTroubleActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar nav;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,AboutUsActivity.class);
        return intent;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.about_us));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
