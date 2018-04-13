package com.isoftston.issuser.conchapp.views.security;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypePageAdapter;
import com.isoftston.issuser.conchapp.utils.Tools;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/12.
 */

public class MineMsgFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerMine)
    ViewPager viewPager;

    public String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_illegal_mine_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.e("yzh","mineMsgFragment");
        tabs=new String[]{getString(R.string.send),getString(R.string.not_alter),getString(R.string.overdue)
                ,getString(R.string.altered),getString(R.string.not_check)};
        MessageTypePageAdapter adapter=new MessageTypePageAdapter(getActivity().getSupportFragmentManager(),tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Tools.setIndicator(tabLayout,10,10);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
