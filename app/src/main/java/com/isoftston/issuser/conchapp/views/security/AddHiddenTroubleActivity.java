package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.IMEUtil;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.AddYHBean;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.SecurityUpdateBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.YhlxBean;
import com.isoftston.issuser.conchapp.model.bean.YhlyBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    @Bind(R.id.tv_yh_type)
    TextView tv_yh_type;
    @Bind(R.id.tv_check_people)
    TextView tv_check_people;
    @Bind(R.id.input_place)
    InputView input_place;
    @Bind(R.id.input_position)
    InputView input_position;

    @Bind(R.id.et_yh_description)
    EditText et_description;
    @Bind(R.id.rl_description)
    RelativeLayout rl_description;

    @Bind(R.id.ll_description)
    LinearLayout ll_description;
    @Bind(R.id.tv_illegal_describ_title)
    TextView tv_illegal_describ_title;
    @Bind(R.id.tv_illegal_descibe_content)
    TextView tv_illegal_descibe_content;

    @Bind(R.id.spinner3)
    Spinner fromSpinner;

    @Bind(R.id.spinnerGrade)
    Spinner gradeSpinner;

    @Bind(R.id.fix_yes)
    RadioButton fix_yes;
    @Bind(R.id.fix_no)
    RadioButton fix_no;

    @Bind(R.id.tv_find_company)
    TextView tv_find_company;

    @Bind(R.id.tv_trouble_company)
    TextView tv_trouble_company;

    @Bind(R.id.ll_isFix)
    LinearLayout isFixLayOut;

    public String startTime,endTime;
    private List<String> findCompanyList=new ArrayList<>();
    private List<String> checkCompanyList=new ArrayList<>();
    private List<String> fromList=new ArrayList<>();
    private List<String> fromListId=new ArrayList<>();
    private List<String> gradeList=new ArrayList<>();
    private List<String> gradeListId=new ArrayList<>();
    private Context context =AddHiddenTroubleActivity.this;
    //0隐患 1违章
    private String type;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> comAdapter;
    private ArrayAdapter<String> fromAdapter;
    private ArrayAdapter<String> gradeAdapter;
    private String find_company;
    private String yh_company;
    private String yh_from;
    private String yh_from_id;
    private String yh_lx_id;
    private String yh_grade=null;
    private String yh_grade_name=null;
    private String fix;
    private List<OrgBean> org=new ArrayList<>();
    private String find_company_id;
    private String yh_company_id;
    private String check_peole_id;

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

            input_place.setInputText(getString(R.string.hidden_trouble_place),null);
            input_position.setInputText(getString(R.string.hidden_trouble_position),null);



//        input_find_company.setInputText(getString(R.string.hidden_trouble_find_company),null);

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
        presenter.getCompanyChoiceList();
        //获取人员信息
        presenter.getUserInfo();
        initSpinner();
        //样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。


    }

    private void initSpinner() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, findCompanyList);
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        comAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, checkCompanyList);
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        comAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromList);
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        fromSpinner.setAdapter(fromAdapter);
        fromSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yh_from = fromAdapter.getItem(i).toString();
                yh_from_id = fromListId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gradeList);
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        gradeSpinner.setAdapter(gradeAdapter);
        gradeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yh_grade_name = gradeAdapter.getItem(i).toString();
                yh_grade = gradeListId.get(i);

                //重大隐患无法整改
                if(yh_grade_name.equals(getString(R.string.hidden_trouble_major_full)))
                {
                    isFixLayOut.setVisibility(View.GONE);
                }
                else
                {
                    isFixLayOut.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    @OnClick(R.id.ll_yh_type)
    public void choiceType(){
        startActivityForResult(ChoiceTypeActivity.getLaucnher(context,1),110);
    }

    @OnClick(R.id.rl_find_company)
    public void findOrg(){
        startActivityForResult(OrgActivity.getLaucnher(context,0),120);
    }

    @OnClick(R.id.rl_trouble_company)
    public void findTroubleOrg(){
        startActivityForResult(OrgActivity.getLaucnher(context,1),120);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==100){
                String result=data.getStringExtra(Constant.CHECK_PEOPLE);
                check_peole_id = data.getStringExtra(Constant.CHECK_PEOPLE_ID);
                if(!TextUtils.isEmpty(result)){
                    tv_check_people.setText(result);
                }
            }
        }else if(requestCode==110){
            if(resultCode==10){
                map= (HashMap<String, String>) data.getSerializableExtra(Constant.TEMP_PIC_LIST);
                StringBuilder builder = new StringBuilder();
                for (String path : map.values()){
                    builder.append(path);
                    builder.append(",");
                }
                picString = builder.toString();
            }else if (resultCode==104){
                String name=data.getStringExtra(Constant.CHECK_PEOPLE);
                String id=data.getStringExtra(Constant.CHECK_PEOPLE_ID);
                tv_yh_type.setText(name);
                yh_lx_id = id;
            }
        }else if(requestCode==120)
        {
            if(resultCode == 130)
            {
                find_company_id=data.getStringExtra(Constant.FIND_COMPANY_ID);
                find_company = data.getStringExtra(Constant.FIND_COMPANY_NAME);
                tv_find_company.setText(find_company);

            }
            else if(resultCode == 131)
            {
                yh_company_id = data.getStringExtra(Constant.DANGER_COMPANY_ID);
                yh_company = data.getStringExtra(Constant.DANGER_COMPANY_NAME);
                tv_trouble_company.setText(yh_company);
            }
        }
    }
    private HashMap<String, String> map =new HashMap<>();
    private String picString;

    @OnClick(R.id.rl_photo)
    public void goPhoto(){
        //进入照片选择界面
        startActivityForResult(ChoicePhotoActivity.getLauncher(context,"0",map,0),110);
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
        getLoadingDialog().show();
        String yh_name=input_trouble_name.getContent().trim();

        String check_people=tv_check_people.getText().toString();

        String yh_address=input_place.getContent().trim();
        String yh_position=input_position.getContent().trim();
        String yh_type=tv_yh_type.getText().toString();
        String yh_describle=et_description.getText().toString().trim();
        if (fix_yes.isChecked()){
            fix = fix_yes.getText().toString();
        }else if (fix_no.isChecked()){
            fix=fix_no.getText().toString();
        }
        if (TextUtils.isEmpty(yh_name)||TextUtils.isEmpty(find_company)||TextUtils.isEmpty(yh_company)||
                TextUtils.isEmpty(check_people)||TextUtils.isEmpty(yh_grade)||TextUtils.isEmpty(yh_address)||
                TextUtils.isEmpty(yh_position)||TextUtils.isEmpty(yh_from)||TextUtils.isEmpty(yh_type)
                ||(TextUtils.isEmpty(fix) && !yh_grade_name.equals(getString(R.string.hidden_trouble_major_full)))||TextUtils.isEmpty(yh_describle)||(TextUtils.isEmpty(yh_grade_name))||TextUtils.isEmpty(yh_grade)||TextUtils.isEmpty(yh_from_id)||tv_yh_type.equals(getResources().getString(R.string.input_text))
                ||tv_find_company.equals(getResources().getString(R.string.input_text)) || tv_trouble_company.equals(getResources().getString(R.string.input_text))){
            ToastMgr.show(R.string.input_all_message);
            getLoadingDialog().dismiss();
            return;
        }


        AddYHBean bean=new AddYHBean();
        bean.setYhmc(yh_name);
//        bean.setGsId("1");
        bean.setJcdwid(find_company_id);
        bean.setJcdwmc(find_company);
        bean.setSjdwid(yh_company_id);
        bean.setSjdwmc(yh_company);
        //bean.setYhly(yh_from);
        bean.setYhly(yh_from_id);
        bean.setFxrmc(check_people);
        bean.setFxrId(check_peole_id);
        //bean.setFxrq(startTime);
        //bean.setCjsj(endTime);
        //bean.setYhlx(yh_type);
        bean.setYhlx(yh_lx_id);
        bean.setYhjb(yh_grade);
        bean.setYhdd(yh_address);
        bean.setYhbw(yh_position);

        if(yh_grade_name.equals(getString(R.string.hidden_trouble_major_full)))
        {
            bean.setSfxczg("0");
        }
        else
        {
            if (fix.equals("是")){
                bean.setSfxczg("1");
            }else {
                bean.setSfxczg("0");
            }
        }

        bean.setYhms(yh_describle);
        bean.setTplj(picString);
//        bean.setKnzchg("1");
        presenter.addYHMessage(bean);
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







    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void addSuccess() {
        ToastMgr.show(getString(R.string.submit_success));
        SecurityUpdateBean bean = new SecurityUpdateBean();
        bean.setType(1); //1是新建安全成功的提示刷新
        EventBus.getDefault().post(bean);
        getLoadingDialog().dismiss();
        finish();

    }

    @Override
    public void getSafeListSuccess(SafeListBean data) {

    }

    @Override
    public void addFailed() {
        getLoadingDialog().dismiss();
        finish();
    }

    @Override
    public void getSafeChoiceList(SecuritySearchBean bean ) {
        checkCompanyList.clear();
        findCompanyList.clear();
        org = bean.ORG;
        for (OrgBean orgBean: org){
            checkCompanyList.add(orgBean.getORG_NAME_());
            findCompanyList.add(orgBean.getORG_NAME_());
        }
        List<YhlyBean> YHLY=bean.YHLY;

        fromList.add(getResources().getString(R.string.input_text));
        fromListId.add("");

        for (YhlyBean yhlyBean:YHLY){
            fromList.add(yhlyBean.getNAME_());
            fromListId.add(yhlyBean.getCODE_());
        }

        gradeList.add(getResources().getString(R.string.input_text));
        gradeListId.add("");

        for(YhlxBean gradeBean:bean.YHJB)
        {
            gradeList.add(gradeBean.getNAME_());
            gradeListId.add(gradeBean.getCODE_());
        }
        adapter.notifyDataSetChanged();
        fromAdapter.notifyDataSetChanged();
        comAdapter.notifyDataSetChanged();
        gradeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getOrgList(List<OrgBean> bean) {

    }

    @Override
    public void getOrgId(String orgId) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void setUserInfo(UserInfoBean userInfo) {
        check_peole_id = userInfo.getId();
        tv_check_people.setText(userInfo.getRealName());
    }
}
