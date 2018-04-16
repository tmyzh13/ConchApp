package com.isoftston.issuser.conchapp.views.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.views.message.adpter.VpAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/16.
 */

public class ImageDetilActivity extends BaseActivity{
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.ll_point)
    LinearLayout ll_point;
    private ArrayList<View> dotsList;
    private List<String> urls = new ArrayList<>();
    private List<View> imageList;
    public Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            int currentItem = viewPager.getCurrentItem();
            //切换至下一个页面
            viewPager.setCurrentItem(++currentItem);
            //再次调用
//            handler.sendEmptyMessageDelayed(1, 2000);
        };
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }
    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, ImageDetilActivity.class);
        return intent;
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        for (int i=0;i<4;i++){
            urls.add("http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg");
        }
        initImages();
        initDots();
        VpAdapter adapter = new VpAdapter(imageList,handler);
        viewPager.setAdapter(adapter);
        //页面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //遍历小圆点集合
                for(int i=0;i<dotsList.size();i++){
                    if(position%dotsList.size() == i){//设置当前小圆点
                        dotsList.get(i).setBackgroundResource(R.drawable.dots_white);
                    }else{//设置其他小圆点
                        dotsList.get(i).setBackgroundResource(R.drawable.dots_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initImages() {
        //实例化一个集合，用于存放图片
        imageList = new ArrayList<View>();
        for (int i = 0; i < urls.size(); i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.viewpager_item, null);
            ImageView iv = view.findViewById(R.id.view_image);
            Glide.with(this).load(urls.get(i))
//                    .centerCrop()
//                    .override(320,160)
//                    .transform(new CenterCrop(this), new RoundedTransformationBuilder().cornerRadius(20).build(this))
                    .into(iv);
            imageList.add(view);
        }
    }

    private void initDots() {
        //实例化一个集合存放小圆点
        dotsList = new ArrayList<View>();
        for(int i=0;i<imageList.size();i++){
            //把第一个小圆点设置为选中状态
            View v = new View(this);
            if(i == 0){
                v.setBackgroundResource(R.drawable.dots_white);
            }else{
                v.setBackgroundResource(R.drawable.dots_normal);
            }
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0){
                params.leftMargin = 20;
            }
            v.setLayoutParams(params);
            ll_point.addView(v);
            dotsList.add(v);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
