package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.IMEUtil;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.work.NewWorkActivity;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/10.
 */

public class AddHiddenTroubleActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_find_company)
    InputView input_find_company;
    @Bind(R.id.input_trouble_company)
    InputView input_trouble_company;
    @Bind(R.id.input_check_worker)
    InputView input_check_worker;
    @Bind(R.id.input_place)
    InputView input_place;
    @Bind(R.id.input_position)
    InputView input_position;
    @Bind(R.id.input_source)
    InputView input_source;
    @Bind(R.id.tv_detail_name_content)
    TextView tv_detail_name_content;
    @Bind(R.id.tv_trouble_name)
    TextView tv_trouble_name;
    @Bind(R.id.ll_alter)
    LinearLayout ll_alter;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.tv_photo)
    TextView tv_photo;
    @Bind(R.id.ll_range)
    LinearLayout ll_range;
    @Bind(R.id.input_illegal_type)
    InputView input_illegal_type;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;

    private Context context =AddHiddenTroubleActivity.this;
    //0隐患 1违章
    private String type;

    public static Intent getLauncher(Context context,String type){
        Intent intent =new Intent(context,AddHiddenTroubleActivity.class);
        intent.putExtra("type",type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_hidden_trouble;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        type=getIntent().getStringExtra("type");
        if(type.equals("0")){
            //隐患
            navBar.setNavTitle(getString(R.string.hidden_trouble));
            tv_trouble_name.setText(getString(R.string.hidden_trouble_detail_name));
            input_trouble_company.setInputType(getString(R.string.hidden_trouble_company));
            input_place.setInputType(getString(R.string.hidden_trouble_place));
            input_position.setInputType(getString(R.string.hidden_trouble_position));
            tv_photo.setText(getString(R.string.hidden_trouble_photo));
            input_source.setInputType(getString(R.string.hidden_trouble_source));
            ll_range.setVisibility(View.VISIBLE);
        }else{
            //违章
            navBar.setNavTitle(getString(R.string.illegal));
            tv_trouble_name.setText(getString(R.string.illegal_detail_name));
            input_trouble_company.setInputType(getString(R.string.illeagl_company));
            input_place.setInputType(getString(R.string.illegal_place));
            input_position.setInputType(getString(R.string.illegal_description));
            tv_photo.setText(getString(R.string.illegal_photo));
            input_source.setInputType(getString(R.string.illegal_report));
            input_illegal_type.setVisibility(View.VISIBLE);
            input_illegal_type.setInputType(getString(R.string.illegal_type));
        }

        input_find_company.setInputType(getString(R.string.hidden_trouble_find_company));
        input_check_worker.setInputType(getString(R.string.hidden_trouble_check));


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

        starttime= Tools.getCurrentTime();
        endtime=Tools.getCurrentTime();
        tv_start_time.setText(starttime);
        tv_end_time.setText(endtime);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.ll_photo)
    public void goPhoto(){
        //进入照片选择界面
        startActivity(ChoicePhotoActivity.getLauncher(context));
    }

    @OnClick(R.id.ll_alter)
    public void alterNamebtn(){
        ll_alter.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
        et_name.setFocusable(true);
        et_name.setFocusableInTouchMode(true);
        et_name.requestFocus();
    }

    @OnClick(R.id.tv_detail_name_content)
    public void alterNameText(){
        tv_detail_name_content.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
        et_name.setFocusable(true);
        et_name.setFocusableInTouchMode(true);
        et_name.requestFocus();
    }

    @OnClick(R.id.ll_confirm)
    public void confirmInfo(){
        //提交新增信息 暂时结束页面
        finish();
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

    @OnClick(R.id.tv_start_time)
    public void choiceStartTime(){
        showDatePickerDialog(tv_start_time,1);
    }

    @OnClick(R.id.tv_end_time)
    public void choiceEndTime(){
        showDatePickerDialog(tv_end_time,2);
    }

    private String starttime;
    private String endtime;

    private void showDatePickerDialog(final TextView textView, final int i) {


        CustomDatePicker customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {



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
                        Toast.makeText(AddHiddenTroubleActivity.this,R.string.hidden_info,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    textView.setText(DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time)));
//                    textView.setTextColor(getResources().getColor(R.color.black));
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
