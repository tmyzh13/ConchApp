package com.isoftston.issuser.conchapp.views.work;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/11.
 */

public class NewWorkActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG =NewWorkActivity.class.getSimpleName() ;
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.jh_manager)
    InputView jh_manager;
    @Bind(R.id.sh_manager)
    InputView sh_manager;
    @Bind(R.id.pz_manager)
    InputView pz_manager;
    @Bind(R.id.sb_class)
    InputView equipment_type;
    @Bind(R.id.sb_number)
    InputView equipment_number;
    @Bind(R.id.sb_name)
    InputView equipment_name;
    @Bind(R.id.work_zone)
    InputView work_zone;
    @Bind(R.id.start_time)
    TextView tv_start_time;
    @Bind(R.id.end_time)
    TextView tv_end_time;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.colorPrimary);
        nav.setNavTitle(getString(R.string.new_work));
        nav.hideBack();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        jh_manager.setInputType(getString(R.string.keeper));
        sh_manager.setInputType(getString(R.string.checker));
        pz_manager.setInputType(getString(R.string.authorize));
        equipment_type.setInputType(getString(R.string.equipment_type));
        equipment_number.setInputType(getString(R.string.equipment_number));
        equipment_name.setInputType(getString(R.string.equipment_name));
        work_zone.setInputType(getString(R.string.work_zone));
        tv_start_time.setText(now);
        tv_end_time.setText(now);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_time:
                showDatePickerDialog(tv_start_time,1);
                break;
            case R.id.end_time:
                showDatePickerDialog(tv_end_time,2);
                break;
        }
    }

    private boolean isDateOneBigger(String beginDateTime, String endDateTime) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(beginDateTime);
            dt2 = sdf.parse(endDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((dt1.getTime() - dt2.getTime())>0) {
            isBigger = true;
        } else  {
            isBigger = false;
        }
        return isBigger;
    }


    private void showDatePickerDialog(final TextView textView, final int i) {


        CustomDatePicker customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {

            private String starttime;
            private String endtime;

            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                try {
                    if (i==1){
                        starttime = DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time));
                        endtime=tv_end_time.getText().toString();
                    }else {
                        starttime = tv_start_time.getText().toString();
                        endtime=DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time));
                    }
                    if (isDateOneBigger(starttime,endtime)){
                        Toast.makeText(NewWorkActivity.this,R.string.information,Toast.LENGTH_SHORT).show();
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
}
