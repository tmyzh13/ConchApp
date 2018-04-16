package com.isoftston.issuser.conchapp.views.check;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.corelibs.views.roundedimageview.CircleImageView;
import com.corelibs.views.roundedimageview.RoundedImageView;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.DeviceAdapter;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.presenter.CheckPresenter;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;
import com.isoftston.issuser.conchapp.views.seacher.SeacherActivity;
import com.isoftston.issuser.conchapp.views.work.ScanCodeActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class CheckFragment extends BaseFragment<CheckView,CheckPresenter> implements CheckView, PtrAutoLoadMoreLayout.RefreshLoadCallback {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.lv_device)
    AutoLoadMoreListView lv_device;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;
    @Bind(R.id.iv_icon)
    CircleImageView iv_icon;

    private DeviceAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_check;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        navBar.setNavTitle(getString(R.string.check_manager));
        navBar.hideBack();
        navBar.showSeach(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SeacherActivity.getLauncher(getContext(),"2"));
            }
        });
        navBar.showSeachColor(2);


        List<DeviceBean> list =new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(new DeviceBean());
        }

        adapter =new DeviceAdapter(getContext());
        adapter.addAll(list);
        lv_device.setAdapter(adapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);

        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(CheckDeviceDetailActivity.getLauncher(getContext(),adapter.getItem(position)));
            }
        });

        Glide.with(getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524193800&di=ec7643dc32956b231ab9694a6c853c71&imgtype=jpg&er=1&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201503%2F05%2F20150305175720_urKVB.jpeg")
                .override(320,320).into(iv_icon);
        ptrLayout.setRefreshLoadCallback(this);
    }

    @Override
    protected CheckPresenter createPresenter() {
        return new CheckPresenter();
    }

    private static final int OPEN_ACTIVITY_CODE = 101;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;

    @OnClick(R.id.ll_scan)
    public void goScanAction(){
        checkPermission();
    }

    private void startScanCode(){
        Intent intent = new Intent(getViewContext(), CaptureActivity.class);
        startActivityForResult(intent, OPEN_ACTIVITY_CODE);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getViewContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
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
                    ToastMgr.show(getString(R.string.check_manager_open_pemission));
                }
                break;
        }
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
            Log.e("yzh", "checkfrtagment---扫描结果：" + s);
            if(s.equals(getString(R.string.check_manager_cancel_scan))){
                //取消扫码
                ToastMgr.show(getString(R.string.check_manager_cancel_scan));
            }else{
                ((BaseActivity)getActivity()).getLoadingDialog().show();
                presenter.checkDevice(s, Tools.getCurrentTime());
            }
        }
    }

    @Override
    public void showLoading() {
        ptrLayout.setRefreshing();
    }

    @Override
    public void hideLoading() {
        ptrLayout.complete();
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
        ptrLayout.disableLoading();
    }

    @Override
    public void renderDatas(boolean reload, List<DeviceBean> list) {
        if(reload){
            adapter.replaceAll(list);
        }else{
            adapter.addAll(list);
        }
    }

    @Override
    public void checkDeviceResult(DeviceBean bean) {
        ((BaseActivity)getActivity()).getLoadingDialog().dismiss();
    }

    @Override
    public void checkDeviceResultError() {
        //防止网络请求失败，或者返回数据异常导致无法触发loading消失操作
        ((BaseActivity)getActivity()).getLoadingDialog().dismiss();
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
        presenter.getDevice(false);
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        if(!frame.isAutoRefresh()){
            ptrLayout.enableLoading();
            presenter.getDevice(true);
        }
    }
}

