package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.CountBean;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkCountBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkRequestCountBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/16.
 */

public class MineMessageFragment extends BaseFragment<WorkView, WorkPresenter> implements WorkView {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerMy)
    ViewPager viewPager;
    public List<String> tabs = new ArrayList<>();
    public List<String> tabList = new ArrayList<>();
    private WorkMessageAdapter adapter;
    private List<CountBean> countBeanlist;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tabs.add(getString(R.string.my_approve));
        tabs.add(getString(R.string.my_check));
        tabs.add(getString(R.string.my_release));
        adapter = new WorkMessageAdapter(getActivity().getSupportFragmentManager(), tabs, 2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Tools.setIndicator(tabLayout, 10, 10);
        WorkRequestCountBean bean = new WorkRequestCountBean();
        bean.setType("2");
        presenter.getWorkCount(bean);
    }

    @Override
    public void onResume() {
        super.onResume();
        WorkRequestCountBean bean = new WorkRequestCountBean();
        bean.setType("2");
        presenter.getWorkCount(bean);
    }

    @Override
    protected WorkPresenter createPresenter() {
        return new WorkPresenter();
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void renderData(WorkBean workBean) {

    }

    @Override
    public void getWorkListInfo(List<WorkBean> list) {

    }

    @Override
    public void getWorkList(List<WorkDetailBean> list) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void addWorkSuccess() {

    }

    @Override
    public void getDangerWorkTypeResult(List<DangerTypeBean> list) {

    }

    @Override
    public void getDeviceTypeResult(List<DeviceTypeBean> list) {

    }

    @Override
    public void getDeviceDetailSuccess(List<DeviceDetailBean> list) {

    }

    @Override
    public void getWorkCountSuccess(List<WorkCountBean> list) {
        tabList.clear();
        if (list != null && list.size() != 0) {
            countBeanlist=list.get(2).getList();
            for(int i = 0;i < countBeanlist.size();++i)
            {
                tabList.add(tabs.get(i)+" "+countBeanlist.get(i).getCount());
            }
            Log.i("ss","--sss--"+tabList.size());
            adapter = new WorkMessageAdapter(getActivity().getSupportFragmentManager(), tabList, 2);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            adapter.notifyDataSetChanged();
        }
    }
}
