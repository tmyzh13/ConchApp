package com.isoftston.issuser.conchapp.views.security;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypePageAdapter;
import com.isoftston.issuser.conchapp.utils.Tools;

import butterknife.Bind;

/**
 * 隐患信息
 * Created by issuser on 2018/4/12.
 */

public class HiddenTroubleMessageFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    public String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hidden_trouble_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabs=new String[]{getString(R.string.not_alter),getString(R.string.overdue)
                ,getString(R.string.altered),getString(R.string.not_check)};
        final MessageTypePageAdapter adapter=new MessageTypePageAdapter(getActivity().getSupportFragmentManager(),tabs,0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Tools.setIndicator(tabLayout,10,10);
        RxBus.getDefault().toObservable(Object.class,"ssss")
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        Log.e("yzh","recevie");
                        tabs[1]=getString(R.string.overdue)+12;
                        adapter.setmTitles(tabs);
                        tabLayout.getTabAt(1).setText(tabs[1]);
                    }
                });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
