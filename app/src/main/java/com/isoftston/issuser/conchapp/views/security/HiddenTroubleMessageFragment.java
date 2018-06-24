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
import com.isoftston.issuser.conchapp.model.bean.HiddenTroubleMsgNumBean;
import com.isoftston.issuser.conchapp.utils.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private MessageTypePageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hidden_trouble_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        tabs = new String[]{getString(R.string.alls),getString(R.string.not_alter),getString(R.string.overdue)
                ,getString(R.string.unapproval),getString(R.string.no_send),getString(R.string.not_check),getString(R.string.unsales),getString(R.string.altered)};
        adapter =new MessageTypePageAdapter(getActivity().getSupportFragmentManager(),tabs,0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        Tools.setIndicator(tabLayout,15,15);
//        RxBus.getDefault().toObservable(Object.class,"ssss")
//                .compose(this.bindToLifecycle())
//                .subscribe(new RxBusSubscriber<Object>() {
//                    @Override
//                    public void receive(Object data) {
//                        Log.e("yzh","recevie");
//                        tabs[1]=getString(R.string.overdue);
//                        adapter.setmTitles(tabs);
//                        tabLayout.getTabAt(1).setText(tabs[1]);
//                    }
//                });
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
    public void refreshHiddenNum(HiddenTroubleMsgNumBean bean)
    {

        Integer[] values = new Integer[]{
                bean.getAll(),
                bean.getWzg(),
                bean.getYq(),
                bean.getWsp(),
                bean.getWfs(),
                bean.getWys(),
                bean.getWxa(),
                bean.getYzg()
        };

        String[] tmps=new String[]{getString(R.string.alls),getString(R.string.not_alter),getString(R.string.overdue)
                ,getString(R.string.unapproval),getString(R.string.no_send),getString(R.string.not_check),getString(R.string.unsales),getString(R.string.altered)};

        for(int i = 0;i < tmps.length;++i)
        {
            tabs[i] = tmps[i] + " " + values[i];
        }
        adapter.notifyDataSetChanged();
    }
}
