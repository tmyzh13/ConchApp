package com.isoftston.issuser.conchapp.views.security;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypePageAdapter;
import com.isoftston.issuser.conchapp.model.bean.HiddenTroubleMsgNumBean;
import com.isoftston.issuser.conchapp.model.bean.MineMsgNumBean;
import com.isoftston.issuser.conchapp.utils.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/12.
 */

public class MineMsgFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerMine)
    ViewPager viewPager;

    private MessageTypePageAdapter adapter;

    public String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_illegal_mine_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        Log.e("yzh","mineMsgFragment");
        tabs=new String[]{getString(R.string.my_send),getString(R.string.my_approval),getString(R.string.my_fix),getString(R.string.my_accept),getString(R.string.my_sales)};
        adapter=new MessageTypePageAdapter(getActivity().getSupportFragmentManager(),tabs,2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        Tools.setIndicator(tabLayout,10,10);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //刷新
    @Subscribe
    public void refreshMineMsgNum(MineMsgNumBean bean)
    {

        Integer[] values = new Integer[]{
                bean.getWDFB(),
                bean.getWDSP(),
                bean.getWDZG(),
                bean.getWDYS(),
                bean.getWDXA()
        };

        String[] tmps=new String[]{getString(R.string.my_send),getString(R.string.my_approval),getString(R.string.my_fix)
                ,getString(R.string.my_accept),getString(R.string.my_sales)};

        for(int i = 0;i < tmps.length;++i)
        {
            tabs[i] = tmps[i] + " " + values[i];
        }
        adapter.notifyDataSetChanged();
    }
}
