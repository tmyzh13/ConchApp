package com.isoftston.issuser.conchapp.views.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/10.
 */

public class ItemSafeDtailActivity extends BaseActivity<MessageView,MessagePresenter> implements MessageView {
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_content_title)
    TextView tv_content_title;

    @Bind(R.id.tv_creat_time)
    TextView tv_creat_time;

    @Bind(R.id.tv_content)
    TextView tv_content;

    private String type;
    private String id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_safe_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.setColorRes(R.color.white);
        nav.setNavTitle(getString(R.string.aq_message_title));
        tv_title.setTextColor(getResources().getColor(R.color.text_color));
        nav.showBack(2);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            type = bundle.getString("type");
            id = bundle.getString("id");
        }
        getData();
    }

    private void getData() {
        presenter.getMessageDetailInfo(type,id);
        PushCacheUtils.getInstance().removePushIdMessage(this,id);
    }


    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
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

    }

    private void initView(MessageDetailBean bean) {
        if(bean == null){
            return;
        }
        tv_content_title.setText(bean.getTitle());
        tv_creat_time.setText(DateUtils.getMillionToString(bean.getCreateTime()));
        tv_content.setText(Html.fromHtml(bean.getContent()));
    }

    @Override
    public void getMessageListResult(MessageListInfoBean data) {

    }

    @Override
    public void getWorkError() {
        ToastMgr.show(R.string.loading_failed);

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
