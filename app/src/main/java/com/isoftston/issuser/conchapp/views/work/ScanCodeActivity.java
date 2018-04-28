package com.isoftston.issuser.conchapp.views.work;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
import com.isoftston.issuser.conchapp.model.bean.ScanInfo;
import com.isoftston.issuser.conchapp.model.bean.SubmitJobBody;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailRequestBean;
import com.isoftston.issuser.conchapp.presenter.WorkDetailPresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkDetailView;
import com.isoftston.issuser.conchapp.views.mine.adapter.ScanInfoAdapter;
import com.isoftston.issuser.conchapp.views.security.ChoicePhotoActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class ScanCodeActivity extends BaseActivity<WorkDetailView, WorkDetailPresenter> implements WorkDetailView, View.OnClickListener {
    private static final String TAG = "ScanCodeActivity";
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;
    private static final int REQUEST_STORAGE_PERMISSION_CODE = 101;
    private static final int OPEN_ACTIVITY_SCAN_CODE = 102;
    private static final int OPEN_ACTIVITY_TAKE_PHOTO_CODE = 103;

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_add)
    ImageView refreshIv;//刷新按钮
    @Bind(R.id.revoke_btn)
    Button revokeBtn;
    @Bind(R.id.commit_btn)
    Button commitBtn;
    @Bind(R.id.modify_btn)
    Button modifyBtn;
    @Bind(R.id.project_name_tv)
    TextView projectNameTv;//项目名称
    @Bind(R.id.project_name_time_day)
    TextView projectTimeDayTv;//项目时间(天)
    @Bind(R.id.work_start_time_day)
    TextView work_start_time_day;//作业开始时间
    @Bind(R.id.work_end_time_day)
    TextView work_end_time_day;//作业结束时间

    //四个标识圆点及相关人员
    @Bind(R.id.charge_person_iv)
    ImageView chargePersonIv;//负责人
    @Bind(R.id.charge_person)
    TextView chargePersonTv;
    @Bind(R.id.charge_person_relname)
    TextView chargePersonRelnameTv;

    @Bind(R.id.guardian_iv)
    ImageView guardianPersonIv;//监护人
    @Bind(R.id.guardian)
    TextView guardianTv;
    @Bind(R.id.guardian_relname)
    TextView guardianRelnameTv;

    @Bind(R.id.auditor_iv)
    ImageView auditorPersonIv;//审核人
    @Bind(R.id.auditor)
    TextView auditorTv;
    @Bind(R.id.auditor_relname)
    TextView auditorRelnameTv;

    @Bind(R.id.approver_iv)
    ImageView approverPersonIv;//审核人
    @Bind(R.id.approver)
    TextView approverTv;
    @Bind(R.id.approver_relname)
    TextView approverRelnameTv;
    //底部列表显示具体人名
    @Bind(R.id.person_in_charge_tv)
    TextView personInChargeNmaeTv;//负责人
    @Bind(R.id.guardian_tv)
    TextView guardianNameTv;//监护人
    @Bind(R.id.auditor_tv)
    TextView auditorNameTv;//审核人
    @Bind(R.id.approver_tv)
    TextView approverNameTv;//批准人
    //其他信息
    @Bind(R.id.equipment_type_tv)
    TextView equipmentTypeTv;//设备类型
    @Bind(R.id.equipment_model_tv)
    TextView equipmentModelTv;//设备型号
    @Bind(R.id.equipment_name_tv)
    TextView equipmentNameTv;//设备名称
    @Bind(R.id.work_zone_tv)
    TextView workZoneTv;//作业区域
    @Bind(R.id.work_address_tv)
    TextView workAddressTv;//作业地点
    @Bind(R.id.work_content_tv)
    TextView workContentTv;//作业内容
    @Bind(R.id.work_company_tv)
    TextView workCompanyTv;//作业单位
    @Bind(R.id.work_number_tv)
    TextView workNumberTv;//作业人数
    @Bind(R.id.danger_work_type_tv)
    TextView dangerWorkTypeTv;//危险作业类型
    @Bind(R.id.gas_checker_tv)
    TextView gasCheckerTv;//气体检测人
    @Bind(R.id.danger_work_rl)
    RelativeLayout dangerWorkRl;//危险作业

    @Bind(R.id.scan_success_layout)
    LinearLayout scanSuccessHintLayout;//扫码成功，点击可继续扫码或拍照布局
    @Bind(R.id.scan_or_photo_tv)
    TextView scanOrPhotoSuccessTv;//扫码/拍照成功

    @Bind(R.id.scan_layout_outter)//没有扫过二维码显示的布局
            LinearLayout scanCodeLl;
    LinearLayout scanCodeLayout;////扫码
    LinearLayout takePhotoLayout;//拍照
    @Bind(R.id.scan_flag_iv)
    ImageView scanFlagIv1;//第一轮扫码、拍照标记view
    @Bind(R.id.photo_flag_iv)
    ImageView photoFlagIv1;
    private boolean isScaned1 = false;//是否扫过
    private boolean isPhotoed1 = false;//是否拍过照

    @Bind(R.id.scaned_layout)
    LinearLayout scanedLayout;
    @Bind(R.id.scan_layout_inner)//扫过二维码后显示的布局
            LinearLayout scanCodeLlInner;
    LinearLayout scanCodeInner;//扫码
    View scanFlagIv;
    LinearLayout takePhotoInnerLayout;//拍照
    View photoFlagIv;
    @Bind(R.id.scan_flag_iv1)
    ImageView scanFlagIv2;//第二轮扫码、拍照标记view
    @Bind(R.id.photo_flag_iv1)
    ImageView photoFlagIv2;
    @Bind(R.id.scan_info_lv)
    ListView mListView;
    private List<ScanInfo> datas;
    private ScanInfoAdapter mAdapter;
    private boolean isScaned2 = false;
    private boolean isPhotoed2 = false;

    private boolean isChargePerson = false;//是否为负责人
    private boolean isGurdianPerson = false;//是否为监护人
    private boolean isAuditorPerson = false;//是否为审核人
    private boolean isApproverPerson = false;//是否为批准人
    private boolean isChargePersonScaned = false;//负责人是否扫过
    private boolean isChargePersonPhotoed = false;//负责人是拍过
    private boolean isOneTurnDone = false;//第一轮是否完成

    private boolean isCommited = false;
    private boolean isDangerWork = false;
    private String jobId = "";
    private String userId = "";

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    isScaned1 = true;
                    hintScan1Success();
                    break;
                case 2:
                    isPhotoed1 = true;
                    hintPhoto1Success();
                    break;
                case 3:
                    isScaned2 = true;
                    hintScan2Success();
                    break;
                case 4:
                    isPhotoed2 = true;
                    hintPhoto2Success();
                    break;
                case 5:
                    isCommited = true;
                    if (isChargePerson){
                        changeChargersToGreen();
                    }else if (isGurdianPerson){
                        changeGuardiansToGreen();
                    }else if (isAuditorPerson){
                        changeAuditorsToGreen();
                    }else if (isApproverPerson){
                        changeApproversToGreen();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    public static Intent getLauncher(Context context, String jobId) {
        Intent intent = new Intent(context, ScanCodeActivity.class);
        intent.putExtra("jobId", jobId);
        return intent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.work_point_information));
        refreshIv.setVisibility(View.VISIBLE);
        refreshIv.setImageResource(R.mipmap.refresh);//改为刷新
        getLoadingDialog().show();
        presenter.getUserInfo();
        //获取作业详情
        presenter.getWorkDetailInfo(jobId);
        scan();
        setData();
        scaned();


    }

    /**
     * 测试数据
     */
    private void setData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ScanInfo info = new ScanInfo("上次扫码时间：" + i, "武汉");
            datas.add(info);
        }
        mAdapter = new ScanInfoAdapter(this, datas);
        mListView.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mListView);
    }

    /**
     * 先获取用户信息,然后判断其职位
     * 检查当前用户的职位
     * 扫描和拍照后改变ui
     */
    int status;

    private void checkUserPosition(WorkDetailBean bean) {
        hideAllBtn();
        status = bean.status;
        if (status == 2 && isChargePerson) {
            revokeBtn.setVisibility(View.VISIBLE);
        }
        //比对自己的职位
        if (userId.equals(bean.leading)) {
            isChargePerson = true;
        } else if (userId.equals(bean.guardian)) {
            isGurdianPerson = true;
        } else if (userId.equals(bean.auditor)) {
            isAuditorPerson = true;
        } else if (userId.equals(bean.approver)) {
            isApproverPerson = true;
        }

        if (status == 5) {
            isOneTurnDone = true;
        } else if (status == 4) {
            isChargePersonScaned = true;
            isChargePersonPhotoed = true;
        }
        
        //第一轮扫码是否完成
        if (isOneTurnDone) {
            changeScanLayout();
            if (isChargePerson) {//负责人
                showOrHideCommitBtn();
                if (bean.status == 4) {
                    showAllBtn();
                    hintScan2Success();
                }
                if (isChargePersonPhotoed) {
                    hintPhoto2Success();
                }
                if (isChargePersonScaned && isChargePersonPhotoed) {
                    changeChargersToGreen();
                }
            } else if (isGurdianPerson) {//监护人
                showOrHideCommitBtn();
                if (isChargePersonScaned) {//改变ui
                    if (isScaned2) {
                        hintScan2Success();
                    } else if (isPhotoed2) {
                        hintPhoto2Success();
                    }
                    if (isScaned2 && isPhotoed2) {
                        changeGuardiansToGreen();
                    }
                }
            } else if (isAuditorPerson) {//审核人
                showOrHideCommitBtn();
                if (isChargePersonScaned) {
                    if (isScaned2) {
                        hintScan2Success();
                    } else if (isPhotoed2) {
                        hintPhoto2Success();
                    }
                    if (isScaned2 && isPhotoed2) {
                        changeAuditorsToGreen();
                    }
                }
            } else if (isApproverPerson) {//批准人
                showOrHideCommitBtn();
                if (isScaned2) {
                    hintScan2Success();
                } else if (isPhotoed2) {
                    hintPhoto2Success();
                }
                if (isScaned2 && isPhotoed2) {
                    changeApproversToGreen();
                }
            } else {
                hideAllBtn();
                scanCodeLlInner.setVisibility(View.GONE);
            }
        } else {//第一轮操作
            if (isChargePerson) {//负责人
                showOrHideCommitBtn();
                if (isChargePersonScaned) {
                    showAllBtn();
                    hintScan1Success();
                }
                if (isChargePersonPhotoed) {
                    hintPhoto1Success();
                }
                if (isChargePersonScaned && isChargePersonPhotoed) {
                    changeChargersToGreen();
                }
            } else if (isGurdianPerson) {//监护人
                showOrHideCommitBtn();
                if (isChargePersonScaned) {//改变ui
                    if (isScaned1) {
                        hintScan1Success();
                    } else if (isPhotoed1) {
                        hintPhoto1Success();
                    }
                    if (isScaned1 && isPhotoed1) {
                        changeGuardiansToGreen();
                    }
                }
            } else if (isAuditorPerson) {//审核人
                showOrHideCommitBtn();
                if (isChargePersonScaned) {
                    if (isScaned1) {
                        hintScan1Success();
                    } else if (isPhotoed1) {
                        hintPhoto1Success();
                    }
                    if (isScaned1 && isPhotoed1) {
                        changeAuditorsToGreen();
                    }
                }
            } else if (isApproverPerson) {//批准人
                showOrHideCommitBtn();
                if (isScaned1) {
                    hintScan1Success();
                } else if (isPhotoed1) {
                    hintPhoto1Success();
                }
                if (isScaned1 && isPhotoed1) {
                    changeApproversToGreen();
                }
            } else {
                hideAllBtn();
                scanCodeLl.setVisibility(View.GONE);
            }
        }
//        modifyBtn.setVisibility(View.VISIBLE);
    }

    private void changeApproversToGreen() {
        approverPersonIv.setImageResource(R.drawable.dots_green);
        approverTv.setTextColor(getResources().getColor(R.color.colorGreen));
        approverRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        approverNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void changeAuditorsToGreen() {
        auditorPersonIv.setImageResource(R.drawable.dots_green);
        auditorTv.setTextColor(getResources().getColor(R.color.colorGreen));
        auditorRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        auditorNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void hintPhoto1Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.photo_action);
        photoFlagIv1.setVisibility(View.VISIBLE);//显示已拍照图标
    }

    private void hintScan1Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.scan_code);
        scanFlagIv1.setVisibility(View.VISIBLE);
    }

    private void changeGuardiansToGreen() {
        guardianPersonIv.setImageResource(R.drawable.dots_green);//圆点变色
        guardianTv.setTextColor(getResources().getColor(R.color.colorGreen));//圆点文字变色
        guardianRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));//具体人名变色
        guardianNameTv.setTextColor(getResources().getColor(R.color.colorGreen));//人名变色
    }

    private void changeChargersToGreen() {
        chargePersonIv.setImageResource(R.drawable.dots_green);
        chargePersonTv.setTextColor(getResources().getColor(R.color.colorGreen));
        chargePersonRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
        personInChargeNmaeTv.setTextColor(getResources().getColor(R.color.colorGreen));
    }

    private void hintPhoto2Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.photo_action);
        photoFlagIv2.setVisibility(View.VISIBLE);
    }

    private void hintScan2Success() {
        scanSuccessHintLayout.setVisibility(View.VISIBLE);
        scanOrPhotoSuccessTv.setText(R.string.scan_code);
        scanFlagIv2.setVisibility(View.VISIBLE);
    }

    private void showOldUi() {
        chargePersonIv.setImageResource(R.drawable.dots_normal);
        chargePersonTv.setTextColor(getResources().getColor(R.color.text_gray));
        chargePersonRelnameTv.setTextColor(getResources().getColor(R.color.text_gray));
        personInChargeNmaeTv.setTextColor(getResources().getColor(R.color.text_gray));

        guardianPersonIv.setImageResource(R.drawable.dots_normal);
        guardianTv.setTextColor(getResources().getColor(R.color.text_gray));
        guardianRelnameTv.setTextColor(getResources().getColor(R.color.text_gray));
        guardianNameTv.setTextColor(getResources().getColor(R.color.text_gray));

        auditorPersonIv.setImageResource(R.drawable.dots_normal);
        auditorTv.setTextColor(getResources().getColor(R.color.text_gray));
        auditorRelnameTv.setTextColor(getResources().getColor(R.color.text_gray));
        auditorNameTv.setTextColor(getResources().getColor(R.color.text_gray));

        approverPersonIv.setImageResource(R.drawable.dots_normal);
        approverTv.setTextColor(getResources().getColor(R.color.text_gray));
        approverRelnameTv.setTextColor(getResources().getColor(R.color.text_gray));
        approverNameTv.setTextColor(getResources().getColor(R.color.text_gray));
    }

    private void showOrHideCommitBtn() {
        if (isCommited) {
            hideAllBtn();
        } else {
            commitBtn.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        //没有扫描和拍照，显示布局
        scanCodeLayout = scanCodeLl.findViewById(R.id.scan_code_layout);
        scanFlagIv = scanCodeLayout.findViewById(R.id.scan_flag_iv);
        takePhotoLayout = scanCodeLl.findViewById(R.id.take_photo_layout);
        photoFlagIv = takePhotoLayout.findViewById(R.id.photo_flag_iv);
        //所有人扫过后按钮在内部显示
        scanCodeInner = scanCodeLlInner.findViewById(R.id.scan_code_layout);
        takePhotoInnerLayout = scanCodeLlInner.findViewById(R.id.take_photo_layout);

        jobId = getIntent().getStringExtra("jobId");
        if (jobId == null) {
            hideAllBtn();
        }
        clicks();
    }

    private void clicks() {
        revokeBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }

    @OnClick(R.id.modify_btn)
    public void modify() {//修改
        Intent intent = new Intent(this, FixWorkActivity.class);
        intent.putExtra("id",jobId);
        startActivity(intent);
    }

    /**
     * 刷新数据
     */
    @OnClick(R.id.iv_add)
    public void refresh() {
        presenter.getWorkDetailInfo(jobId);
    }

    private void scaned() {//扫过二维码
        scanCodeInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(0);
            }
        });
        takePhotoInnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this, "0", map), OPEN_ACTIVITY_TAKE_PHOTO_CODE);
            }
        });
    }

    private HashMap<String, String> map = new HashMap<>();

    private void scan() {//没有扫过二维码
        scanCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(0);
            }
        });
        takePhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this, "0", map), OPEN_ACTIVITY_TAKE_PHOTO_CODE);
            }
        });
    }

    private void checkPermission(int type) {
        if (type == 0) {
            if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //申请摄像头权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            } else {
                startScanCode();
            }
        }
    }

    /**
     * 申请摄像头权限返回结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION_CODE://摄像头权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获得授权,开始扫描
                    startScanCode();
                } else {
                    ToastMgr.show(getString(R.string.check_manager_open_pemission));
                }
                break;
            default:
                break;
        }
    }

    private void startScanCode() {
        //开始扫描
        Intent intent = new Intent(getViewContext(), CaptureActivity.class);
        startActivityForResult(intent, OPEN_ACTIVITY_SCAN_CODE);
    }

    /**
     * 扫描二维码返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ACTIVITY_SCAN_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String s = bundle.getString("result");
            Log.e(TAG, "---扫描结果：" + s);
            if (s.equals(getString(R.string.check_manager_cancel_scan))) {
                //取消扫码
                ToastMgr.show(getString(R.string.check_manager_cancel_scan));
            } else {
                if (TextUtils.equals(s, equipmentModelTv.getText().toString())) {
                    showResultView(s);
                } else {
                    ToastUtils.showtoast(ScanCodeActivity.this, "您扫描的二维码有误，请扫描正确的二维码！");
                }

            }
        } else if (requestCode == OPEN_ACTIVITY_TAKE_PHOTO_CODE && resultCode == 10) {
            map = (HashMap<String, String>) data.getSerializableExtra(Constant.TEMP_PIC_LIST);
            Log.e(TAG, "----size:" + map.size() + ",--" + map.toString());
            if (status == 5) {
                isPhotoed2 = true;
                handler.sendEmptyMessage(4);
            } else {
                isPhotoed1 = true;
                handler.sendEmptyMessage(3);
            }
            photoFlagIv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 提示扫码成功
     */
    private void showResultView(final String result) {
        final Dialog dialog = new Dialog(this, R.style.Dialog);
        dialog.show();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View viewDialog = inflater.inflate(R.layout.scan_success_layout, null);
        Display display = this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialog.setContentView(viewDialog, layoutParams);
        TextView equipmentNumber = viewDialog.findViewById(R.id.equipment_name_number_tv);
        Button sureBtn = viewDialog.findViewById(R.id.scan_success_sure_btn);
        //获取扫码得到的编号
        equipmentNumber.setText(result);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (status == 5) {
                    isScaned2 = true;
                    handler.sendEmptyMessage(2);
                } else {
                    isScaned1 = true;
                    handler.sendEmptyMessage(1);
                }
                scanFlagIv.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * 所有人扫过后显示布局
     */
    private void changeScanLayout() {
        if (status == 5) {
            scanCodeLl.setVisibility(View.GONE);
            scanedLayout.setVisibility(View.VISIBLE);
            scanCodeInner.setBackgroundResource(R.drawable.scaned_code_btn_shape);
            takePhotoInnerLayout.setBackgroundResource(R.drawable.scaned_code_btn_shape);
            isOneTurnDone = true;
            showOldUi();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    protected WorkDetailPresenter createPresenter() {
        return new WorkDetailPresenter();
    }

    @Override
    public void renderData(WorkDetailBean workDetailBean) {
    }


    @Override
    public void getWorkDetailInfo(WorkDetailRequestBean workBean) {
        //填充数据
        WorkDetailBean workDetailBean = workBean.work;
//        work_start_time_day
        if (workDetailBean.status == 1 || workDetailBean.status == 5) {
            hideAllBtn();
        }
        projectNameTv.setText(workDetailBean.name);
        String createTime = DateUtils.format_yyyy_MM_dd_china.format(workDetailBean.createTime);
        projectTimeDayTv.setText(createTime);
        String startTime = DateUtils.format_yyyy_MM_dd_HH_mm.format(workDetailBean.startTime);
        work_start_time_day.setText(startTime);
        if (!(workDetailBean.endTime == 0)) {
            String endTime = DateUtils.format_yyyy_MM_dd_HH_mm.format(workDetailBean.endTime);
            work_end_time_day.setText(endTime);
        }
        String chargeName = workDetailBean.leadingName;
        if (chargeName != null) {
            chargePersonRelnameTv.setText(chargeName);
            personInChargeNmaeTv.setText(chargeName);
        }
        String guardianName = workDetailBean.guardianName;
        if (guardianName != null) {
            guardianRelnameTv.setText(guardianName);
            guardianNameTv.setText(guardianName);
        }
        String auditorName = workDetailBean.auditorName;
        if (auditorName != null) {
            auditorRelnameTv.setText(auditorName);
            auditorNameTv.setText(auditorName);
        }
        String approverName = workDetailBean.approverName;
        if (approverName != null) {
            approverRelnameTv.setText(approverName);
            approverNameTv.setText(approverName);
        }
        equipmentTypeTv.setText(String.valueOf(workDetailBean.equipmentType));
        equipmentModelTv.setText(workDetailBean.equipmentCode);
        equipmentNameTv.setText(workDetailBean.equipmentName);
        workZoneTv.setText(workDetailBean.area);
        workAddressTv.setText(workDetailBean.area);
        workContentTv.setText(workDetailBean.content);
        workCompanyTv.setText(workDetailBean.company);
        workNumberTv.setText(String.valueOf(workDetailBean.numberPeople));
        if (workDetailBean.type == 0) {
            isDangerWork = true;
        } else {
            isDangerWork = false;
        }
        if (isDangerWork) {
            gasCheckerTv.setText(workDetailBean.gas);
            dangerWorkRl.setVisibility(View.VISIBLE);
        } else {
            dangerWorkRl.setVisibility(View.GONE);
        }
        checkUserPosition(workDetailBean);
        getLoadingDialog().dismiss();
    }

    private void hideAllBtn() {
        revokeBtn.setVisibility(View.GONE);
        commitBtn.setVisibility(View.GONE);
        modifyBtn.setVisibility(View.GONE);
    }

    private void showAllBtn() {
        revokeBtn.setVisibility(View.VISIBLE);
        commitBtn.setVisibility(View.VISIBLE);
        modifyBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void responseError(int type) {
        if (type == 0) {
            ToastMgr.show(R.string.internet_error);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----获取作业详细信息失败");
        } else if (type == 1) {
            ToastMgr.show(R.string.back_failed);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----撤销作业失败");
        } else if (type == 2) {
            ToastMgr.show(R.string.subimt_failed);
            getLoadingDialog().dismiss();
            Log.e(TAG, "----提交作业失败");
        }
    }

    @Override
    public void revokeJob(ResponseDataBean responseDataBean) {
        ToastMgr.show(R.string.revoke_success);
        hideAllBtn();
    }

    @Override
    public void submitJob(ResponseDataBean responseDataBean) {
        ToastMgr.show(R.string.submit_success);
        isCommited = true;
        hideAllBtn();
        handler.sendEmptyMessage(5);
    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {
        userId = userInfoBean.getId();
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.revoke_btn:
                presenter.revokeJob(jobId);
                break;
            case R.id.commit_btn:
                final boolean scaned = isScaned1 || isScaned2;
                final boolean photoed = isPhotoed1 || isPhotoed2;
                if (scaned && photoed && map != null && map.size() > 0) {
                    SubmitJobBody body = new SubmitJobBody();
                    body.code = equipmentModelTv.getText().toString();
                    body.workId = jobId;
                    body.location = PreferencesHelper.getData(Constant.LOCATION_NAME);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String path : map.values()) {
                        stringBuilder.append(path).append(',');
                    }
                    body.imgs = stringBuilder.toString();
                    presenter.submitJob(body);
                } else {
                    ToastUtils.showtoast(this, "扫描和拍照同时完成以后才可以提交！");
                }
                break;
        }
    }
}
