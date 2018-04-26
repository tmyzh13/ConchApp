package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeRequstBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/11.
 */

public class FixWorkActivity extends BaseActivity<WorkView,WorkPresenter> implements WorkView,View.OnClickListener {
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.bt_sure)
    Button bt_sure;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.et_guardian)
    EditText et_guardian;
    @Bind(R.id.tv_auditor)
    TextView tv_auditor;
    @Bind(R.id.tv_approver)
    TextView tv_approver;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_fix_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.colorPrimary);
        nav.setNavTitle(getString(R.string.fix_work));
        nav.hideBack();
        bt_sure.setOnClickListener(this);
    }

    @Override
    protected WorkPresenter createPresenter() {
        return new WorkPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_sure:
                FixWorkBean bean=new FixWorkBean();
                bean.setId("1010");
                bean.setStartTime(1524473386515l);//tv_start_time.getText()
                bean.setEndTime(1524473386515l);//tv_end_time.getText()
                bean.setGuardian(et_guardian.getText().toString());
                bean.setAuditor(tv_auditor.getText().toString());
                bean.setApprover(tv_approver.getText().toString());
                presenter.fixWork(bean);
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
        ToastUtils.showtoast(FixWorkActivity.this,getString(R.string.fix_success));
        finish();
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
}
