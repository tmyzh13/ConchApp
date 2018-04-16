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
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.views.security.ChoicePhotoActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;


public class ScanCodeActivity extends BaseActivity {
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

    @Bind(R.id.guardian_iv)
    ImageView guardianPersonIv;//监护人
    @Bind(R.id.guardian)
    TextView guardianTv;

    @Bind(R.id.auditor_iv)
    ImageView auditorPersonIv;//审核人
    @Bind(R.id.auditor)
    TextView auditorTv;

    @Bind(R.id.approver_iv)
    ImageView approverPersonIv;//审核人
    @Bind(R.id.approver)
    TextView approverTv;
    //底部列表显示具体人名
    @Bind(R.id.person_in_charge_tv)
    TextView personInChargeNmaeTv;//负责人
    @Bind(R.id.guardian_tv)
    TextView guardianNameTv;//监护人
    @Bind(R.id.auditor_tv)
    TextView auditorNameTv;//审核人
    @Bind(R.id.approver_tv)
    TextView approverNameTv;//批准人


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
    private boolean isScaned2 = false;
    private boolean isPhotoed2 = false;

    private boolean isChargePerson;//是否为负责人
    private boolean isGurdianPerson;//是否为监护人
    private boolean isAuditorPerson;//是否为审核人
    private boolean isApproverPerson;//是否为批准人
    private boolean isChargePersonScaned;//负责人是否扫过
    private boolean isChargePersonPhotoed;//负责人是拍过

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
        refreshIv.setImageResource(R.mipmap.add);//改为刷新
        checkUserPosition();
        scan();
        scaned();
    }

    /**
     * 先获取用户信息,然后判断其职位
     * 检查当前用户的职位
     * 扫描和拍照后改变ui
     */
    private void checkUserPosition() {
        if (isChargePerson) {//负责人
            if (isChargePersonScaned) {
                scanFlagIv1.setVisibility(View.VISIBLE);
            }
            if (isChargePersonPhotoed) {
                photoFlagIv1.setVisibility(View.VISIBLE);
            }
        } else if (isGurdianPerson) {//监护人
            if (isChargePersonScaned && isScaned1) {//改变ui
                if (isScaned1) {
                    scanFlagIv1.setVisibility(View.VISIBLE);//显示已扫码图标
                } else if (isPhotoed1) {
                    photoFlagIv1.setVisibility(View.VISIBLE);//显示已拍照图标
                }
                if (isScaned1 && isPhotoed1) {
                    guardianPersonIv.setImageResource(R.drawable.dots_green);//圆点变色
                    guardianTv.setTextColor(getResources().getColor(R.color.colorGreen));//圆点文字变色
                    guardianNameTv.setTextColor(getResources().getColor(R.color.colorGreen));//人名变色
                }
            }
        } else if (isAuditorPerson) {//审核人

        } else if (isApproverPerson) {//批准人

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
                checkPermission(1);
            }
        });
    }

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
                startActivity(ChoicePhotoActivity.getLauncher(ScanCodeActivity.this,"1"));
//                checkPermission(1);
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
        } else if (type == 1) {
            if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请读写权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_STORAGE_PERMISSION_CODE);
            } else {
                startTakePhoto();
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
                    Toast.makeText(getViewContext(), "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获得授权,开始拍照
                    startTakePhoto();
                } else {
                    Toast.makeText(getViewContext(), "请至权限中心打开本应用的读写访问权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void startScanCode() {
        //开始扫描
        Intent intent = new Intent(getViewContext(), CaptureActivity.class);
        startActivityForResult(intent, OPEN_ACTIVITY_SCAN_CODE);
    }

    /**
     * 打开相机
     */
    private File file;

    private void startTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();

        //改变Uri  com.isoftston.conch.fileprovider注意和xml中的一致
        Uri uri = FileProvider.getUriForFile(this, "com.isoftston.conch.fileprovider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, OPEN_ACTIVITY_TAKE_PHOTO_CODE);
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
            showResultView();
            changeScanLayout();
        } else if (requestCode == OPEN_ACTIVITY_TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            changeScanLayout();
            //在手机相册中显示刚拍摄的图片
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);
            Log.e("TAG", "--" + FileProvider.getUriForFile(this, "com.isoftston.conch.fileprovider", file));
//            imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
    }

    /**
     * 提示扫码成功
     */
    private void showResultView() {
        Dialog dialog = new Dialog(this, R.style.Dialog);
        dialog.show();
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewDialog = inflater.inflate(R.layout.scan_code_layout, null);
        Display display = this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
        dialog.setContentView(viewDialog, layoutParams);
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
