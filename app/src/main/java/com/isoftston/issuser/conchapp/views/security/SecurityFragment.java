package com.isoftston.issuser.conchapp.views.security;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypePageAdapter;
import com.isoftston.issuser.conchapp.views.seacher.SeacherActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/9.
 */

public class SecurityFragment extends BaseFragment {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
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

    private String[] titles;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_security;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        tabLayout.getTabAt(index).setText(name)

        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.home_security));
        nav.hideBack();
        nav.showSeach(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入搜索界面
                startActivity(SeacherActivity.getLauncher(getContext()));
            }
        });
        nav.showAdd(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入隐患问题新增
                startActivity(AddHiddenTroubleActivity.getLauncher(getContext(),"1"));
            }
        });
        titles=new String[]{getString(R.string.search_all),getString(R.string.not_alter),getString(R.string.overdue)};
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.search_all)+"12"));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.not_alter)+"8"));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.overdue)+"4"));

        MessageTypePageAdapter adapter=new MessageTypePageAdapter(getActivity().getSupportFragmentManager(),titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setIndicator(tabLayout,10,10);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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

    @OnClick(R.id.tv_hidden_trouble)
    public void choiceHiddenTrouble(){
        tv_hidden_trouble.setBackground(getResources().getDrawable(R.drawable.tab_security_gradient_bg));
        tv_illegal.setBackground(getResources().getDrawable(R.drawable.tab_security_normal));
        tv_mine.setBackground(getResources().getDrawable(R.drawable.tab_security_normal));
        tv_illegal.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_mine.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_hidden_trouble.setTextColor(getResources().getColor(R.color.white));
        nav.showOrHideAdd(true);
    }

    @OnClick(R.id.tv_illegal)
    public void choicIllegal(){
        tv_illegal.setBackground(getResources().getDrawable(R.drawable.tab_security_illegal_bg));
        tv_hidden_trouble.setBackground(getResources().getDrawable(R.drawable.tab_security_normal));
        tv_mine.setBackground(getResources().getDrawable(R.drawable.tab_security_normal));
        tv_illegal.setTextColor(getResources().getColor(R.color.white));
        tv_mine.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_hidden_trouble.setTextColor(getResources().getColor(R.color.text_color_shallow));
        nav.showOrHideAdd(true);
    }

    @OnClick(R.id.tv_mine)
    public void choiceMine(){
        tv_mine.setBackground(getResources().getDrawable(R.drawable.tab_security_mine));
        tv_hidden_trouble.setBackground(getResources().getDrawable(R.drawable.tab_security_normal));
        tv_illegal.setBackground(getResources().getDrawable(R.drawable.tab_security_normal));
        tv_illegal.setTextColor(getResources().getColor(R.color.text_color_shallow));
        tv_mine.setTextColor(getResources().getColor(R.color.app_blue));
        tv_hidden_trouble.setTextColor(getResources().getColor(R.color.text_color_shallow));
        //选择我的时 新增功能去掉
        nav.showOrHideAdd(false);
    }

}
