package com.isoftston.issuser.conchapp.views.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.corelibs.base.BaseActivity;
import com.corelibs.views.roundedimageview.RoundedTransformationBuilder;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.mGridViewAdapter;
import com.isoftston.issuser.conchapp.constants.Urls;
import com.isoftston.issuser.conchapp.model.bean.AirResponseBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WeatherResponseBean;
import com.isoftston.issuser.conchapp.presenter.MessagePresenter;
import com.isoftston.issuser.conchapp.utils.DateUtils;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.MessageView;
import com.isoftston.issuser.conchapp.views.message.adpter.VpAdapter;
import com.isoftston.issuser.conchapp.views.message.utils.PushCacheUtils;
import com.isoftston.issuser.conchapp.weight.MyGridView;
import com.isoftston.issuser.conchapp.weight.NavBar;
import com.isoftston.issuser.conchapp.weight.RoundByXfermode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/10.
 */

public class ItemDangerDtailActivity extends BaseActivity<MessageView,MessagePresenter> implements MessageView {
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.mGridView)
    MyGridView mGridView;
    @Bind(R.id.tv_user)
    TextView tv_yh_finder;
    @Bind(R.id.tv_company)
    TextView tv_company;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.sjdwmc_tv)
    TextView sjdwmc_tv;
    @Bind(R.id.yhdd_tv)
    TextView yhdd_tv;
    @Bind(R.id.yhbw_tv)
    TextView yhbw_tv;
    @Bind(R.id.yhjb_tv)
    TextView yhjb_tv;
    @Bind(R.id.yhlx_tv)
    TextView yhlx_tv;
    @Bind(R.id.zgqx_tv)
    TextView zgqx_tv;

    @Bind(R.id.yhzt_tv)
    TextView yhzt_tv;
    @Bind(R.id.msyh_tv)
    TextView msyh_tv;
    @Bind(R.id.yhly_tv)
    TextView yhly_tv;
    @Bind(R.id.ll_fix_pic)
    LinearLayout ll_fix_pic;

    @Bind(R.id.vw_fix_pic)
    View vw_fix_pic;

    @Bind(R.id.zgzt_fix_img)
    RoundByXfermode zgztFixImage;
    @Bind(R.id.zgzt_unfix_img)
    RoundByXfermode zgztUnFixImage;



    private List<View> imageList;
    private ArrayList<View> dotsList;
//    private int[] images = {R.drawable.aaa,R.drawable.bbb,R.drawable.ccc,R.drawable.ddd};
    private List<String> urls = new ArrayList<>();
    private List<String> zgurls = new ArrayList<>();

    private mGridViewAdapter gridViewAdapter;
    private ArrayList<Map<String, Object>> data_list;
    public Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            int currentItem = vp.getCurrentItem();
            //切换至下一个页面
            vp.setCurrentItem(++currentItem);
            //再次调用
//            handler.sendEmptyMessageDelayed(1, 2000);
        };
    };
    private String type;
    private String id;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_danger_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.setColorRes(R.color.white);
        nav.setNavTitle(getString(R.string.yh_project_check));
        tv_title.setTextColor(getResources().getColor(R.color.text_color));
        nav.showBack(2);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            type = bundle.getString("type");
            id = bundle.getString("id");
        }
        getData();
        //页面改变监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //遍历小圆点集合
                for(int i=0;i<dotsList.size();i++){
                    if(position%dotsList.size() == i){//设置当前小圆点
                        dotsList.get(i).setBackgroundResource(R.drawable.dots_focus);
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

    private void getData() {
        presenter.getMessageDetailInfo(type,id);
        PushCacheUtils.getInstance().removePushIdMessage(this,id);
    }


    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    private void initDots() {
        //实例化一个集合存放小圆点
        dotsList = new ArrayList<View>();
        for(int i=0;i<urls.size();i++){
            //把第一个小圆点设置为选中状态
            View v = new View(this);
            if(i == 0){
                v.setBackgroundResource(R.drawable.dots_focus);
            }else{
                v.setBackgroundResource(R.drawable.dots_normal);
            }
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0){
                params.leftMargin = 30;
                params.bottomMargin = 20;
            }
            v.setLayoutParams(params);
            ll.addView(v);
            dotsList.add(v);
        }
    }
    private void initImages() {
        //实例化一个集合，用于存放图片
        imageList = new ArrayList<View>();
        for (int i = 0; i < urls.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.viewpager_item, null);
//            TextView title = (TextView) view.findViewById(R.id.view_title);
//            title.setText("头像");
            ImageView iv = view.findViewById(R.id.view_image);
            Glide.with(this).load(urls.get(i))
                    .centerCrop()
                    .into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ItemDangerDtailActivity.this,ImageDetilActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("imagepath", (ArrayList<String>) urls);
                    bundle.putInt("index",j);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
//            iv.setImageResource(images[i]);
            imageList.add(view);
        }
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void getMessageDetailResult(MessageDetailBean bean) {
        initView(bean);
        initImages();
        //初始化小圆点
        initDots();
        //适配器
        VpAdapter adapter = new VpAdapter(imageList,handler);
        vp.setPageMargin(10);
        vp.setAdapter(adapter);
        //初始化vp的位置
        vp.setCurrentItem(0);

        if(zgurls!=null&&zgurls.size()>0){
            gridViewAdapter = new mGridViewAdapter(this, zgurls);
            mGridView.setAdapter(gridViewAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Map<String, Object> map = data_list.get(i);
                    Intent intent=new Intent(ItemDangerDtailActivity.this,ImageDetilActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("imagepath", (ArrayList<String>) zgurls);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }else{
            ll_fix_pic.setVisibility(View.GONE);
            vw_fix_pic.setVisibility(View.GONE);
        }
    }

    private void initView(MessageDetailBean bean) {
        if(bean == null){
            return;
        }
        tv_yh_finder.setText(bean.getFxrmc());
        String picPath[];
        if(bean.getDangerPhoto()!=null){
            picPath = bean.getDangerPhoto().split(",");
            for (String path : picPath){
                if (!path.startsWith("uploadDir")){
                    urls.add(Urls.ROOT+path);
                }else {
                    urls.add(Urls.IMAGE_ROOT+path);
                }
            }
        }

        String zgpicPath[];
        if(bean.getRepairPhoto()!=null){

            zgpicPath = bean.getRepairPhoto().split(",");
            for (String path : zgpicPath){
                if (!path.startsWith("uploadDir")){
                    zgurls.add(Urls.ROOT+path);
                }else {
                    zgurls.add(Urls.IMAGE_ROOT+path);
                }
            }
        }

        tv_company.setText(bean.getFxrCompany());
        tv_time.setText(DateUtils.getMillionToString(bean.getFxsj()));
        sjdwmc_tv.setText(bean.getFindByUnit());
        yhdd_tv.setText(bean.getDangerSite());
        yhbw_tv.setText(bean.getDangerPart());

        if(bean.getZgqx()!=null)
        zgqx_tv.setText(DateUtils.getMillionToDate(bean.getZgqx()));
        if(bean.getYhzgzt() == null || "0".equals(bean.getYhzgzt())){
            yhzt_tv.setText(R.string.un_fix);
            zgztUnFixImage.setVisibility(View.VISIBLE);
            zgztFixImage.setVisibility(View.GONE);
        }else if ("1".equals(bean.getYhzgzt())){
            yhzt_tv.setText(R.string.on_fix);
            zgztUnFixImage.setVisibility(View.VISIBLE);
            zgztFixImage.setVisibility(View.GONE);
        }else {
            yhzt_tv.setText(R.string.fixed);
            zgztUnFixImage.setVisibility(View.GONE);
            zgztFixImage.setVisibility(View.VISIBLE);
        }




        //Map<String,String> fromLYMap= MainActivity.fromLYMap;
        //Map<String,String> fromLXMap=MainActivity.fromLXMap;
        //if(fromLYMap!=null) {
            String yhly = bean.getYhly();
            if(yhly==null){
                ll_fix_pic.setVisibility(View.GONE);
            }else{
                yhly_tv.setText(SharePrefsUtils.getValue(this,yhly,""));
            }

        //}
        msyh_tv.setText(bean.getYhms());

        //if(fromLXMap!=null) {
            String dangerType = bean.getDangerType();
            yhlx_tv.setText(SharePrefsUtils.getValue(this,dangerType,""));
        //}

        yhjb_tv.setText(SharePrefsUtils.getValue(this,bean.getDangerLevel(),""));
    }

    @Override
    public void getMessageListResult(MessageListInfoBean data) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void reLogin() {

    }

    @Override
    public void refreshWeather(WeatherResponseBean bean) {

    }

    @Override
    public void getEachMessageListResult(EachMessageInfoBean data) {

    }

    @Override
    public void refreshAir(AirResponseBean bean) {

    }
}
