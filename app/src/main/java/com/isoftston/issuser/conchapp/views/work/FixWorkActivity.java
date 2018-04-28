package com.isoftston.issuser.conchapp.views.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeRequstBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.views.security.ChoiceCheckPeopleActivity;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/11.
 */

public class FixWorkActivity extends BaseActivity<WorkView,WorkPresenter> implements WorkView,View.OnClickListener {
    private static final int CHOSE_CHEKER_CODE = 11;
    private static final int CHOSE_AGREE_CODE = 12;
    private static final int CHOSE_KEPPER_CODE = 10;
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.bt_sure)
    Button bt_sure;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.tv_auditor)
    TextView tv_auditor;
    @Bind(R.id.tv_approver)
    TextView tv_approver;
    @Bind(R.id.tv_guardian)
    TextView tv_guardian;
    @Bind(R.id.et_num)
    EditText et_num;//作业人数
    @Bind(R.id.rl_approver)
    RelativeLayout rl_approver;//批准人
    @Bind(R.id.rl_auditor)
    RelativeLayout rl_auditor;//审核人
    @Bind(R.id.rl_guardian)
    RelativeLayout rl_guardian;//审核人
    private Context context = FixWorkActivity.this;
    private String jobId;
    private String chosedUserName;
    private String chosedUserId;
    private String tv_auditor_id;
    private String tv_guardian_id;
    private String tv_approver_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fix_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.colorPrimary);
        nav.setNavTitle(getString(R.string.fix_work));
        nav.hideBack();
        jobId = getIntent().getStringExtra("id");
        bt_sure.setOnClickListener(this);
        rl_approver.setOnClickListener(this);
        rl_auditor.setOnClickListener(this);
        rl_guardian.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
    }

    @Override
    protected WorkPresenter createPresenter() {
        return new WorkPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_sure:
                String startTimeStr = tv_start_time.getText().toString().trim();
                long startTime = TextUtils.isEmpty(String.valueOf(startTimeStr))? 0 : DateUtils.getDateToLongMS(startTimeStr);
                String endTimeStr = tv_end_time.getText().toString().trim();
                long endTime = TextUtils.isEmpty(String.valueOf(endTimeStr))? 0 : DateUtils.getDateToLongMS(endTimeStr);
                String numberPeople=et_num.getText().toString().trim();
                FixWorkBean bean=new FixWorkBean();
                bean.setId(jobId);
                bean.setStartTime(startTime);//
                bean.setEndTime(endTime);//
                bean.setGuardian(tv_guardian_id);
                bean.setAuditor(tv_auditor_id);
                bean.setApprover(tv_approver_id);
                bean.setNumberPeople(Integer.parseInt(numberPeople));
                presenter.fixWork(bean);
                break;
            case R.id.rl_approver:
                //审核人
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context,3), CHOSE_CHEKER_CODE);
                break;
            case R.id.rl_auditor:
                //批准人
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context,4), CHOSE_AGREE_CODE);
                break;
            case R.id.rl_guardian:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context,2), CHOSE_KEPPER_CODE);
                break;
            case R.id.tv_start_time:
                showDatePickerDialog(tv_start_time, 1);
                break;
            case R.id.tv_end_time:
                showDatePickerDialog(tv_end_time, 2);
                break;

        }
    }

    private void showDatePickerDialog(final TextView textView, final int i) {
        CustomDatePicker customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {

            private String starttime;
            private String endtime;

            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                try {
                    if (i == 1) {
                        starttime = DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time));
                        endtime = tv_end_time.getText().toString();
                    } else {
                        starttime = tv_start_time.getText().toString();
                        endtime = DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time));
                    }
                    if (isDateOneBigger(starttime, endtime)) {
                        Toast.makeText(context, R.string.information, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    textView.setText(DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time)));
                    textView.setTextColor(getResources().getColor(R.color.black));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "1970-01-01 00:00", "2099-12-12 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 不显示时和分
        //customDatePicker.showYearMonth();
        customDatePicker.setIsLoop(false); // 不允许循环滚动
        //customDatePicker.show(dateText.getText().toString() + " " + timeText.getText().toString());
        customDatePicker.show(DateUtils.format_yyyy_MM_dd_HH_mm.format(new Date()));
    }

    private boolean isDateOneBigger(String starttime, String endtime) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(starttime);
            dt2 = sdf.parse(endtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((dt1.getTime() - dt2.getTime()) > 0) {
            isBigger = true;
        } else {
            isBigger = false;
        }
        return isBigger;
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

        ToastUtils.showtoast(FixWorkActivity.this,getString(R.string.modify_failed));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==NewWorkActivity.CHOSE_CHARGER_CODE){
            chosedUserName = data.getStringExtra(Constant.CHECK_PEOPLE);
            chosedUserId = data.getStringExtra(Constant.CHECK_PEOPLE_ID);
            switch (requestCode){
                case CHOSE_AGREE_CODE:
                    tv_auditor.setText(chosedUserName);
                    tv_auditor_id = chosedUserId;
                    break;
                case  CHOSE_KEPPER_CODE:
                    tv_guardian.setText(chosedUserName);
                    tv_guardian_id =chosedUserId;
                    break;
                case CHOSE_CHEKER_CODE:
                    tv_approver.setText(chosedUserName);
                    tv_approver_id =chosedUserId;
                    break;
            }
        }

    }
}
