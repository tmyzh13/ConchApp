package com.isoftston.issuser.conchapp.views.check;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.DeviceAdapter;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class CheckFragment extends BaseFragment {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.lv_device)
    AutoLoadMoreListView lv_device;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    private DeviceAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_check;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        navBar.setNavTitle(getString(R.string.check_manager));
        navBar.hideBack();

        List<DeviceBean> list =new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(new DeviceBean());
        }

        adapter =new DeviceAdapter(getContext());
        adapter.addAll(list);
        lv_device.setAdapter(adapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);

        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(CheckDeviceDetailActivity.getLauncher(getContext()));
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
