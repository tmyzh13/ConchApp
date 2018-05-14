package com.isoftston.issuser.conchapp.views.security;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.NoScrollingGridView;
import com.google.gson.JsonObject;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.SelectImageHelper;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.utils.FileSizeUtil;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.utils.UploadImage;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.views.work.NewWorkActivity;
import com.isoftston.issuser.conchapp.weight.ChooseImagePopupWindow;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by issuser on 2018/4/11.
 */

public class ChoicePhotoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.gv_pic)
    NoScrollingGridView gv_pic;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    private AsyncTask asyncTask;
    private Context context = ChoicePhotoActivity.this;
    private SelectImageHelper helper;
    private ChooseImagePopupWindow window;
    //0 新增问题  1作业详情
    private String type;
    //需要图片数量
    private int count;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> historylist;
    private HashMap<String, String> mFiles;
    private int kind;//0：拍照、相册 1：拍照 2：相册

    public static Intent getLauncher(Context context, String type, HashMap<String, String> map,int kind) {
        Intent intent = new Intent(context, ChoicePhotoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("files", map);
        intent.putExtra("kind",kind);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_photo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        navBar.setNavTitle(getString(R.string.choice_photo));
        navBar.showBack(2);
        iv_back.setOnClickListener(this);
        type = getIntent().getStringExtra("type");
//        mFiles = (HashMap<String, String>) getIntent().getSerializableExtra("files");
        kind = getIntent().getIntExtra("kind",0);
        if (mFiles == null) {
            mFiles = new HashMap<>();
        }

        if (type.equals("0")) {
            count = 3;
        } else if (type.equals("1")) {
            count = 5;
        }

        helper = new SelectImageHelper(count, gv_pic, R.layout.item_select_pic);
        helper.toObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(final Integer position) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //如果没有授权，则请求授权
                    ActivityCompat.requestPermissions(ChoicePhotoActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                } else {
//                    helper.openCamera(position, "");
                    if(kind==1){
                        helper.openCamera(position, "");
                        return;
                    }
                    if (window == null)
                        window = new ChooseImagePopupWindow(context,kind);
                    window.setOnTypeChosenListener(new ChooseImagePopupWindow.OnTypeChosenListener() {
                        @Override
                        public void onCamera() {
                            helper.openCamera(position, "");
                        }

                        @Override
                        public void onGallery() {
                            helper.openGallery(position, "");
                        }
                    });
                    window.showAtBottom(navBar);
                }
            }
        });
        if (mFiles != null && mFiles.size() != 0) {
            Log.e("yzh", "currentFile");
            ArrayList<String> localFiles = new ArrayList<>();
            for (String path : mFiles.keySet()) {
                localFiles.add(path);
            }
            helper.setPicFiles(localFiles);
        }

    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA) {
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //授权失败
                Toast.makeText(getViewContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }


    @OnClick(R.id.tv_confirm)
    public void confirm() {

        List<File> listFiles = helper.getChosenImages();
        for (int i = 0; i < listFiles.size(); i++) {
            String path = FileSizeUtil.compressImage(listFiles.get(i).getPath());
            list.add(path);
        }

        if (list.size() == 0) {
            ToastUtils.showtoast(context, "还未上传图片");
            finish();
            return;
        }

        showLoading();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                hideLoading();
                switch (msg.arg1) {
                    case 1:
                        HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                        if (map != null && map.size() > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(Constant.TEMP_PIC_LIST, map);
                            setResult(10, intent);
                            ToastUtils.showtoast(context, getString(R.string.submit_success));
                            finish();
                        } else {
                            ToastUtils.showtoast(context, getString(R.string.submit_fail));
                        }
                        break;
                    case 0:
                        ToastUtils.showtoast(context, getString(R.string.submit_fail));
                        break;
                }
            }
        };

        //上传图片
        String token = SharePrefsUtils.getValue(context, "token", null);
        final String token1 = token.replaceAll("\"", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String picPath = "";
                Message message = new Message();
                HashMap<String, String> files = new HashMap<>();
                for (final String path : list) {
                    String uploadPath = mFiles.get(path);
                    if (TextUtils.isEmpty(uploadPath)) { // 没上传过就上传
                        picPath = UploadImage.uploadFile(Urls.ROOT + Urls.UPLOAD_IMAGE, path, token1);
                        if (picPath == null) {
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            return;
                        }
                        com.alibaba.fastjson.JSONObject object = null;
                        try {
                            object = com.alibaba.fastjson.JSONObject.parseObject(picPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (object == null) {
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            return;
                        }

                        uploadPath = object.getString("mess").toString();
                    }
                    // 上传过就直接使用地址
                    files.put(path, uploadPath);
                }
                message.arg1 = 1;
                message.obj = files;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showDialogView();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                showDialogView();
                break;
        }
    }

    private void showDialogView() {
        AlertDialog alert = new AlertDialog.Builder(context).setTitle(R.string.information_tips)
                .setMessage(R.string.exsit)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override//处理确定按钮点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel_action, new DialogInterface.OnClickListener() {
                    @Override//取消按钮点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();//对话框关闭。
                    }
                }).create();

        alert.show();
    }
}
