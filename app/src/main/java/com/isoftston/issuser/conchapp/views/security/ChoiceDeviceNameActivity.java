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
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckDeviceAdapter;
import com.isoftston.issuser.conchapp.adapters.DeviceNameAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceNameBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceNameCodeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
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

public class ChoiceDeviceNameActivity extends BaseActivity<WorkView, WorkPresenter> implements WorkView {


    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_seach)
    EditText et_seach;
    @Bind(R.id.lv_device)
    ListView lv_device;
    private DeviceNameAdapter adapter;
    private String searchContent = "";
    private List<DeviceDetailBean> listdata;
    private Context context = ChoiceDeviceNameActivity.this;


    public static Intent getLaucnher(Context context) {
        Intent intent = new Intent(context, ChoiceDeviceNameActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_check_device;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String device_id=getIntent().getStringExtra("device_id");
        nav.setNavTitle(getString(R.string.choice_check_people));
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);
        if (device_id!=null){
            DeviceNameBean bean=new DeviceNameBean();
            bean.setType(device_id);
            presenter.getDeviceName(bean);
        }
        adapter = new DeviceNameAdapter(context);
        listdata = new ArrayList<>();
        adapter.addAll(listdata);
        lv_device.setAdapter(adapter);
        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent =new Intent();
                intent.putExtra(Constant.CHECK_PEOPLE,adapter.getItem(position).getName());
                intent.putExtra(Constant.CHECK_DEVICE_TYPE,listdata.get(position).getEquipCode());
                setResult(NewWorkActivity.CHOSE_NAME_CODE,intent);
                finish();
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

    }

    @Override
    public void getDeviceDetailSuccess(List<DeviceDetailBean> list) {
        for (int i=0;i<list.size();i++){
            listdata.add(list.get(i));
        }
        adapter.addAll(listdata);
        lv_device.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach() {
        finish();
    }
}
