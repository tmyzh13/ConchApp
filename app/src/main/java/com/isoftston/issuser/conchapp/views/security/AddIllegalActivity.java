package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.weight.ChooseListPopupWindow;
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
 * Created by john on 2018/4/15.
 */

public class AddIllegalActivity extends BaseActivity<SecuryView,SecurityPresenter> implements SecuryView {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_illegal_name)
    InputView input_illegal_name;

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

    @Bind(R.id.male_rb)
    RadioButton male_rb;
    @Bind(R.id.famale_rb)
    RadioButton famale_rb;

    @Bind(R.id.tv_illegal_find_company)
    TextView tv_illegal_find_company;

    @Bind(R.id.tv_illegal_company)
    TextView tv_illegal_company;

    private Context context=AddIllegalActivity.this;
    private String startTime,endTime;
    private ChooseListPopupWindow window;
    private List<OrgBean> org;
    private String find_company;
    private String find_company_id;
    private String wz_company;
    private String wz_company_id;
    private String wz_type_id;
    private String wz_people_id;
    private int nm;

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
        input_illegal_place.setInputText(getString(R.string.illegal_place),null);

        startTime= Tools.getCurrentTime();
        endTime=Tools.getCurrentTime();


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
    }

    private void initSpinner() {

    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }

    @OnClick(R.id.ll_confirm)
    public void confirmInfo(){
        getLoadingDialog().show();
        String name=input_illegal_name.getContent().trim();

        String check_people=tv_check_people.getText().toString();
        String wz_type=tv_illegal_type.getText().toString();
        String wz_address=input_illegal_place.getContent().trim();
        String wz_describle=et_description.getText().toString().trim();
        if (male_rb.isChecked()){
            nm = 1;
        }else if (famale_rb.isChecked()){
            nm=0;
        }

        AddYHBean bean=new AddYHBean();
        bean.setYhmc(name);
//        bean.setGsId("1");//公司id
        bean.setJcdwid(find_company_id);//公司名称
        bean.setJcdwmc(find_company);
        bean.setSjdwid(wz_company_id);
        bean.setSjdwmc(wz_company);
//        bean.setYhly("1");
        bean.setFxrmc(check_people);
        bean.setFxrId(wz_people_id);

        bean.setYhlx(wz_type_id);
//        bean.setYhjb("1");
        bean.setYhdd(wz_address);
//        bean.setYhbw("1");
//        bean.setYhlbb("1");
//        bean.setSfxczg("1");
        bean.setYhms(wz_describle);
        bean.setTplj(picString);
//        bean.setKnzchg("1");
//        bean.setIschoose("1");
        bean.setNm(nm);
        presenter.addWZMessage(bean);
//        finish();
    }

    private HashMap<String, String> map = new HashMap<>();
    private String picString;

    @OnClick(R.id.rl_photo)
    public void choicePhoto(){
        startActivityForResult(ChoicePhotoActivity.getLauncher(context,"0",map,0),110);
    }


//    @OnClick(R.id.ll_check_people)
//    public void choiceCheckPeople(){
//        startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context,7),100);
//    }

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
    @OnClick(R.id.rl_wz_type)
    public void choiceWZType(){
        startActivityForResult(ChoiceTypeActivity.getLaucnher(context,2),110);
    }

    @OnClick(R.id.rl_find_company)
    public void findOrg(){
        startActivityForResult(OrgActivity.getLaucnher(context,0),120);
    }

    @OnClick(R.id.rl_wz_company)
    public void findTroubleOrg(){
        startActivityForResult(OrgActivity.getLaucnher(context,1),120);
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
            else if (resultCode==100){
                String name=data.getStringExtra(Constant.CHECK_PEOPLE);
                tv_check_people.setText(name);
                wz_people_id = data.getStringExtra(Constant.CHECK_PEOPLE_ID);
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
            }else if (resultCode==104){
                String name=data.getStringExtra(Constant.CHECK_PEOPLE);
                tv_illegal_type.setText(name);
                wz_type_id = data.getStringExtra(Constant.CHECK_PEOPLE_ID);
            }
        }else if(requestCode==120)
        {
            if(resultCode == 130)
            {
                find_company_id=data.getStringExtra(Constant.FIND_COMPANY_ID);
                find_company = data.getStringExtra(Constant.FIND_COMPANY_NAME);
                tv_illegal_find_company.setText(find_company);

            }
            else if(resultCode == 131)
            {
                wz_company_id = data.getStringExtra(Constant.DANGER_COMPANY_ID);
                wz_company = data.getStringExtra(Constant.DANGER_COMPANY_NAME);
                tv_illegal_company.setText(wz_company);
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


    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void addSuccess() {
        ToastMgr.show(getString(R.string.submit_success));
        getLoadingDialog().dismiss();
        SecurityUpdateBean bean = new SecurityUpdateBean();
        bean.setType(1); //1是新建安全成功的提示刷新
        EventBus.getDefault().post(bean);
        finish();
    }

    @Override
    public void getSafeListSuccess(SafeListBean data) {

    }

    @Override
    public void addFailed() {
        ToastMgr.show(getString(R.string.submit_fail));
        getLoadingDialog().dismiss();
    }

    @Override
    public void getSafeChoiceList(SecuritySearchBean bean) {

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
        tv_check_people.setText(userInfo.getRealName());
        wz_people_id = userInfo.getId();
    }
}
