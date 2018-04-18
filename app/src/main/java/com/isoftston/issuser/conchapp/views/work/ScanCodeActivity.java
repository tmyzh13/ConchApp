package com.isoftston.issuser.conchapp.views.work;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
import com.isoftston.issuser.conchapp.model.bean.ScanInfo;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.presenter.WorkDetailPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.WorkDetailView;
import com.isoftston.issuser.conchapp.views.mine.adapter.ScanInfoAdapter;
import com.isoftston.issuser.conchapp.views.security.ChoicePhotoActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.io.File;
import java.util.ArrayList;
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
    @Bind(R.id.project_name_time_hour)
    TextView projectTimeHourTv;//项目时间（时分）
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

    @Bind(R.id.scan_success_layout)
    LinearLayout scanSuccessHintLayout;//扫码成功，点击可继续扫码或拍照布局
    @Bind(R.id.scan_or_photo_tv)
    TextView scanOrPhotoSuccessTv;//扫码/拍照成功

    @Bind(R.id.scan_layout_outter)//没有扫过二维码显示的布局
            LinearLayout scanCodeLl;
    LinearLayout scanCodeLayout;////扫码
    LinearLayout takePhotoLayout;//拍照
    ImageView scanFlagIv1, photoFlagIv1;//第一轮扫码、拍照标记view
    private boolean isScaned1 = false;//是否扫过
    private boolean isPhotoed1 = false;//是否拍过照

    @Bind(R.id.scaned_layout)
    LinearLayout scanedLayout;
    @Bind(R.id.scan_layout_inner)//扫过二维码后显示的布局
            LinearLayout scanCodeLlInner;
    LinearLayout scanCodeInner;//扫码
    LinearLayout takePhotoInnerLayout;//拍照
    ImageView scanFlagIv2, photoFlagIv2;//第二轮扫码、拍照标记view

    @Bind(R.id.scan_info_lv)
    ListView mListView;
    private List<ScanInfo> datas;
    private ScanInfoAdapter mAdapter;
    private boolean isScaned2 = false;
    private boolean isPhotoed2 = false;

    private boolean isChargePerson;//是否为负责人
    private boolean isGurdianPerson;//是否为监护人
    private boolean isAuditorPerson;//是否为审核人
    private boolean isApproverPerson;//是否为批准人
    private boolean isChargePersonScaned;//负责人是否扫过
    private boolean isChargePersonPhotoed;//负责人是拍过
    private boolean isOneTurnDone;//第一轮是否完成

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, ScanCodeActivity.class);
        return intent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.work_point_information));
        refreshIv.setVisibility(View.VISIBLE);
        refreshIv.setImageResource(R.mipmap.refresh);//改为刷新
        checkUserPosition();
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
//        mListView.setVisibility(View.GONE);
    }

    /**
     * 先获取用户信息,然后判断其职位
     * 检查当前用户的职位
     * 扫描和拍照后改变ui
     */
    private void checkUserPosition() {
        presenter.getWorkDetailInfo("cn", 01);
        if (isOneTurnDone){
            changeScanLayout();
        }
        if (isChargePerson) {//负责人
            if (isChargePersonScaned) {
                scanSuccessHintLayout.setVisibility(View.VISIBLE);
                scanOrPhotoSuccessTv.setText(R.string.scan_code);
                scanFlagIv1.setVisibility(View.VISIBLE);
            }
            if (isChargePersonPhotoed) {
                scanSuccessHintLayout.setVisibility(View.VISIBLE);
                scanOrPhotoSuccessTv.setText(R.string.photo_action);
                photoFlagIv1.setVisibility(View.VISIBLE);
            }
            if (isChargePersonScaned && isChargePersonPhotoed){
                chargePersonIv.setImageResource(R.drawable.dots_green);
                chargePersonTv.setTextColor(getResources().getColor(R.color.colorGreen));
                chargePersonRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
                personInChargeNmaeTv.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        } else if (isGurdianPerson) {//监护人
            if (isChargePersonScaned) {//改变ui
                if (isScaned1) {
                    scanSuccessHintLayout.setVisibility(View.VISIBLE);
                    scanOrPhotoSuccessTv.setText(R.string.scan_code);//提示已扫描
                    scanFlagIv1.setVisibility(View.VISIBLE);//显示已扫码图标
                } else if (isPhotoed1) {
                    scanSuccessHintLayout.setVisibility(View.VISIBLE);
                    scanOrPhotoSuccessTv.setText(R.string.photo_action);
                    photoFlagIv1.setVisibility(View.VISIBLE);//显示已拍照图标
                }
                if (isScaned1 && isPhotoed1) {
                    guardianPersonIv.setImageResource(R.drawable.dots_green);//圆点变色
                    guardianTv.setTextColor(getResources().getColor(R.color.colorGreen));//圆点文字变色
                    guardianRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));//具体人名变色
                    guardianNameTv.setTextColor(getResources().getColor(R.color.colorGreen));//人名变色
                }
            }
        } else if (isAuditorPerson) {//审核人
            if (isChargePersonScaned) {
                if (isScaned1) {
                    scanSuccessHintLayout.setVisibility(View.VISIBLE);
                    scanOrPhotoSuccessTv.setText(R.string.scan_code);
                    scanFlagIv1.setVisibility(View.VISIBLE);
                } else if (isPhotoed1) {
                    scanSuccessHintLayout.setVisibility(View.VISIBLE);
                    scanOrPhotoSuccessTv.setText(R.string.photo_action);
                    photoFlagIv1.setVisibility(View.VISIBLE);
                }
                if (isScaned1 && isPhotoed1) {
                    auditorPersonIv.setImageResource(R.drawable.dots_green);
                    auditorTv.setTextColor(getResources().getColor(R.color.colorGreen));
                    auditorRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
                    auditorNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
                }
            }
        } else if (isApproverPerson) {//批准人
            if (isScaned1){
                scanSuccessHintLayout.setVisibility(View.VISIBLE);
                scanOrPhotoSuccessTv.setText(R.string.scan_code);
                scanFlagIv1.setVisibility(View.VISIBLE);
            }else if (isPhotoed1){
                scanSuccessHintLayout.setVisibility(View.VISIBLE);
                scanOrPhotoSuccessTv.setText(R.string.photo_action);
                photoFlagIv1.setVisibility(View.VISIBLE);
            }
            if (isScaned1 && isPhotoed1){
                approverPersonIv.setImageResource(R.drawable.dots_green);
                approverTv.setTextColor(getResources().getColor(R.color.colorGreen));
                approverRelnameTv.setTextColor(getResources().getColor(R.color.colorGreen));
                approverNameTv.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        }
    }

    private void initView() {
        //没有扫描和拍照，显示布局
        scanCodeLayout = scanCodeLl.findViewById(R.id.scan_code_layout);
        takePhotoLayout = scanCodeLl.findViewById(R.id.take_photo_layout);
        //所有人扫过后按钮在内部显示
        scanCodeInner = scanCodeLlInner.findViewById(R.id.scan_code_layout);
        takePhotoInnerLayout = scanCodeLlInner.findViewById(R.id.take_photo_layout);
    }

    @OnClick(R.id.modify_btn)
    public void modify() {//修改
        Intent intent = new Intent(this, FixWorkActivity.class);
        startActivity(intent);
    }

    /**
     * 刷新数据
     */
    @OnClick(R.id.iv_add)
    public void refresh() {
        presenter.getWorkDetailInfo("cn", 1);
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
                startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this, "1", list), OPEN_ACTIVITY_TAKE_PHOTO_CODE);
            }
        });
    }

    private ArrayList<String> list = new ArrayList<>();

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
                startActivityForResult(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this, "1", list), OPEN_ACTIVITY_TAKE_PHOTO_CODE);
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
                showResultView(s);
//                changeScanLayout();
            }
        } else if (requestCode == OPEN_ACTIVITY_TAKE_PHOTO_CODE && resultCode == 10) {
            list = data.getStringArrayListExtra(Constant.TEMP_PIC_LIST);
            Log.e(TAG, "----size:" + list.size() + ",--" + list.toString());
        }
    }

    /**
     * 提示扫码成功
     */
    private void showResultView(String result) {
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
            }
        });

    }

    /**
     * 所有人扫过后显示布局
     */
    private void changeScanLayout() {
        scanCodeLl.setVisibility(View.GONE);
        scanedLayout.setVisibility(View.VISIBLE);
        scanCodeInner.setBackgroundResource(R.drawable.scaned_code_btn_shape);
        takePhotoInnerLayout.setBackgroundResource(R.drawable.scaned_code_btn_shape);
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
    public void getWorkDetailInfo(WorkDetailBean workDetailBean) {
        //填充数据
    }

    @Override
    public void responseError(int type) {
        if (type == 0) {
            Log.e(TAG, "----获取作业详细信息失败");
        } else if (type == 1) {
            Log.e(TAG, "----撤销作业失败");
        } else if (type == 2) {
            Log.e(TAG, "----提交作业失败");
        }
    }

    @Override
    public void revokeJob(ResponseDataBean responseDataBean) {

    }

    @Override
    public void submitJob(ResponseDataBean responseDataBean) {

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
                presenter.revokeJob(11);
                break;
            case R.id.commit_btn:
                presenter.submitJob(1, "11", list.toString());
                break;
        }
    }
}
