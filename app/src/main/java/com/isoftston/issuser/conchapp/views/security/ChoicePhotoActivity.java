package com.isoftston.issuser.conchapp.views.security;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.NoScrollingGridView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.SelectImageHelper;
import com.isoftston.issuser.conchapp.weight.ChooseImagePopupWindow;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by issuser on 2018/4/11.
 */

public class ChoicePhotoActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.gv_pic)
    NoScrollingGridView gv_pic;

    private Context context=ChoicePhotoActivity.this;
    private SelectImageHelper helper;
    private ChooseImagePopupWindow window;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,ChoicePhotoActivity.class);
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

        helper =new SelectImageHelper(3,gv_pic, R.layout.item_select_pic);
        helper.toObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(final Integer position) {

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //如果没有授权，则请求授权
                        ActivityCompat.requestPermissions(ChoicePhotoActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                    } else {
                        if (window == null)
                            window = new ChooseImagePopupWindow(context);

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
    protected BasePresenter createPresenter() {
        return null;
    }
}
