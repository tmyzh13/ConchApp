package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.isoftston.issuser.conchapp.weight.ChooseListPopupWindow;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2018/4/15.
 */

public class AddIllegalActivity extends BaseActivity<SecuryView,SecurityPresenter> implements SecuryView {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_illegal_name)
    InputView input_illegal_name;
    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.input_find_company)
    InputView input_find_company;
    @Bind(R.id.input_illegal_company)
    InputView input_illegal_company;
    @Bind(R.id.tv_check_people)
    TextView tv_check_people;
    @Bind(R.id.tv_illegal_type)
    TextView tv_illegal_type;
    @Bind(R.id.input_illegal_place)
    InputView input_illegal_place;
    @Bind(R.id.et_description)
    EditText et_description;
    @Bind(R.id.rl_description)
    RelativeLayout rl_description;
    @Bind(R.id.ll_description)
    LinearLayout ll_description;
    @Bind(R.id.tv_illegal_describ_title)
    TextView tv_illegal_describ_title;
    @Bind(R.id.tv_illegal_descibe_content)
    TextView tv_illegal_descibe_content;

    private Context context=AddIllegalActivity.this;
    private String startTime,endTime;
    private ChooseListPopupWindow window;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,AddIllegalActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_illegal;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.illegal_problem));
        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        navBar.showBack(2);
        setBarColor(getResources().getColor(R.color.transparent_black));

        //设置栏目标题
        input_illegal_name.setInputText(getString(R.string.illegal_detail_name),null);
        input_find_company.setInputText(getString(R.string.hidden_trouble_find_company),null);
        input_illegal_company.setInputText(getString(R.string.illeagl_company),null);
        input_illegal_place.setInputText(getString(R.string.illegal_place),null);

        startTime= Tools.getCurrentTime();
        endTime=Tools.getCurrentTime();
        tv_start_time.setText(startTime);
        tv_end_time.setText(endTime);

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

    @OnClick(R.id.ll_confirm)
    public void confirmInfo(){
        AddYHBean bean=new AddYHBean();
//        bean.setYhmc(input_illegal_name.getContent());
        bean.setYhmc(input_illegal_name.getContent());
        bean.setGsId("1");
        bean.setJcdwid("1");
        bean.setJcdwmc(input_find_company.getContent());
        bean.setSjdwid("1");
        bean.setSjdwmc(input_illegal_company.getContent());
        bean.setYhly("1");
        bean.setFxrmc(tv_check_people.getText().toString());
        bean.setFxrId("1");
        bean.setFxrq(tv_start_time.getText().toString());
        bean.setCjsj(tv_end_time.getText().toString());
        bean.setYhlx("1");
        bean.setYhjb("1");
        bean.setYhdd(input_illegal_place.getContent());
        bean.setYhbw("1");
        bean.setYhlbb("1");
        bean.setSfxczg("1");
        bean.setYhms(et_description.getText().toString());
        bean.setTplj(picString);
        bean.setKnzchg("1");
        bean.setIschoose("1");
        presenter.addWZMessage(bean);
        finish();
    }

    private HashMap<String, String> map = new HashMap<>();
    private String picString;

    @OnClick(R.id.rl_photo)
    public void choicePhoto(){
        startActivityForResult(ChoicePhotoActivity.getLauncher(context,"0",map),110);
    }


    @OnClick(R.id.ll_check_people)
    public void choiceCheckPeople(){
        startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context,7),100);
    }

    @OnClick(R.id.tv_illegal_type)
    public void choiceType(){
        if (window == null){
            window = new ChooseListPopupWindow(context);
            List<String> list =new ArrayList<>();
            list.add("类型一");
            list.add("类型二");
            list.add("类型三");
            window.setDatas(list);
            window.setPopOnItemClickListener(new ChooseListPopupWindow.PopOnItemClick() {
                @Override
                public void choice(String content) {
                    tv_illegal_type.setText(content);
                }
            });
        }



        window.showAtBottom(navBar);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(requestCode==10){
                String result=data.getStringExtra(Constant.CHECK_PEOPLE);
                if(!TextUtils.isEmpty(result)){
                    tv_check_people.setText(result);
                }

            }
        }else if(requestCode==110){
            if(resultCode==10){
                map = (HashMap<String, String>) data.getSerializableExtra(Constant.TEMP_PIC_LIST);
                StringBuilder builder = new StringBuilder();
                for (String path : map.values()){
                    builder.append(path);
                    builder.append(",");
                }
                picString = builder.toString();
            }
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
                        Toast.makeText(AddIllegalActivity.this,R.string.hidden_info,Toast.LENGTH_SHORT).show();
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
