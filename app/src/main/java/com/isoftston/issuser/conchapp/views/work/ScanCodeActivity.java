package com.isoftston.issuser.conchapp.views.work;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;


public class ScanCodeActivity extends BaseActivity {
    private static final String TAG = "ScanCodeActivity";
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;
    private static final int OPEN_ACTIVITY_CODE = 101;

    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.revoke_btn)
    Button revokeBtn;
    @Bind(R.id.commit_btn)
    Button commitBtn;
    @Bind(R.id.modify_btn)
    Button modifyBtn;

    @Bind(R.id.scan_layout_outter)//没有扫过二维码显示的布局
            LinearLayout scanCodeLl;
    LinearLayout scanCodeLayout;//没有扫过二维码显示的布局

    @Bind(R.id.scaned_layout)
    LinearLayout scanedLayout;
    @Bind(R.id.scan_layout_inner)//扫过二维码后显示的布局
            LinearLayout scanCodeLlInner;
    LinearLayout scanCodeInner;


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
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.work_point_information));
//        nav.hideBack();
        scan();
        scaned();
    }

    @OnClick(R.id.modify_btn)
    public void modify() {//修改
//        Intent intent = new Intent(this, );
//        startActivity(intent);
    }

    private void scaned() {//扫过二维码
        scanCodeInner = scanCodeLlInner.findViewById(R.id.scan_code_layout);
        scanCodeInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void scan() {//没有扫过二维码
        scanCodeLayout = scanCodeLl.findViewById(R.id.scan_code_layout);
        scanCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    @OnClick(R.id.scan_code_layout)
    public void scanCode() {
        checkPermission();//检查摄像头权限
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
        } else {
            startScanCode();
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
        }
    }

    private void startScanCode() {
        //开始扫描
        Intent intent = new Intent(getViewContext(), CaptureActivity.class);
        startActivityForResult(intent, OPEN_ACTIVITY_CODE);
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
        if (requestCode == OPEN_ACTIVITY_CODE) {
            Bundle bundle = data.getExtras();
            String s = bundle.getString("result");
            Log.e(TAG, "---扫描结果：" + s);
            scanCodeLl.setVisibility(View.GONE);
            scanedLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
