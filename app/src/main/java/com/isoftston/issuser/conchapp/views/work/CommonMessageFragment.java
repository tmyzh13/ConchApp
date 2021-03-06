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

public class CommonMessageFragment extends BaseFragment<WorkView,WorkPresenter> implements WorkView {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPagerCommon)
    ViewPager viewPager;
    public List<String> tabs=new ArrayList<>();
    public List<String> tabList=new ArrayList<>();
    private WorkMessageAdapter adapter;
    private List<CountBean> countBeanlist;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        presenter.getWorkInfo();
        WorkRequestCountBean bean=new WorkRequestCountBean();
        bean.setType("0");
        presenter.getWorkCount(bean);
        tabs.add(getString(R.string.all));
        adapter = new WorkMessageAdapter(getActivity().getSupportFragmentManager(),tabs, 0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        Tools.setIndicator(tabLayout,10,10);
    }

    @Override
    public void onResume() {
        super.onResume();
        WorkRequestCountBean bean=new WorkRequestCountBean();
        bean.setType("0");
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
        hideLoading();
        if (list.size()==0){
            return;
        }
        tabs.clear();
        tabs.add(getString(R.string.all));
        if (list!=null&&list.size()!=0){
            for (int i=0;i<list.size();i++){
                String name=list.get(i).getName();
                if (name!=null){
                    tabs.add(name);
                }
            }
            viewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getWorkList(List<WorkDetailBean> list) {

    }

    @Override
    public void getWorkError() {
        hideLoading();
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
        Log.i("zt","--zt--"+list.size());
        tabList.clear();
        if(list!=null&& list.size()!=0){
            countBeanlist=list.get(0).getList();
            tabList.add(tabs.get(0)+" "+list.get(0).getTotal());
            for (int i=0;i<countBeanlist.size();i++){
                tabList.add(tabs.get(i+1)+" "+countBeanlist.get(i).getCount());
            }
            adapter = new WorkMessageAdapter(getActivity().getSupportFragmentManager(), tabList, 0);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
//            if (tabList.size()>6){
//                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//            }else {
//                tabLayout.setTabMode(TabLayout.MODE_FIXED);
//            }
            adapter.notifyDataSetChanged();
        }

    }
}
