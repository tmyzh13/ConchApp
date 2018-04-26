package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckDeviceAdapter;
import com.isoftston.issuser.conchapp.adapters.CheckPeopleAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeRequstBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.views.work.NewWorkActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/25.
 */

public class ChoiceDeviceTypeActivity extends BaseActivity<WorkView, WorkPresenter> implements WorkView {


    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_seach)
    EditText et_seach;
    @Bind(R.id.lv_device)
    ListView lv_device;
    private CheckDeviceAdapter adapter;
    private String searchContent = "";
    private List<DeviceTypeBean> listdata;
    private List<DeviceTypeBean> listdata_next = new ArrayList<>();
    private Context context = ChoiceDeviceTypeActivity.this;
    private List<DeviceTypeBean> listtotal;


    public static Intent getLaucnher(Context context) {
        Intent intent = new Intent(context, ChoiceDeviceTypeActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_check_device;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.choice_device_type));
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);
        presenter.getDeviceType(new FixWorkBean());
        adapter = new CheckDeviceAdapter(context);
        listdata = new ArrayList<>();
//        adapter.addAll(listdata);
        lv_device.setAdapter(adapter);
        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int id = adapter.getItem(position).getId();
                listdata_next.clear();
                for (int i=0;i<listtotal.size();i++) {
                    if (listtotal.get(i).getCreateBy().equals(String.valueOf(id)) && listtotal.get(i).getId()!=id) {
                        listdata_next.add(listtotal.get(i));
                    }
                }
                Log.i("datss",listdata_next.size()+"");
                if (listdata_next != null && listdata_next.size() != 0) {
                    adapter.replaceAll(listdata_next);
//                    adapter.addAll(listdata_next);
                lv_device.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else {
                Intent intent =new Intent();
                intent.putExtra(Constant.CHECK_PEOPLE,adapter.getItem(position).getName());
                intent.putExtra(Constant.CHECK_DEVICE_ID,id);
                setResult(NewWorkActivity.CHOSE_DEVICE_CODE,intent);
                finish();
            }

            }
        });
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
        listtotal = list;
        listdata.clear();
        for (DeviceTypeBean bean : list) {
            if (bean.getLevel() == 1) {
                listdata.add(bean);
            }
        }
        adapter.addAll(listdata);
        lv_device.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDeviceDetailSuccess(List<DeviceDetailBean> list) {

    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach() {
        finish();
    }
}
