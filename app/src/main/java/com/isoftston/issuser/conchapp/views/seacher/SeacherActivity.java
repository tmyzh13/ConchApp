package com.isoftston.issuser.conchapp.views.seacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.IMEUtil;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.isoftston.issuser.conchapp.adapters.DeviceAdapter;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.views.check.CheckDeviceDetailActivity;
import com.isoftston.issuser.conchapp.views.message.ItemDtailActivity;
import com.isoftston.issuser.conchapp.views.work.ScanCodeActivity;
import com.isoftston.issuser.conchapp.views.work.adpter.ListviewAdapter;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypeAdapter;
import com.isoftston.issuser.conchapp.model.bean.TabBean;
import com.isoftston.issuser.conchapp.presenter.SeacherPresenter;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.SeacherView;
import com.isoftston.issuser.conchapp.weight.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/10.
 */

public class SeacherActivity extends BaseActivity<SeacherView,SeacherPresenter> implements SeacherView {

    @Bind(R.id.et_seach)
    EditText et_seach;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.view_statue)
    View viewStatue;
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    @Bind(R.id.ll_histroy)
    LinearLayout ll_histroy;
    @Bind(R.id.lv_message)
    AutoLoadMoreListView lv_message;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    private  Context context=SeacherActivity.this;
    private String historys[]={"安全","事件1","我的发布","项目中的隐患"
                                ,"重大违章事故的发生"};

    //0 隐患 1 作业 2 搜索
    private String type;
    private List<TabBean> list_trouble;
    private List<TabBean> list_work;

    private MessageTypeAdapter messageTypeAdapter;
    private ListviewAdapter listViewAdapter;
    private DeviceAdapter deviceAdapter;

    public static Intent getLauncher(Context context,String type){
        Intent intent =new Intent(context,SeacherActivity.class);
        intent.putExtra("type",type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searcher;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        type =getIntent().getStringExtra("type");

        list_trouble=new ArrayList<>();
        TabBean bean =new TabBean();
        bean.name=getString(R.string.search_all);
        TabBean bean1 =new TabBean();
        bean1.name=getString(R.string.search_hidden_trouble);
        TabBean bean2=new TabBean();
        bean2.name=getString(R.string.search_illegal);
        list_trouble.add(bean);
        list_trouble.add(bean1);
        list_trouble.add(bean2);

        list_work=new ArrayList<>();
        TabBean workBean=new TabBean();
        workBean.name=getString(R.string.search_all);
        TabBean workBean1=new TabBean();
        workBean1.name=getString(R.string.danger_work);
        TabBean workBean2=new TabBean();
        workBean2.name=getString(R.string.common_work);
        list_work.add(workBean);
        list_work.add(workBean1);
        list_work.add(workBean2);

        ViewGroup.LayoutParams lp =viewStatue.getLayoutParams();
        lp.height= Tools.getStatueHeight(context);
        viewStatue.setLayoutParams(lp);

        setBarColor(getResources().getColor(R.color.transparent_black));

        ArrayList list =new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("我这是假数据假数据假数据" + i + "itme");
        }
        listViewAdapter=new ListviewAdapter(context,list);

        List<MessageBean> list1=new ArrayList<>();
        for(int i=0;i<10;i++){
            MessageBean messageBean=new MessageBean();
            list1.add(messageBean);
        }
        messageTypeAdapter=new MessageTypeAdapter(context);
        messageTypeAdapter.addAll(list1);

        List<DeviceBean> list2 =new ArrayList<>();
        for(int i=0;i<10;i++){
            DeviceBean deviceBean=new DeviceBean();
            list2.add(deviceBean);
        }
        deviceAdapter=new DeviceAdapter(context);
        deviceAdapter.addAll(list2);


        if(type.equals("0")){
            for(int i=0;i<list_trouble.size();i++){
                tabLayout.addTab(tabLayout.newTab().setText(list_trouble.get(i).name));
            }
            lv_message.setAdapter(messageTypeAdapter);
        }else if(type.equals("1")){
            for(int i=0;i<list_work.size();i++){
                tabLayout.addTab(tabLayout.newTab().setText(list_work.get(i).name));
            }
            lv_message.setAdapter(listViewAdapter);
        } else if(type.equals("2")){
            tabLayout.setVisibility(View.GONE);
            lv_message.setAdapter(deviceAdapter);
        }

        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type.equals("0")){
                    Intent intent =new Intent(context,ItemDtailActivity.class);
                    startActivity(intent);
                }else if(type.equals("1")){
                    startActivity(ScanCodeActivity.getLauncher(context,""));
                }else if(type.equals("2")){
                    startActivity(CheckDeviceDetailActivity.getLauncher(context,new DeviceBean()));
                }
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.e("yzh","---"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initData();

        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(et_seach.getText().toString().trim())){
                        alterHistroySeach(et_seach.getText().toString().trim());
                        ll_histroy.setVisibility(View.GONE);
                        ptrLayout.setVisibility(View.VISIBLE);
                    }
                    IMEUtil.closeIME(et_seach,context);
                    return true;
                }
                return false;
            }
        });

        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    ll_histroy.setVisibility(View.VISIBLE);
                    ptrLayout.setVisibility(View.GONE);
                }
            }
        });

        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
    }

    private void initData(){
        for (int i = 0; i < historys.length; i++) {
            TextView tv = (TextView) getLayoutInflater().inflate(
                    R.layout.search_label_tv, flowLayout, false);
            tv.setText(historys[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alterHistroySeach(str);
                    ll_histroy.setVisibility(View.GONE);
                    ptrLayout.setVisibility(View.VISIBLE);

                    et_seach.setText(str);
                    et_seach.setSelection(str.length());
                }
            });
            flowLayout.addView(tv);//添加到父View
        }
    }


    @OnClick(R.id.iv_delete)
    public void deletaHistory(){
        for(int i=0;i<historys.length;i++){
            flowLayout.removeAllViews();
        }
    }

    /**
     * 修改界面的历史搜索
     * @param key
     */
    private void alterHistroySeach(String key){

    }

    @Override
    protected SeacherPresenter createPresenter() {
        return new SeacherPresenter();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach(){
        finish();
    }

    @OnClick(R.id.iv_delete)
    public void deletehistory(){

    }

    @Override
    public void searchSuccess(List<CheckPeopleBean> list) {

    }
}
