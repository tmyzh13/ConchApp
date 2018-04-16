package com.isoftston.issuser.conchapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.DisplayUtil;
import com.corelibs.views.zoom.ZoomImageView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.utils.Tools;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by yizh on 2016/10/25.
 */
public class BigPicActivity extends BaseActivity {


    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.ll_dots)
    LinearLayout ll_dots;

    public ArrayList<String> imgs=new ArrayList<>();
    public int currentIndex;
    private ArrayList<View> views=new ArrayList<>();
    private ArrayList<View> dots=new ArrayList<>();
    private Context context=BigPicActivity.this;
    private int count;

    public static Intent getLauncher(Context context,ArrayList<String> imgs,int current){
        Intent intent=new Intent(context,BigPicActivity.class);
        intent.putStringArrayListExtra("img", imgs);
        intent.putExtra("index", current);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_pic;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        imgs=getIntent().getStringArrayListExtra("img");
        currentIndex=getIntent().getIntExtra("index", 0);

        for(int i=0;i<imgs.size();i++){
            View view= LayoutInflater.from(context).inflate(R.layout.item_big_pic,null);
            ZoomImageView imageView=(ZoomImageView)view.findViewById(R.id.iv_pic);
//            ViewGroup.LayoutParams lp=imageView.getLayoutParams();
//            lp.width= Tools.getScreenWidth(context);
//            lp.height=lp.width/4*3;
            if(imgs.get(i).contains("http")){
                Glide.with(context).load(imgs.get(i)).into(imageView);
            }else{
                Glide.with(context).load(new File(imgs.get(i))).into(imageView);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            views.add(view);
        }

        count = imgs.size();

        for (int i = 0; i < count; i++)
        {
            View view = buildChildView();
            ll_dots.addView(view);
            dots.add(view);
        }

        viewPager.setAdapter(new RecommendAdAdapter());
        viewPager.addOnPageChangeListener(new RecommendAdPageChangedListener());
        viewPager.setCurrentItem(currentIndex);
        dots.get(currentIndex).setBackgroundResource(R.drawable.dot_focused);
    }


    /**
     * 生成小圆点
     */
    private View buildChildView()
    {
        View view = new View(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                DisplayUtil.dip2px(context, 8), DisplayUtil.dip2px(context, 8));
        view.setBackgroundResource(R.drawable.dot_normal);
        lp.setMargins(DisplayUtil.dip2px(
                context, 3), 0, DisplayUtil.dip2px(context, 3), 0);
        view.setLayoutParams(lp);

        return view;
    }


    class RecommendAdPageChangedListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageSelected(int arg0)
        {


            int position = arg0 % count;
            for (int i = 0; i < count; i++)
            {
                if (i == position)
                    dots.get(i).setBackgroundResource(R.drawable.dot_focused);
                else
                    dots.get(i).setBackgroundResource(R.drawable.dot_normal);
            }

        }

    }
    /**
     * 用于存储当页面被添加或删除时的position 当页面个数为3的时候, 无限轮播会出现bug,
     * 必须在destroyItem中判断 当Math.abs(add - remove) == 0的时候不删除页面, 不然会出现空页面的情况.
     */
    private int add = 0;

    class RecommendAdAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            return count < 3 ? count : Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            int i = position % count;
            add = i;
            if (views.get(i).getParent() != null)
            {
                ((ViewPager) views.get(i).getParent()).removeView(views.get(i));
            }
            container.addView(views.get(i));
            return views.get(i);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object arg2)
        {
            int i = position % count;
            if (count == 3 && Math.abs(add - i) == 0)
                return;

            container.removeView(views.get(i));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
