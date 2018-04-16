package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.RxBusSubscriber;
import com.corelibs.utils.rxbus.RxBus;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkMessageAdapter;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/16.
 */

public class DangerMessageFragment extends BaseFragment {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerDanger)
    ViewPager viewPager;
    public String[] tabs;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_danger_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabs=new String[]{getString(R.string.all), getString(R.string.shuini), getString(R.string.matou),getString(R.string.kuangshan),getString(R.string.build)};
        final WorkMessageAdapter adapter=new WorkMessageAdapter(getActivity().getSupportFragmentManager(),tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Tools.setIndicator(tabLayout,10,10);
        RxBus.getDefault().toObservable(Object.class,"ssss")
                .compose(this.bindToLifecycle())
                .subscribe(new RxBusSubscriber<Object>() {
                    @Override
                    public void receive(Object data) {
                        tabs[1]=getString(R.string.shuini)+12;
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
