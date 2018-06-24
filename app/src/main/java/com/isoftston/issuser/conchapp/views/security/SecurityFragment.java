package com.isoftston.issuser.conchapp.views.security;

import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.IllegalTypeAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.MsgTotalCountBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.views.message.utils.PushBroadcastReceiver;
import com.isoftston.issuser.conchapp.views.message.utils.PushCacheUtils;
import com.isoftston.issuser.conchapp.views.seacher.SeacherActivity;
import com.isoftston.issuser.conchapp.weight.MyViewPager;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class SecurityFragment extends BaseFragment<SecuryView, SecurityPresenter> {

    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.tv_hidden_trouble)
    TextView tv_hidden_trouble;
    @Bind(R.id.tv_hidden_trouble_count)
    TextView tv_hidden_trouble_count;
    @Bind(R.id.tv_illegal_count)
    TextView tv_illegal_count;
    @Bind(R.id.tv_illegal)
    TextView tv_illegal;
    @Bind(R.id.tv_mine)
    TextView tv_mine;
    @Bind(R.id.tv_mine_acount)
    TextView tv_mine_count;
    @Bind(R.id.myViewPager)
    MyViewPager myViewPager;

    private Integer yhTotalCount = 0;

    private Integer wzTotalCount = 0;

    //选择当前信息类型 默认隐患
    private String type = "yh";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_security;
    }

    private PushBroadcastReceiver broadcastReceiver;
    IllegalTypeAdapter adapter;
    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setConcerMark();
        }
    };

    @Override
    protected void init(Bundle savedInstanceState) {
//        tabLayout.getTabAt(index).setText(name)

        EventBus.getDefault().register(this);
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.home_security));
        nav.hideBack();
        nav.showSeach(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入搜索界面
                startActivity(SeacherActivity.getLauncher(getContext(), "1"));
            }
        });

        if ("false".equals(PreferencesHelper.getData(Constant.YH_ADD))){
            nav.showOrHideAdd(false);
        }else {
            nav.showAdd(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入隐患问题新增
                    if (type.equals("yh")) {
                        startActivity(AddHiddenTroubleActivity.getLauncher(getContext()));
                    } else if (type.equals("wz")) {
                        //新增违章
                        startActivity(AddIllegalActivity.getLauncher(getContext()));
                    }

                }
            });
        }

        adapter = new IllegalTypeAdapter(getActivity().getSupportFragmentManager());
        myViewPager.setOffscreenPageLimit(3);
        myViewPager.setAdapter(adapter);
        setConcerMark();
    }

    //手动选择城市
    @Subscribe
    public void getTotalCountBean(MsgTotalCountBean bean) {
        if(bean.getIsUpdate() == 1)
        {
            yhTotalCount = yhTotalCount + bean.getYhCount();
            wzTotalCount = wzTotalCount + bean.getWzCount();
        }
        else
        {
            yhTotalCount = bean.getYhCount();
            wzTotalCount = bean.getWzCount();
        }

        String hiddenTxt = getResources().getString(R.string.hidden_trouble) + " " + yhTotalCount;
        tv_hidden_trouble.setText(hiddenTxt);
        String illegalTxt = getResources().getString(R.string.illegal_msg) + " " + wzTotalCount;
        tv_illegal.setText(illegalTxt);
    }

    private void setConcerMark() {
        List<MessageBean> list = PushCacheUtils.getInstance().readPushLocalCache(getContext());
        int yhCpunt = PushCacheUtils.getInstance().getTypeMessageCount(list, "1");
        tv_hidden_trouble_count.setVisibility(View.GONE);
        if (yhCpunt > 0) {
            tv_hidden_trouble_count.setVisibility(View.VISIBLE);
            tv_hidden_trouble_count.setText(yhCpunt + "");
        }
        tv_illegal_count.setVisibility(View.GONE);
        int wzCpunt = PushCacheUtils.getInstance().getTypeMessageCount(list, "2");
        if (wzCpunt > 0) {
            tv_illegal_count.setVisibility(View.VISIBLE);
            tv_illegal_count.setText(wzCpunt + "");

        }
    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }

    // 具体方法（通过反射的方式）
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    private void registerBroadcast() {
        broadcastReceiver = new PushBroadcastReceiver(mHander);
        IntentFilter intentFilter = new IntentFilter("getThumbService");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        setConcerMark();
        registerBroadcast();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onPause();
    }

    @OnClick(R.id.tv_hidden_trouble)
    public void choiceHiddenTrouble() {
        tv_hidden_trouble.setBackgroundResource(R.drawable.tab_security_gradient_bg);
        tv_illegal.setBackgroundResource(R.drawable.tab_security_normal);
        tv_mine.setBackgroundResource(R.drawable.tab_security_normal);
        tv_illegal.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_mine.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_hidden_trouble.setTextColor(getResources().getColor(R.color.white));
        if ("false".equals(PreferencesHelper.getData(Constant.YH_ADD))){
            nav.showOrHideAdd(false);
        }else {
            nav.showOrHideAdd(true);
        }
        type = "yh";
        myViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.tv_illegal)
    public void choicIllegal() {
        tv_illegal.setBackgroundResource(R.drawable.tab_security_illegal_bg);
        tv_hidden_trouble.setBackgroundResource(R.drawable.tab_security_normal);
        tv_mine.setBackgroundResource(R.drawable.tab_security_normal);
        tv_illegal.setTextColor(getResources().getColor(R.color.white));
        tv_mine.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_hidden_trouble.setTextColor(getResources().getColor(R.color.text_color_shallow));
        if ("false".equals(PreferencesHelper.getData(Constant.YH_ADD))){
            nav.showOrHideAdd(false);
        }else {
            nav.showOrHideAdd(true);
        }
        type = "wz";
        myViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.tv_mine)
    public void choiceMine() {
        tv_mine.setBackgroundResource(R.drawable.tab_security_mine);
        tv_hidden_trouble.setBackgroundResource(R.drawable.tab_security_normal);
        tv_illegal.setBackgroundResource(R.drawable.tab_security_normal);
        tv_illegal.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_mine.setTextColor(getResources().getColor(R.color.app_blue));
        tv_hidden_trouble.setTextColor(getResources().getColor(R.color.text_color_shallow));
        //选择我的时 新增功能去掉
        nav.showOrHideAdd(false);
        type = "wd";
        myViewPager.setCurrentItem(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
