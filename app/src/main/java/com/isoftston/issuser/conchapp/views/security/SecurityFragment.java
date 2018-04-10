package com.isoftston.issuser.conchapp.views.security;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.DisplayUtil;
import com.isoftston.issuser.conchapp.views.work.ItemFragment;
import com.isoftston.issuser.conchapp.weight.NavBar;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

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
