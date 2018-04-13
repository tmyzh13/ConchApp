package com.isoftston.issuser.conchapp.views.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.views.WebActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/12.
 */

public class CheckDeviceDetailActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;

    private Context context=CheckDeviceDetailActivity.this;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,CheckDeviceDetailActivity.class);
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
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
}
