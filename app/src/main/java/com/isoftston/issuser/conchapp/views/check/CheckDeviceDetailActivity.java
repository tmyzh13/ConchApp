package com.isoftston.issuser.conchapp.views.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.presenter.CheckPresenter;
import com.isoftston.issuser.conchapp.views.WebActivity;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/12.
 */

public class CheckDeviceDetailActivity extends BaseActivity<CheckView,CheckPresenter> implements CheckView {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.text_normal)
    TextView text_normal;
    @Bind(R.id.tv_device_name)
    TextView tv_device_name;
    @Bind(R.id.tv_phone_num)
    TextView tv_phone_num;
    private Context context=CheckDeviceDetailActivity.this;
    private String descId;

    public static Intent getLauncher(Context context, DeviceBean bean){
        Intent intent =new Intent(context,CheckDeviceDetailActivity.class);
        Bundle bundle =new Bundle();
        bundle.putSerializable("bean",bean);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_device_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.danger_work));
        navBar.setColorRes(R.color.transparent);
        Bundle bundle=getIntent().getExtras();
        DeviceBean deviceBean= (DeviceBean) bundle.getSerializable("bean");
        if (deviceBean!=null){
            text_normal.setText(deviceBean.getEquipCode());
        }
//        String descId= deviceBean.getDescId();
//        if (descId!=null){
//            presenter.getDeviceInfo(descId);
//        }
        presenter.getOneDeviceInfo("1");


    }

    @Override
    protected CheckPresenter createPresenter() {
        return new CheckPresenter();
    }

    @OnClick({R.id.ll_safety_point,R.id.ll_damage_type,R.id.ll_accident,R.id.ll_accident_eg
                ,R.id.ll_check_rules,R.id.ll_manager_system,R.id.ll_approval_system,R.id.ll_eneger_system})
    public void goWebActivity(View view){
        switch (view.getId()){
            case R.id.ll_safety_point:
//                Log.e("yzh","ssssss");
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_safety_point),""));
                break;
            case R.id.ll_damage_type:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_damage_type),""));
                break;
            case R.id.ll_accident:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_accident),""));
                break;
            case R.id.ll_accident_eg:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_accident_eg),""));
                break;
            case R.id.ll_check_rules:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_action_rules),""));
                break;
            case R.id.ll_manager_system:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_manager_system),""));
                break;
            case R.id.ll_approval_system:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_approval_system),""));
                break;
            case R.id.ll_eneger_system:
                startActivity(WebActivity.getLauncher(context,getString(R.string.danger_work_eneger_system),""));
                break;
        }
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void renderDatas(boolean reload, List<DeviceBean> list) {

    }

    @Override
    public void checkDeviceResult(DeviceBean bean) {
        tv_device_name.setText(bean.getName());
        tv_phone_num.setText(bean.getCenterPhone());
        descId = bean.getDescId();
    }

    @Override
    public void checkDeviceResultError() {

    }

    @Override
    public void CheckAllDeviceResult(List<DeviceBean> deviceListBean) {


    }
}
