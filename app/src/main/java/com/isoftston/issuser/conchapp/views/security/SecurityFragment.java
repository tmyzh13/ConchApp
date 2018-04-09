package com.isoftston.issuser.conchapp.views.security;

import android.os.Bundle;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class SecurityFragment extends BaseFragment {

    @Bind(R.id.nav)
    NavBar nav;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_security;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.colorPrimary);
        nav.setNavTitle(getString(R.string.home_security));
        nav.hideBack();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
