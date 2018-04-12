package com.isoftston.issuser.conchapp.views.work;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.DisplayUtil;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.security.SecurityFragment;
import com.isoftston.issuser.conchapp.weight.NavBar;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class WorkFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.iv_add)
    ImageView iv_add;
    @Bind(R.id.iv_seach)
    ImageView iv_seach;
    @Bind((R.id.moretab_viewPager))
    ViewPager viewPager;
    @Bind((R.id.moretab_indicator))
    ScrollIndicatorView scrollIndicatorView;
    private IndicatorViewPager indicatorViewPager;
    private String[] names ;
    private String[] mines ;
    @Bind(R.id.tv_danger_work)
    TextView tv_danger_work;
    @Bind(R.id.tv_common_work)
    TextView tv_common_work;
    @Bind(R.id.tv_mine)
    TextView tv_mine;
    private FragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.home_work));
        nav.hideBack();
        tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_work_normal));
        iv_seach.setVisibility(View.VISIBLE);
        iv_add.setVisibility(View.VISIBLE);
        iv_seach.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        tv_common_work.setOnClickListener(this);
        tv_danger_work.setOnClickListener(this);
        tv_mine.setOnClickListener(this);
        names=new String[]{getString(R.string.all), getString(R.string.shuini), getString(R.string.matou),getString(R.string.kuangshan),getString(R.string.build)};
        mines=new String[]{getString(R.string.my_approve), getString(R.string.my_check),getString(R.string.my_release)};
        adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(0xff262626, Color.GRAY).setSize(selectSize, unSelectSize));
        scrollIndicatorView.setScrollBar(new ColorBar(getActivity(), 0xFF18287A, 4));
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
//        viewPager.setOffscreenPageLimit(1);
        indicatorViewPager.setAdapter(new MyAdapter());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_add:
                Intent intent=new Intent(getActivity(),NewWorkActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_seach:
                break;
            case R.id.tv_danger_work:
                iv_add.setVisibility(View.VISIBLE);
                ToastUtils.showtoast(getActivity(),"onclick1");
                tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_work_normal));
                tv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
                tv_common_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
                names=new String[]{getString(R.string.all), getString(R.string.shuini), getString(R.string.matou),getString(R.string.kuangshan),getString(R.string.build)};
                adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
                indicatorViewPager.setAdapter(new MyAdapter());
                viewPager.setAdapter(adapter);
                break;
            case R.id.tv_common_work:
                iv_add.setVisibility(View.VISIBLE);
                ToastUtils.showtoast(getActivity(),"onclick2");
                tv_common_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_work_gradient_bg));
                tv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
                tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
                names=new String[]{getString(R.string.all), getString(R.string.shuini), getString(R.string.matou),getString(R.string.kuangshan),getString(R.string.build)};
                adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
                indicatorViewPager.setAdapter(new MyAdapter());
                viewPager.setAdapter(adapter);
                break;
            case R.id.tv_mine:
                iv_add.setVisibility(View.GONE);
                tv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_security_mine));
                tv_danger_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
                tv_common_work.setBackgroundDrawable(getResources().getDrawable(R.drawable.work_mine));
                names=mines;
                adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
                indicatorViewPager.setAdapter(new MyAdapter());
                viewPager.setAdapter(adapter);
                ToastUtils.showtoast(getActivity(),"onclick3");
                break;
        }
    }

    private class TabPageIndicatorAdapter extends FragmentPagerAdapter {

        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putString("arg", names[position]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return names[position%names.length];
        }

        @Override
        public int getCount() {
            return names.length;
        }
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position]);

            int witdh = getTextWidth(textView);
            int padding = DisplayUtil.dipToPix(getActivity(), 8);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth((int) (witdh * 1.3f) + padding);

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new TextView(container.getContext());
            }
            TextView textView = (TextView) convertView;
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }

    }
}
