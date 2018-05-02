package com.isoftston.issuser.conchapp.views.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.IMEUtil;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.NewWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.views.security.ChoiceCheckPeopleActivity;
import com.isoftston.issuser.conchapp.views.security.ChoiceDeviceNameActivity;
import com.isoftston.issuser.conchapp.views.security.ChoiceDeviceTypeActivity;
import com.isoftston.issuser.conchapp.weight.CustomDatePicker;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/11.
 */

public class NewWorkActivity extends BaseActivity<WorkView, WorkPresenter> implements WorkView, View.OnClickListener {
    private static final String TAG = NewWorkActivity.class.getSimpleName();
    public static final int CHOSE_CHARGER_CODE = 100;//负责人
    public static final int CHOSE_KEPPER_CODE = 101;//监护人
    public static final int CHOSE_CHEKER_CODE = 102;//审核人
    public static final int CHOSE_AGREE_CODE = 103;//批准人
    public static final int CHOSE_DEVICE_CODE = 104;
    public static final int CHOSE_NAME_CODE = 105;//设备名称
    private static final int CHOSE_CHARGERNAME_CODE = 99;
    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.start_time)
    TextView tv_start_time;
    @Bind(R.id.end_time)
    TextView tv_end_time;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.ll_alter)
    LinearLayout ll_alter;
    @Bind(R.id.tv_detail_name_content)
    TextView tv_detail_name_content;
    @Bind(R.id.tv_gas_checker)
    TextView tv_gas_checker;
    @Bind(R.id.rl_charger)
    RelativeLayout rl_charger;
    @Bind(R.id.charger_name_tv)
    TextView chagerNameTv;//负责人
    @Bind(R.id.rl_keeper)
    RelativeLayout rl_keeper;
    @Bind(R.id.keeper_name_tv)
    TextView keeperNameTv;//监护人
    @Bind(R.id.rl_check_people)
    RelativeLayout rl_check_people;
    @Bind(R.id.checker_name_tv)
    TextView checkerNameTv;//审核人
    @Bind(R.id.rl_agree)
    RelativeLayout rl_agree;
    @Bind(R.id.authorize_name_tv)
    TextView authorizeNameTv;//批准人

    @Bind(R.id.rl_equipment_type)
    RelativeLayout rl_equipment_type;//设备类型
    @Bind(R.id.equipment_type_tv)
    TextView equipment_type_tv;
    @Bind(R.id.rl_equipment_model)
    RelativeLayout rl_equipment_model;//设备型号
    @Bind(R.id.equipment_model_tv)
    TextView equipment_model_tv;
    @Bind(R.id.rl_equipment_name)
    RelativeLayout rl_equipment_name;//设备名称
    @Bind(R.id.equipment_name_tv)
    TextView equipment_name_tv;

    @Bind(R.id.work_zone_input)
    InputView work_zone_input;//作业区域
    @Bind(R.id.work_address_input)
    InputView work_address_input;//作业点
    @Bind(R.id.worker_num_input)
    InputView worker_num_input;//作业人数

    @Bind(R.id.rg_is_danger_work)
    RadioGroup rg_is_danger_work;
    @Bind(R.id.rb_yes)
    RadioButton rb_yes;
    @Bind(R.id.rb_no)
    RadioButton rb_no;
    //三个下拉选择iv
//    @Bind(R.id.chose_danger_work_type_iv)
//    ImageView chose_danger_work_type_iv;
    @Bind(R.id.chose_gas_checker_iv)
    ImageView chose_gas_checker_iv;
    @Bind(R.id.chose_worker_company_iv)
    ImageView chose_worker_company_iv;
    @Bind(R.id.work_zone_sp)
    Spinner work_zone_sp;
    @Bind(R.id.company_tv)
    TextView company_tv;
    @Bind(R.id.work_company_sp)
    Spinner work_company_sp;

    @Bind(R.id.rl_gas_checker)
    RelativeLayout rl_gas_checker;
    @Bind(R.id.spinner)
    Spinner mySpinner;

    @Bind(R.id.description_et)
    EditText description_et;
    @Bind(R.id.ll_description)
    LinearLayout ll_description;
    @Bind(R.id.tv_illegal_descibe_content)
    TextView tv_illegal_descibe_content;
    @Bind(R.id.tv_illegal_describ_title)
    TextView tv_illegal_describ_title;
    @Bind(R.id.bt_submit)
    Button bt_submit;
    private Context context = NewWorkActivity.this;
    private ArrayAdapter<String> adapter;//创建一个数组适配器
    private int isDangerWork = 0;
    private List<String> dangerTypeList = new ArrayList<>();
    private int device_id;
    private String areaId;
    private List<String> areaList;
    private List<WorkBean> workBeanList = new ArrayList<>();
    private ArrayAdapter<String> spAdapter;
    private int isDanger;
    private List<DangerTypeBean> totalist = new ArrayList<>();
    private int type;

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

        work_zone_input.setInputText(getString(R.string.work_zone), null);
        work_address_input.setInputText(getString(R.string.work_part), null);
        worker_num_input.setInputText(getString(R.string.work_number), InputType.TYPE_NUMBER_VARIATION_NORMAL);
        tv_start_time.setText(now);
        tv_end_time.setText(now);
        clicks();
        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(et_name.getText().toString())) {
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_detail_name_content.setText(et_name.getText().toString());
                    } else {
                        ll_alter.setVisibility(View.VISIBLE);
                    }
                    et_name.setVisibility(View.GONE);
                    IMEUtil.closeIME(et_name, context);
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
                    if (!TextUtils.isEmpty(et_name.getText().toString())) {
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_detail_name_content.setText(et_name.getText().toString());
                    } else {
                        ll_alter.setVisibility(View.VISIBLE);
                    }
                    et_name.setVisibility(View.GONE);

                }
            }
        });

        description_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (!TextUtils.isEmpty(description_et.getText().toString())) {
                        tv_illegal_descibe_content.setVisibility(View.VISIBLE);
                        tv_illegal_descibe_content.setText(description_et.getText().toString());
                    } else {
                        tv_illegal_descibe_content.setText("");
                    }
                    description_et.setVisibility(View.GONE);
                    ll_description.setVisibility(View.VISIBLE);
//                    rl_description.setBackgroundColor(getResources().getColor(R.color.transparent));
                    tv_illegal_describ_title.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        description_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(description_et.getText().toString())) {
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_illegal_descibe_content.setText(description_et.getText().toString());
                    } else {
                        tv_illegal_descibe_content.setText("");
                    }
                    description_et.setVisibility(View.GONE);
                    ll_description.setVisibility(View.VISIBLE);
//                    rl_description.setBackgroundColor(getResources().getColor(R.color.transparent));
                    tv_illegal_describ_title.setTextColor(getResources().getColor(R.color.black));
                    IMEUtil.closeIME(description_et, context);
                    return true;
                }
                return false;
            }
        });

        isDangerWork = getIntent().getIntExtra("isDangerWork", 0);
        if (isDangerWork == 0) {
            rb_yes.setChecked(true);
            rb_no.setChecked(false);
            rb_no.setClickable(false);
            rl_gas_checker.setVisibility(View.VISIBLE);
        } else {
            rb_no.setChecked(true);
            rb_yes.setChecked(false);
            rb_yes.setClickable(false);
            rl_gas_checker.setVisibility(View.GONE);
        }
        presenter.getDangerWorkType(new FixWorkBean());
        aboutSpinner();
    }

    /**设置作业类别*/
    private void aboutWorkClassSpinner() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dangerTypeList);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.getItem(i) != null && adapter.getItem(i).equals(getString(R.string.danger_work_type_item))) {
                    rl_charger.setVisibility(View.GONE);
                    rl_gas_checker.setVisibility(View.VISIBLE);
                } else {
                    rl_charger.setVisibility(View.VISIBLE);
                    rl_gas_checker.setVisibility(View.GONE);
                }
                type = Integer.parseInt(totalist.get(i).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
    }

    private void aboutSpinner() {
        //作业区域下拉选择
        areaList = new ArrayList<>();
        areaList.add("");
        presenter.getWorkInfo();
        spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaList);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        work_zone_sp.setAdapter(spAdapter);
        work_zone_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "----" + spAdapter.getItem(position).toString());
                area = spAdapter.getItem(position).toString();
                for (WorkBean bean : workBeanList) {
                    if (area.equals(bean.getName())) {
                        areaId = String.valueOf(bean.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                work_zone_sp.setSelection(0);
                area = getString(R.string.shuini);
            }
        });
        //作业单位下拉选择
        final ArrayAdapter<String> companyAdapter;
        List<String> companyList = new ArrayList<>();
        companyList.add(PreferencesHelper.getData(Constant.ORG_NAME));
        companyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, companyList);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        work_company_sp.setAdapter(companyAdapter);
        work_company_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                company = companyAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                work_company_sp.setSelection(0);
                company = companyAdapter.getItem(0).toString();
            }
        });
    }

    private void clicks() {
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
//        chose_danger_work_type_iv.setOnClickListener(this);
        chose_gas_checker_iv.setOnClickListener(this);
        chose_worker_company_iv.setOnClickListener(this);
        rl_charger.setOnClickListener(this);
        rl_keeper.setOnClickListener(this);
        rl_check_people.setOnClickListener(this);
        rl_agree.setOnClickListener(this);
        rl_agree.setOnClickListener(this);
        ll_description.setOnClickListener(this);
        rl_gas_checker.setOnClickListener(this);
        rl_equipment_type.setOnClickListener(this);
        rl_equipment_name.setOnClickListener(this);
    }

    @Override
    protected WorkPresenter createPresenter() {
        return new WorkPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_time:
                showDatePickerDialog(tv_start_time, 1);
                break;
            case R.id.end_time:
                showDatePickerDialog(tv_end_time, 2);
                break;
            case R.id.rl_gas_checker:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context, 5), CHOSE_CHARGER_CODE);
                break;
            case R.id.rl_charger:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context, 1), CHOSE_CHARGERNAME_CODE);
                break;
            case R.id.rl_agree:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context, 4), CHOSE_AGREE_CODE);
                break;
            case R.id.rl_check_people:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context, 3), CHOSE_CHEKER_CODE);
                break;
            case R.id.rl_keeper:
                startActivityForResult(ChoiceCheckPeopleActivity.getLaucnher(context, 2), CHOSE_KEPPER_CODE);
                break;
            case R.id.rl_dangerwork_type:

                break;
            case R.id.rl_equipment_type:
                startActivityForResult(ChoiceDeviceTypeActivity.getLaucnher(context), CHOSE_DEVICE_CODE);
                break;
            case R.id.rl_equipment_name:
                Intent intent = new Intent(context, ChoiceDeviceNameActivity.class);
                intent.putExtra("device_id", String.valueOf(device_id));
                startActivityForResult(intent, CHOSE_NAME_CODE);
                break;

            case R.id.bt_submit:
                getNewJobInfo();
                break;
            case R.id.chose_gas_checker_iv:
                break;
            case R.id.chose_worker_company_iv:
                break;
            case R.id.ll_description:
                ll_description.setVisibility(View.GONE);
                description_et.setVisibility(View.VISIBLE);
                description_et.setFocusable(true);
                description_et.setFocusableInTouchMode(true);
                description_et.requestFocus();
                break;
            default:
                break;
        }
    }

    String leading;
    String guardian;
    String auditor;
    String approver;
    String area;
    String company;

    private void getNewJobInfo() {
        NewWorkBean bean = new NewWorkBean();
        String name = et_name.getText().toString().trim();
        String startTimeStr = tv_start_time.getText().toString().trim();
        long startTime = TextUtils.isEmpty(String.valueOf(startTimeStr)) ? 0 : DateUtils.getDateToLongMS(startTimeStr);
        String endTimeStr = tv_end_time.getText().toString().trim();
        long endTime = TextUtils.isEmpty(String.valueOf(endTimeStr)) ? 0 : DateUtils.getDateToLongMS(endTimeStr);
        String equipmentType = equipment_type_tv.getText().toString();
//        String equipmentType = equipment_type_tv.getText().toString().trim();
//        String equipmentCode = equipment_model_tv.getText().toString().trim();
//        String equipmentName = equipment_name_tv.getText().toString().trim();
        String equipmentCode = equipment_model_tv.getText().toString();
        String equipmentName = equipment_name_tv.getText().toString();
        String part = work_address_input.getContent().toString().trim();
        String content = description_et.getText().toString().trim();
        company = PreferencesHelper.getData(Constant.ORG_NAME);//作业单位。用户所属公司
        String numPeople = worker_num_input.getContent().trim();
//        int type = 0;//危险作业类型(手动选择)

        //1危险、0常规作业。前页面传递
        if (rb_yes.isChecked()) {
            isDanger = 1;
        } else if (rb_no.isChecked()) {
            isDanger = 0;
        }

        if (TextUtils.isEmpty(name) || startTime == 0
                || TextUtils.isEmpty(name) || TextUtils.isEmpty(equipmentType) || TextUtils.isEmpty(equipmentCode)
                || TextUtils.isEmpty(equipmentName) || TextUtils.isEmpty(area) || TextUtils.isEmpty(part)
                || TextUtils.isEmpty(content) || TextUtils.isEmpty(company) || TextUtils.isEmpty(numPeople)
                || TextUtils.isEmpty(String.valueOf(type)) || TextUtils.isEmpty(leading) || TextUtils.isEmpty(guardian)
                || TextUtils.isEmpty(auditor) || TextUtils.isEmpty(approver)) {
            ToastMgr.show(R.string.input_all_message);
            return;
        }
        bean.setName(name);
        bean.setStartTime(startTime);
        bean.setEndTime(endTime);
        bean.setEquipmentType(device_id);
        bean.setEquipmentCode(equipmentCode);
        bean.setEquipmentName(equipmentName);
        bean.setArea(areaId);
        bean.setPart(part);
        bean.setContent(content);
        bean.setCompany(company);
        try {
            bean.setNumberPeople(Integer.parseInt(numPeople));
        } catch (Exception e) {
            ToastMgr.show(R.string.input_num);
            return;
        }
        bean.setType(type);
        bean.setLeading(leading);
        bean.setGuardian(guardian);
        bean.setAuditor(auditor);
        bean.setApprover(approver);
        bean.setIsDanger(isDanger);
//        bean.setOrgId("1");
        presenter.addWork(bean);
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
        if ((dt1.getTime() - dt2.getTime()) > 0) {
            isBigger = true;
        } else {
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
                    if (i == 1) {
                        starttime = DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time));
                        endtime = tv_end_time.getText().toString();
                    } else {
                        starttime = tv_start_time.getText().toString();
                        endtime = DateUtils.format_yyyy_MM_dd_HH_mm.format(DateUtils.format_yyyy_MM_dd_HH_mm.parse(time));
                    }
                    if (isDateOneBigger(starttime, endtime)) {
                        Toast.makeText(NewWorkActivity.this, R.string.information, Toast.LENGTH_SHORT).show();
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
    public void alterNamebtn() {
        ll_alter.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_detail_name_content)
    public void alterNameText() {
        tv_detail_name_content.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
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
        workBeanList = list;
        areaList.clear();
        for (int i = 0; i < list.size(); i++) {
            areaList.add(list.get(i).getName());
        }
        spAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkList(List<WorkDetailBean> list) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void addWorkSuccess() {
        ToastUtils.showtoast(context, getString(R.string.submit_success));
        finish();
    }

    @Override
    public void getDangerWorkTypeResult(List<DangerTypeBean> list) {
        totalist = list;
        for (DangerTypeBean bean : list) {
            dangerTypeList.add(bean.getName());
        }
        aboutWorkClassSpinner();

    }

    @Override
    public void getDeviceTypeResult(List<DeviceTypeBean> list) {

    }

    @Override
    public void getDeviceDetailSuccess(List<DeviceDetailBean> list) {

    }

    private String chosedUserName;
    private String chosedUserId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CHOSE_CHARGER_CODE) {
            chosedUserName = data.getStringExtra(Constant.CHECK_PEOPLE);
            chosedUserId = data.getStringExtra(Constant.CHECK_PEOPLE_ID);
            if (requestCode == CHOSE_CHARGER_CODE) {
                tv_gas_checker.setText(chosedUserName);
                leading = chosedUserId;
            } else if (requestCode == CHOSE_CHEKER_CODE) {
                checkerNameTv.setText(chosedUserName);
                auditor = chosedUserId;
            } else if (requestCode == CHOSE_KEPPER_CODE) {
                keeperNameTv.setText(chosedUserName);
                guardian = chosedUserId;
            } else if (requestCode == CHOSE_AGREE_CODE) {
                authorizeNameTv.setText(chosedUserName);
                approver = chosedUserId;
            } else if (requestCode == CHOSE_CHARGERNAME_CODE) {
                chagerNameTv.setText(chosedUserName);
                leading = chosedUserId;
            }
        } else if (resultCode == CHOSE_DEVICE_CODE) {
            if (requestCode == CHOSE_DEVICE_CODE) {
                String type = data.getStringExtra(Constant.CHECK_PEOPLE);
                device_id = data.getIntExtra(Constant.CHECK_DEVICE_ID, -1);
                equipment_type_tv.setText(type);
            }
        } else if (resultCode == CHOSE_NAME_CODE) {
            if (requestCode == CHOSE_NAME_CODE) {
                String type = data.getStringExtra(Constant.CHECK_PEOPLE);
                equipment_name_tv.setText(type);
                String Device_type = data.getStringExtra(Constant.CHECK_DEVICE_TYPE);
                equipment_model_tv.setText(Device_type);
            }
        }
    }
}

