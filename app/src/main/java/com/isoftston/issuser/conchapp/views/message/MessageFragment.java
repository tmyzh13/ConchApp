package com.isoftston.issuser.conchapp.views.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.message.adpter.MessageListviewAdapter;
import com.isoftston.issuser.conchapp.views.message.adpter.VpAdapter;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener {
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
    private List<View> list=null;
    private List<String> datas=new ArrayList<>();
    private boolean up=false;
    public Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            int currentItem = viewPager.getCurrentItem();
            //切换至下一个页面
            viewPager.setCurrentItem(++currentItem);
            //再次调用
//            handler.sendEmptyMessageDelayed(1, 2000);
        };
    };
    private View view;

    @Override
    protected int getLayoutId() {
        initDate();
        return R.layout.fragment_message;

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.main_message));
        nav.hideBack();
        iv_seach.setVisibility(View.VISIBLE);
//        ll_main.setOnClickListener(this) ;
        VpAdapter adapter = new VpAdapter(list,handler);
        addData();
        MessageListviewAdapter messageListviewAdapter=new MessageListviewAdapter(getActivity(),datas);
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
               ToastUtils.showtoast(getActivity(),"当前是第"+position+"个条目");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(),ItemDtailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addData() {
        for (int i=0;i<10;i++){
            datas.add("我这是假数据假数据假数据"+i+"itme");
        }
    }

    private void initDate() {
        list=new ArrayList<>();
        for (int i=0;i<3;i++){
            if (i==0){
                view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.viewpager_yh_item, null);
            }else if (i==1){
                view=LayoutInflater.from(getActivity()).inflate(
                        R.layout.viewpager_wz_item, null);
            }else {
                view=LayoutInflater.from(getActivity()).inflate(
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
                    bt_yh.setVisibility(View.GONE);
                    bt_wz.setVisibility(View.GONE);
                    bt_aq.setVisibility(View.GONE);
                    ll_aq_detail.setVisibility(View.VISIBLE);
                    ll_wz_detail.setVisibility(View.VISIBLE);
                    ll_yh_detail.setVisibility(View.VISIBLE);
                }else {
                    up=true;
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
}
