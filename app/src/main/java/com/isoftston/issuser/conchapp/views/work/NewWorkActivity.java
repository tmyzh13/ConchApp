package com.isoftston.issuser.conchapp.views.work;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.IMEUtil;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.views.security.ChoiceCheckPeopleActivity;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/11.
 */

public class NewWorkActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG =NewWorkActivity.class.getSimpleName() ;
    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.sb_class)
    InputView equipment_type;
    @Bind(R.id.sb_name)
    InputView equipment_name;
    @Bind(R.id.work_zone)
    InputView work_zone;
    @Bind(R.id.start_time)
    TextView tv_start_time;
    @Bind(R.id.end_time)
    TextView tv_end_time;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.tv_detail_name_content)
    TextView tv_detail_name_content;
    @Bind(R.id.ll_alter)
    LinearLayout ll_alter;
    @Bind(R.id.rl_agree)
    RelativeLayout rl_agree;
    @Bind(R.id.rl_check_people)
    RelativeLayout rl_check_people;
    @Bind(R.id.rl_keeper)
    RelativeLayout rl_keeper;
    private Context context =NewWorkActivity.this;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.new_work));
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        nav.showBack(2);
        setBarColor(getResources().getColor(R.color.transparent_black));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        equipment_type.setInputType(getString(R.string.equipment_type));
        equipment_name.setInputType(getString(R.string.equipment_name));
        work_zone.setInputType(getString(R.string.work_zone));
        tv_start_time.setText(now);
        tv_end_time.setText(now);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        rl_keeper.setOnClickListener(this);
        rl_agree.setOnClickListener(this);
        rl_check_people.setOnClickListener(this);
        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(et_name.getText().toString())){
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_detail_name_content.setText(et_name.getText().toString());
                    }else{
                        ll_alter.setVisibility(View.VISIBLE);
                    }
                    et_name.setVisibility(View.GONE);
                    IMEUtil.closeIME(et_name,context);
                    return true;
                }
                return false;
            }
        });
        et_name.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if(!TextUtils.isEmpty(et_name.getText().toString())){
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_detail_name_content.setText(et_name.getText().toString());
                    }else{
                        ll_alter.setVisibility(View.VISIBLE);
                    }
                    et_name.setVisibility(View.GONE);

                }
            }
        });
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
            case R.id.rl_agree:
            case R.id.rl_check_people:
            case R.id.rl_keeper:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context),100);
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
    @OnClick(R.id.ll_alter)
    public void alterNamebtn(){
        ll_alter.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_detail_name_content)
    public void alterNameText(){
        tv_detail_name_content.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
    }
}
