package com.isoftston.issuser.conchapp.views.work;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkCountBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkRequestCountBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.views.seacher.SeacherActivity;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkTypeAdapter;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class WorkFragment extends BaseFragment<WorkView,WorkPresenter>  implements WorkView,View.OnClickListener {
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_add)
    ImageView iv_add;
    @Bind(R.id.iv_seach)
    ImageView iv_seach;
    @Bind((R.id.moretab_viewPager))
    ViewPager viewPager;
    @Bind(R.id.tv_danger_work)
    TextView tv_danger_work;
    @Bind(R.id.tv_common_work)
    TextView tv_common_work;
    @Bind(R.id.tv_mine)
    TextView tv_mine;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.home_work));
        nav.hideBack();
        WorkRequestCountBean bean=new WorkRequestCountBean();
        bean.setType("0");
        presenter.getWorkCount(bean);
        tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_work_normal));
        iv_seach.setVisibility(View.VISIBLE);
        iv_add.setVisibility(View.VISIBLE);
        iv_seach.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        tv_common_work.setOnClickListener(this);
        tv_danger_work.setOnClickListener(this);
        tv_mine.setOnClickListener(this);
        WorkTypeAdapter adapter=new WorkTypeAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setDangerWorkUI();
                        break;
                    case 1:
                        setCommonWorkUI();
                        break;
                    case 2:
                        setMineWork();
                        break;
                        default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if ("false".equals(PreferencesHelper.getData(Constant.ZY_ADD))){
            iv_add.setVisibility(View.GONE);
        }
    }

    @Override
    protected WorkPresenter createPresenter() {
        return new WorkPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_add:
                int item = viewPager.getCurrentItem();
                Intent intent=new Intent(getActivity(),NewWorkActivity.class);
                intent.putExtra("isDangerWork",item);
                startActivity(intent);
                break;
            case R.id.iv_seach:
                startActivity(SeacherActivity.getLauncher(getContext(),"2"));
                break;
            case R.id.tv_danger_work:
                setDangerWorkUI();
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_common_work:
                setCommonWorkUI();
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_mine:
                setMineWork();
                viewPager.setCurrentItem(2);
                break;
        }
    }

    private void setMineWork() {
        iv_add.setVisibility(View.GONE);
        tv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_security_mine));
        tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
        tv_common_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
    }

    private void setCommonWorkUI() {
        if ("false".equals(PreferencesHelper.getData(Constant.ZY_ADD))){
            iv_add.setVisibility(View.GONE);
        }else {
            iv_add.setVisibility(View.VISIBLE);
        }
        tv_common_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_work_gradient_bg));
        tv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
        tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
    }

    private void setDangerWorkUI() {
        if ("false".equals(PreferencesHelper.getData(Constant.ZY_ADD))){
            iv_add.setVisibility(View.GONE);
        }else{
            iv_add.setVisibility(View.VISIBLE);
        }
        tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_work_normal));
        tv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
        tv_common_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
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

    @Override
    public void onResume() {
        super.onResume();
        WorkRequestCountBean bean=new WorkRequestCountBean();
        bean.setType("0");
        presenter.getWorkCount(bean);
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void renderData(WorkBean workBean) {

    }

    @Override
    public void getWorkListInfo(List<WorkBean> list) {

    }

    @Override
    public void getWorkList(List<WorkDetailBean> list) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void addWorkSuccess() {

    }

    @Override
    public void getDangerWorkTypeResult(List<DangerTypeBean> list) {

    }

    @Override
    public void getDeviceTypeResult(List<DeviceTypeBean> list) {

    }

    @Override
    public void getDeviceDetailSuccess(List<DeviceDetailBean> list) {

    }

    @Override
    public void getWorkCountSuccess(List<WorkCountBean> list) {
        if (list!=null&& list.size()!=0){
            for (WorkCountBean bean:list){
                if (bean.getType()==1){
                    String dangerTotle=getResources().getString(R.string.danger_work)+" "+bean.getTotal();
                    tv_danger_work.setText(dangerTotle);
                }else if (bean.getType()==0){
                    String commonTotle=getResources().getString(R.string.common_work)+" "+ bean.getTotal();
                    tv_common_work.setText(commonTotle);
                }else if (bean.getType()==2){
                    String mineTotle=getResources().getString(R.string.home_mine)+" "+bean.getTotal();
                    tv_mine.setText(mineTotle);
                }
            }
        }

    }
}
