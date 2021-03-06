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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BaseFragment;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.corelibs.views.roundedimageview.CircleImageView;
import com.google.zxing.client.android.CaptureActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.DeviceAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.CheckPresenter;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;
import com.isoftston.issuser.conchapp.views.seacher.SeacherActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class CheckFragment extends BaseFragment<CheckView, CheckPresenter> implements CheckView, PtrAutoLoadMoreLayout.RefreshLoadCallback {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.lv_device)
    AutoLoadMoreListView lv_device;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;
    @Bind(R.id.iv_icon)
    CircleImageView iv_icon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.iv_time)
    TextView tvTime;
    @Bind(R.id.tnNum)
    TextView tvNum;

    private DeviceAdapter adapter;
    private List<DeviceBean> mlist = new ArrayList<>();
    ;
    private UserInfoBean userInfoBean = new UserInfoBean();
    private boolean isLoading = false;
    private String cityName = "";

    private Boolean isUpRefresh = false;
    private boolean isLastRow = false;
    private boolean isDownRefresh = false;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            cityName = aMapLocation.getAddress();
            if ("".equals(cityName)) {
                cityName = getString(R.string.city_name);
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void loadNextPage(int totalItemCount) {
        ptrLayout.setLoading();
        isDownRefresh = true;

        String lastId = adapter.getData().get(totalItemCount - 1).getRecordId().toString();
        presenter.getAllDeviceInfo(lastId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_check;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        //初始化定位
        mLocationClient = new AMapLocationClient(getContext().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        presenter.getUserInfo();
        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        navBar.setNavTitle(getString(R.string.check_manager));
        navBar.hideBack();
        navBar.showSeach(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SeacherActivity.getLauncher(getContext(), "3"));
            }
        });
        navBar.showSeachColor(2);

        //presenter.getAllDeviceInfo("");
        adapter = new DeviceAdapter(getContext());
        adapter.addAll(mlist);
        lv_device.setAdapter(adapter);
//        ptrLayout.disableLoading();
//        ptrLayout.setCanRefresh(false);

        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(CheckDeviceDetailActivity.getLauncher(getContext(), adapter.getItem(position)));
            }
        });

        lv_device.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //当滚到最后一行且停止滚动时，执行加载
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
                    loadNextPage(adapter.getCount());
                    isLastRow = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //判断是否滚到最后一行
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    if (adapter.getCount() > 0) {
                        isLastRow = true;
                    }
                }
            }
        });


        ptrLayout.setRefreshLoadCallback(this);
        //presenter.getDevice(true);

        Calendar now = Calendar.getInstance();
        String txt;
        if (SharePrefsUtils.getValue(getViewContext(), "app_language", "").equals("en")) {
            txt = now.get(Calendar.YEAR) + "年" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        } else {
            txt = now.get(Calendar.YEAR) + "年" + (now.get(Calendar.MONTH) + 1) + "月" + now.get(Calendar.DAY_OF_MONTH) + "日";
        }

        tvTime.setText(txt);

    }

    @Override
    protected CheckPresenter createPresenter() {
        return new CheckPresenter();
    }

    private static final int OPEN_ACTIVITY_CODE = 101;
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 100;

    @OnClick(R.id.ll_scan)
    public void goScanAction() {
        checkPermission();
    }

    private void startScanCode() {
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
        //获取地理位置
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        if (requestCode == OPEN_ACTIVITY_CODE) {
            Bundle bundle = data.getExtras();
            String s = bundle.getString("result");
            Log.e("yzh", "checkfrtagment---扫描结果：" + s);
            if (s.equals(getString(R.string.check_manager_cancel_scan))) {
                //取消扫码
//                presenter.checkDevice("2");
                ToastMgr.show(getString(R.string.check_manager_cancel_scan));
            } else {
                ((BaseActivity) getActivity()).getLoadingDialog().show();

//
//                LocationUtils.getCNBylocation(getActivity());
//                Log.i("yzh", "cityName:" + LocationUtils.cityName);
//                presenter.checkDevice(s, LocationUtils.cityName);
                presenter.checkDevice(s, cityName);
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
        if (reload) {
            adapter.replaceAll(list);
        } else {
            adapter.addAll(list);
        }
    }

    @Override
    public void checkDeviceResult(DeviceBean bean) {
        ((BaseActivity) getActivity()).getLoadingDialog().dismiss();
//        isUpRefresh = true;
//        presenter.getAllDeviceInfo("");
        if (bean == null) {
            ToastMgr.show("设备不存在");
            return;
        }
        ToastMgr.show("扫描成功");

        startActivity(CheckDeviceDetailActivity.getLauncher(getContext(), bean));

    }

    @Override
    public void checkDeviceResultError(String message) {
        //防止网络请求失败，或者返回数据异常导致无法触发loading消失操作
        ((BaseActivity) getActivity()).getLoadingDialog().dismiss();
        ToastMgr.show(message);
    }

    @Override
    public void CheckAllDeviceResult(List<DeviceBean> list, String total) {
        hideLoading();
        this.mlist.clear();
        this.mlist = list;

        if (list.size() == 0 && adapter.getCount() > 0) {
            return;
        }
//        Collections.sort(mlist, new Comparator<DeviceBean>() {
//            @Override
//            public int compare(DeviceBean deviceBean, DeviceBean t1) {
//                return deviceBean.getCreateTime().compareTo(t1.getCreateTime());
//            }
//        });
        Collections.sort(mlist, new DeviceComparator());

        if (isUpRefresh) {
            adapter.clear();
            isUpRefresh = false;
        }

        if (isDownRefresh) {
            isDownRefresh = false;
        }
        adapter.addAll(list);
        //adapter.replaceAll(list);
        adapter.notifyDataSetChanged();
        Log.i("yzh", "fresh size:" + list.size() + ",now size:" + adapter.getCount());
        tvNum.setText(total);
        ptrLayout.complete();
    }

    class DeviceComparator implements Comparator<DeviceBean> {

        @Override
        public int compare(DeviceBean o1, DeviceBean o2) {
            return o2.getCreateTime().compareTo(o1.getCreateTime());
        }
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
//        Log.i("yzh","onloading");
//        if (mlist != null && mlist.size() > 0) {
//            isLoading = true;
//            presenter.getAllDeviceInfo(mlist.get(mlist.size() - 1).getId());
//        } else {
//            ptrLayout.complete();
//        }
    }


    @Override
    public void onRefreshing(PtrFrameLayout frame) {
//        Log.i("yzh","onrefreshing");
//        isLoading = false;
//        if (!frame.isAutoRefresh()) {
//            ptrLayout.enableLoading();
//            presenter.getAllDeviceInfo("");
//        }
        isUpRefresh = true;
        presenter.getAllDeviceInfo("");
    }

    @Override
    public void setUserInfo(UserInfoBean userInfo) {
        userInfoBean = userInfo;
        tvName.setText(userInfoBean.getRealName());
        tvCompany.setText(userInfoBean.getCompanyName());
        if (userInfoBean.getSex() != null) {
            if (userInfoBean.getSex().equals("男")) {
                iv_icon.setImageDrawable(getResources().getDrawable(R.mipmap.man_head));
            } else {
                iv_icon.setImageDrawable(getResources().getDrawable(R.mipmap.woman_head));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isUpRefresh = true;
        presenter.getUserInfo();
        presenter.getAllDeviceInfo("");
        mLocationClient.startLocation();
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void reLogin() {
        ToastUtils.showtoast(getActivity(), getString(R.string.re_login));
        PreferencesHelper.saveData(Constant.LOGIN_STATUE, "");
        startActivity(LoginActivity.getLauncher(getActivity()));
    }
}

