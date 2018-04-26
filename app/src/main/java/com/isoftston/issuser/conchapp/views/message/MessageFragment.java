package com.isoftston.issuser.conchapp.views.message;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BaseFragment;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypeAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageItemBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;
import com.isoftston.issuser.conchapp.model.event.MyEvent;
import com.isoftston.issuser.conchapp.presenter.MessagePresenter;
import com.isoftston.issuser.conchapp.utils.LocationUtils;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.interfaces.MessageView;
import com.isoftston.issuser.conchapp.views.message.adpter.VpAdapter;
import com.isoftston.issuser.conchapp.views.seacher.SeacherActivity;
import com.isoftston.issuser.conchapp.views.work.CityLocationActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by issuser on 2018/4/9.
 */

public class MessageFragment extends BaseFragment<MessageView, MessagePresenter> implements MessageView, View.OnClickListener {
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
    @Bind(R.id.yh_msg)
    TextView yhMsg;
    @Bind(R.id.wz_msg)
    TextView wzMsg;
    @Bind(R.id.iv_dirict)
    ImageView iv_direc;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.bt_aq)
    Button bt_aq;
    @Bind(R.id.bt_wz)
    Button bt_wz;
    @Bind(R.id.lv_message)
    AutoLoadMoreListView lv_message;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;
    private List<View> list = null;
    private List<String> datas = new ArrayList<>();
    private boolean up = false;
    @Bind(R.id.location_iv)
    ImageView locationIv;
    @Bind(R.id.location_city_tv)
    TextView locationCityTv;
    @Bind(R.id.tv_wzg_num)
    TextView tv_wzg_num;
    @Bind(R.id.tv_yh_total)
    TextView tv_yh_total;
    @Bind(R.id.tv_aq_total)
    TextView tv_aq_total;
    @Bind(R.id.tv_wz_total)
    TextView tv_wz_total;


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
    public MessageTypeAdapter mAdapter;
    public List<MessageBean> listAllMessage = new ArrayList<>();
    public List<MessageBean> listYhMessage = new ArrayList<>();
    public List<MessageBean> listWzMessage = new ArrayList<>();
    public List<MessageBean> listAqMessage = new ArrayList<>();
    private int currrentPage;
    private boolean isChange;

    @Override
    protected int getLayoutId() {
        initDate();
        return R.layout.fragment_message;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        presenter.getUserInfo();
        EventBus.getDefault().register(this);
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.main_message));
        nav.hideBack();
        iv_seach.setVisibility(View.VISIBLE);
        iv_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SeacherActivity.getLauncher(getContext(), "0"));
            }
        });

        VpAdapter adapter = new VpAdapter(list, handler);
        ll_main.setOnClickListener(this);
        mAdapter = new MessageTypeAdapter(getContext());
        presenter.getMessageListInfo("all", "");
        lv_message.setAdapter(mAdapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
        iv_direc.setOnClickListener(this);
        bt_yh.setOnClickListener(this);
        bt_aq.setOnClickListener(this);
        bt_wz.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_aq_detail.setOnClickListener(this);
        ll_wz_detail.setOnClickListener(this);
        ll_yh_detail.setOnClickListener(this);
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
//        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
//                    case 0:
//                        currrentPage = 1;
//                        isChange = true;
//                        presenter.getMessageListInfo("yh","");
//                        break;
//                    case 1:
//                        currrentPage = 2;
//                        isChange = true;
//                        presenter.getMessageListInfo("wz","");
//                        break;
//                    case 2:
//                        currrentPage = 3;
//                        isChange = true;
//                        presenter.getMessageListInfo("aq","");
//                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ItemDtailActivity.class);
                Bundle bundle=new Bundle();
                if (currrentPage == 0){
                    bundle.putString("type",listAllMessage.get(i).getType());
                    bundle.putString("id",listAllMessage.get(i).getId());
                }else if (currrentPage == 1){
                    bundle.putString("type",listYhMessage.get(i).getType());
                    bundle.putString("id",listYhMessage.get(i).getId());
                }else if (currrentPage == 2){
                    bundle.putString("type",listWzMessage.get(i).getType());
                    bundle.putString("id",listWzMessage.get(i).getId());
                }else {
                    bundle.putString("type",listAqMessage.get(i).getType());
                    bundle.putString("id",listAqMessage.get(i).getId());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv_message.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount  && totalItemCount != 0&& totalItemCount!=lv_message.getHeaderViewsCount()
                        + lv_message.getFooterViewsCount() && mAdapter.getCount() > 0) {
                    loadNextPage();
                }
            }
        });
        checkLocationPermission();

    }

    private void loadNextPage(){
        if (currrentPage == 0){
            presenter.getMessageListInfo("all","");
        }else if (currrentPage == 1){
            presenter.getMessageListInfo("yh","");
        }else if (currrentPage == 2){
            presenter.getMessageListInfo("wz","");
        }else {
            presenter.getMessageListInfo("aq","");
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //申请摄像头权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else {
            setLocationCity();
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
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_dirict:
                if (up) {
                    up = false;
                    iv_direc.setImageDrawable(getResources().getDrawable(R.mipmap.up));
                    bt_yh.setVisibility(View.GONE);
                    bt_wz.setVisibility(View.GONE);
                    bt_aq.setVisibility(View.GONE);
                    ll_aq_detail.setVisibility(View.VISIBLE);
                    ll_wz_detail.setVisibility(View.VISIBLE);
                    ll_yh_detail.setVisibility(View.VISIBLE);
                } else {
                    up = true;
                    iv_direc.setImageDrawable(getResources().getDrawable(R.mipmap.down));
                    bt_yh.setVisibility(View.VISIBLE);
                    bt_wz.setVisibility(View.VISIBLE);
                    bt_aq.setVisibility(View.VISIBLE);
                    ll_aq_detail.setVisibility(View.GONE);
                    ll_wz_detail.setVisibility(View.GONE);
                    ll_yh_detail.setVisibility(View.GONE);
                }

                break;
            case R.id.bt_yh:
            case R.id.yh_detail:
                changeCurrent(0);
                currrentPage = 1;
                isChange = true;
//                presenter.getMessageListInfo("yh","");
                break;
            case R.id.bt_wz:
            case R.id.wz_detail:
                changeCurrent(1);
                currrentPage = 2;
                isChange = true;
//                presenter.getMessageListInfo("wz","");
                break;
            case R.id.bt_aq:
            case R.id.aq_detail:
                changeCurrent(2);
                currrentPage = 3;
                isChange = true;
//                presenter.getMessageListInfo("aq","");
                break;
            case R.id.iv_back:
                nav.setNavTitle(getString(R.string.main_message));
                viewPager.setVisibility(View.GONE);
                ll_main.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.GONE);
                break;
        }
    }

    private void changeCurrent(int i) {
        nav.setNavTitle(getString(R.string.home_message));
        viewPager.setVisibility(View.VISIBLE);
        ll_main.setVisibility(View.GONE);
        iv_back.setImageDrawable(getResources().getDrawable(R.mipmap.back));
        iv_back.setVisibility(View.VISIBLE);
        viewPager.setCurrentItem(i);
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
        PreferencesHelper.saveData(Constant.LOCATION_NAME, LocationUtils.cityName);;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setLocationCity();
            }
        }
    }

    //自动定位
    private void setLocationCity() {
        LocationUtils.getCNBylocation(getActivity());
        locationCityTv.setText(LocationUtils.cityName);
        PreferencesHelper.saveData(Constant.LOCATION_NAME, LocationUtils.cityName);;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void getMessageDetailResult(MessageDetailBean bean) {
        hideLoading();
    }

    @Override
    public void getMessageListResult(MessageListInfoBean data) {
        ((BaseActivity)getActivity()).getLoadingDialog().dismiss();
        hideLoading();
        Log.i("yh","----获取信息列表");
        Map<String,MessageItemBean> totle=data.totle;
        MessageItemBean bean1=totle.get("aq");
        Log.i("aq",bean1.getTotal()+"");
        MessageItemBean bean2=totle.get("wz");
        Log.i("wz",bean2.getTotal()+"");
        MessageItemBean bean3=totle.get("yh");
        Log.i("yh",bean3.getTotal()+"----"+bean3.getWys());
        tv_aq_total.setText(bean1.getTotal()+"");
        tv_wz_total.setText(bean2.getTotal()+"");
        tv_yh_total.setText(bean3.getTotal()+"");
        tv_wzg_num.setText(bean3.getWzg()+"");
        if (currrentPage == 0){
            listAllMessage.clear();
            listAllMessage = data.list;
        }else if (currrentPage == 1){
            listYhMessage.clear();
            listYhMessage = data.list;
        }else if (currrentPage == 2){
            listWzMessage.clear();
            listWzMessage = data.list;
        }else {
            listAqMessage.clear();
            listAqMessage = data.list;
        }
        if (isChange){
            mAdapter.getData().clear();
            isChange = false;
        }
        mAdapter.addAll(data.list);
        mAdapter.notifyDataSetChanged();
        lv_message.setAdapter(mAdapter);

    }

    @Override
    public void getWorkError() {
        ((BaseActivity)getActivity()).getLoadingDialog().dismiss();
        hideLoading();
        startActivity(LoginActivity.getLauncher(getActivity()));

    }

    @Override
    public void reLogin() {
        ToastUtils.showtoast(getActivity(),getString(R.string.re_login));
        PreferencesHelper.saveData(Constant.LOGIN_STATUE,"");
        startActivity(LoginActivity.getLauncher(getActivity()));
    }
}
