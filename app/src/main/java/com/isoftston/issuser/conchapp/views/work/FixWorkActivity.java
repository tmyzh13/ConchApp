package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/11.
 */

public class FixWorkActivity extends BaseActivity {
    @Bind(R.id.nav)
    NavBar nav;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_fix_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.colorPrimary);
        nav.setNavTitle(getString(R.string.fix_work));
        nav.hideBack();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
