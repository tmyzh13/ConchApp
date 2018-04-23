package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkMessageAdapter;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/16.
 */

public class CommonMessageFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerCommon)
    ViewPager viewPager;
    public String[] tabs;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabs=new String[]{getString(R.string.all), getString(R.string.shuini), getString(R.string.matou),getString(R.string.kuangshan),getString(R.string.build)};
        WorkMessageAdapter adapter=new WorkMessageAdapter(getActivity().getSupportFragmentManager(),tabs, 0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Tools.setIndicator(tabLayout,10,10);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
