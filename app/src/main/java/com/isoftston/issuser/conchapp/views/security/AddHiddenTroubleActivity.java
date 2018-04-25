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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.IMEUtil;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.AddYHBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.views.work.NewWorkActivity;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/10.
 */

public class AddHiddenTroubleActivity extends BaseActivity<SecuryView,SecurityPresenter> implements SecuryView  {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_trouble_name)
    InputView input_trouble_name;
    @Bind(R.id.input_find_company)
    InputView input_find_company;
    @Bind(R.id.input_trouble_company)
    InputView input_trouble_company;
    @Bind(R.id.tv_check_people)
   TextView tv_check_people;
    @Bind(R.id.input_place)
    InputView input_place;
    @Bind(R.id.input_position)
    InputView input_position;
    @Bind(R.id.input_source)
    InputView input_source;
    @Bind(R.id.et_yh_description)
    EditText et_description;
    @Bind(R.id.rl_description)
    RelativeLayout rl_description;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.ll_description)
    LinearLayout ll_description;
    @Bind(R.id.tv_illegal_describ_title)
    TextView tv_illegal_describ_title;
    @Bind(R.id.tv_illegal_descibe_content)
    TextView tv_illegal_descibe_content;
    public String startTime,endTime;

    private Context context =AddHiddenTroubleActivity.this;
    //0隐患 1违章
    private String type;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,AddHiddenTroubleActivity.class);
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
        navBar.showBack(2);
        setBarColor(getResources().getColor(R.color.transparent_black));

            //隐患
            navBar.setNavTitle(getString(R.string.hidden_trouble));
            input_trouble_name.setInputText(getString(R.string.hidden_trouble_detail_name),null);
            input_trouble_company.setInputText(getString(R.string.hidden_trouble_company),null);
            input_place.setInputText(getString(R.string.hidden_trouble_place),null);
            input_position.setInputText(getString(R.string.hidden_trouble_position),null);
            input_source.setInputText(getString(R.string.hidden_trouble_source),null);


        input_find_company.setInputText(getString(R.string.hidden_trouble_find_company),null);
        starttime= Tools.getCurrentTime();
        endtime=Tools.getCurrentTime();
        tv_start_time.setText(starttime);
        tv_end_time.setText(endtime);
        et_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if(!TextUtils.isEmpty(et_description.getText().toString())){
                        tv_illegal_descibe_content.setText(et_description.getText().toString());
                    }else{
                        tv_illegal_descibe_content.setText("");
                    }
                    et_description.setVisibility(View.GONE);
                    ll_description.setVisibility(View.VISIBLE);
                    rl_description.setBackgroundColor(getResources().getColor(R.color.transparent));
                    tv_illegal_describ_title.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        et_description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(et_description.getText().toString())){

                        tv_illegal_descibe_content.setText(et_description.getText().toString());
                    }else{
                        tv_illegal_descibe_content.setText("");
                    }
                    et_description.setVisibility(View.GONE);
                    ll_description.setVisibility(View.VISIBLE);
                    rl_description.setBackgroundColor(getResources().getColor(R.color.transparent));
                    tv_illegal_describ_title.setTextColor(getResources().getColor(R.color.black));
                    IMEUtil.closeIME(et_description,context);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }

    @OnClick(R.id.rl_check_people)
    public void choiceCheckPeople(){
        startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context,6),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==10){
                String result=data.getStringExtra(Constant.CHECK_PEOPLE);
                if(!TextUtils.isEmpty(result)){
                    tv_check_people.setText(result);
                }
            }
        }else if(requestCode==110){
            if(resultCode==10){
                list=data.getStringArrayListExtra(Constant.TEMP_PIC_LIST);
            }
        }
    }
    private ArrayList<String> list=new ArrayList<>();

    @OnClick(R.id.rl_photo)
    public void goPhoto(){
        //进入照片选择界面
        startActivityForResult(ChoicePhotoActivity.getLauncher(context,"0",list),110);
    }


    @OnClick(R.id.ll_description)
    public void choiceDescription(){
        ll_description.setVisibility(View.GONE);
        et_description.setVisibility(View.VISIBLE);
        et_description.setFocusable(true);
        et_description.setFocusableInTouchMode(true);
        et_description.requestFocus();
        rl_description.setBackgroundResource(R.drawable.ll_input_selector_bg);
        tv_illegal_describ_title.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick(R.id.ll_confirm)
    public void confirmInfo(){
        //提交新增信息 暂时结束页面
        AddYHBean bean=new AddYHBean();
//        bean.setYhmc(input_trouble_name.getContent());
        bean.setYhmc("1");
        bean.setGsId("1");
        bean.setJcdwid("1");
        bean.setJcdwmc("1");
        bean.setSjdwid("1");
        bean.setSjdwmc("1");
        bean.setYhly("1");
        bean.setFxrmc("1");
        bean.setFxrId("1");
        bean.setFxrq("1");
        bean.setCjsj("1");
        bean.setYhlx("1");
        bean.setYhjb("1");
        bean.setYhdd("1");
        bean.setYhbw("1");
        bean.setYhlbb("1");
        bean.setSfxczg("1");
        bean.setYhms("1");
        bean.setTplj("1");
        bean.setKnzchg("1");
        presenter.addYHMessage(bean);
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

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void addSuccess() {
        ToastMgr.show(getString(R.string.submit_success));
        finish();

    }

    @Override
    public void getSafeListSuccess(SafeListBean data) {

    }

    @Override
    public void addFailed() {

    }
}
