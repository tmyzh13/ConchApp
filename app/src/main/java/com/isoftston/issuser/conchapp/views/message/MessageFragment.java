package com.isoftston.issuser.conchapp.views.message;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.event.MyEvent;
import com.isoftston.issuser.conchapp.utils.LocationUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.message.adpter.MessageListviewAdapter;
import com.isoftston.issuser.conchapp.views.message.adpter.VpAdapter;
import com.isoftston.issuser.conchapp.views.work.CityLocationActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MessageFragment";
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_seach)
    ImageView iv_seach;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    @Bind(R.id.yh_detail)
    LinearLayout ll_yh_detail;
    @Bind(R.id.wz_detail)
    LinearLayout ll_wz_detail;
    @Bind(R.id.aq_detail)
    LinearLayout ll_aq_detail;
    @Bind(R.id.bt_yh)
    Button bt_yh;
    @Bind(R.id.iv_dirict)
    ImageView iv_direc;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.bt_aq)
    Button bt_aq;
    @Bind(R.id.bt_wz)
    Button bt_wz;
    @Bind(R.id.listview)
    ListView listView;
    private List<View> list = null;
    private List<String> datas = new ArrayList<>();
    private boolean up = false;
    @Bind(R.id.location_iv)
    ImageView locationIv;
    @Bind(R.id.location_city_tv)
    TextView locationCityTv;
    public static final int LOCATION_REQUEST_CODE = 100;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int currentItem = viewPager.getCurrentItem();
            //切换至下一个页面
            viewPager.setCurrentItem(++currentItem);
            //再次调用
//            handler.sendEmptyMessageDelayed(1, 2000);
        }

        ;
    };

    private View view;

    @Override
    protected int getLayoutId() {
        initDate();
        return R.layout.fragment_message;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.main_message));
        nav.hideBack();
        iv_seach.setVisibility(View.VISIBLE);
//        ll_main.setOnClickListener(this) ;
        VpAdapter adapter = new VpAdapter(list, handler);
        ll_main.setOnClickListener(this);
        addData();
        MessageListviewAdapter messageListviewAdapter = new MessageListviewAdapter(getActivity(), datas);
        listView.setAdapter(messageListviewAdapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
        iv_direc.setOnClickListener(this);
        bt_yh.setOnClickListener(this);
        bt_aq.setOnClickListener(this);
        bt_wz.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_aq_detail.setOnClickListener(this);
        ll_wz_detail.setOnClickListener(this);;
        ll_yh_detail.setOnClickListener(this);
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getActivity(), "当前是第" + position + "个条目", Toast.LENGTH_SHORT).show();
                ToastUtils.showtoast(getActivity(), "当前是第" + position + "个条目");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ItemDtailActivity.class);
                startActivity(intent);
            }
        });
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //申请摄像头权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }else {
            setLocationCity();
        }
    }

    private void addData() {
        for (int i = 0; i < 10; i++) {
            datas.add("我这是假数据假数据假数据" + i + "itme");
        }
    }

    private void initDate() {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.viewpager_yh_item, null);
            } else if (i == 1) {
                view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.viewpager_wz_item, null);
            } else {
                view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.viewpager_aq_item, null);
            }

            list.add(view);
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_dirict:
                if (up){
                    up=false;
                    iv_direc.setImageDrawable(getResources().getDrawable(R.mipmap.up));
                    bt_yh.setVisibility(View.GONE);
                    bt_wz.setVisibility(View.GONE);
                    bt_aq.setVisibility(View.GONE);
                    ll_aq_detail.setVisibility(View.VISIBLE);
                    ll_wz_detail.setVisibility(View.VISIBLE);
                    ll_yh_detail.setVisibility(View.VISIBLE);
                }else {
                    up=true;
                    iv_direc.setImageDrawable(getResources().getDrawable(R.mipmap.down));
                    bt_yh.setVisibility(View.VISIBLE);
                    bt_wz.setVisibility(View.VISIBLE);
                    bt_aq.setVisibility(View.VISIBLE);
                    ll_aq_detail.setVisibility(View.GONE);
                    ll_wz_detail.setVisibility(View.GONE);
                    ll_yh_detail.setVisibility(View.GONE);
                }

                break;
            case R.id.bt_aq:
            case R.id.bt_wz:
            case R.id.bt_yh:
            case R.id.aq_detail:
            case R.id.yh_detail:
            case R.id.wz_detail:
                nav.setNavTitle(getString(R.string.home_message));
                viewPager.setVisibility(View.VISIBLE);
                ll_main.setVisibility(View.GONE);
                iv_back.setImageDrawable(getResources().getDrawable(R.mipmap.back));
                iv_back.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }

    @OnClick(R.id.location_city_tv)
    public void location() {
        Intent intent = new Intent(getViewContext(), CityLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //手动选择城市
    @Subscribe
    public void getUserChocedCity(MyEvent event) {
        //to
        locationCityTv.setText(event.cityName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setLocationCity();
            }
        }
    }
    //自动定位
    private void setLocationCity() {
        LocationUtils.getCNBylocation(getActivity());
        locationCityTv.setText(LocationUtils.cityName);
        Log.e(TAG,"--CITY:"+LocationUtils.cityName);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
