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

public class MineMessageFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerMy)
    ViewPager viewPager;
    public String[] tabs;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabs=new String[]{getString(R.string.my_approve), getString(R.string.my_check),getString(R.string.my_release)};
        WorkMessageAdapter adapter=new WorkMessageAdapter(getActivity().getSupportFragmentManager(),tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Tools.setIndicator(tabLayout,10,10);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
