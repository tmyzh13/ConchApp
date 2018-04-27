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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.IMEUtil;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.isoftston.issuser.conchapp.adapters.DeviceAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.LoginActivity;
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
import java.util.Map;

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
    @Bind(R.id.iv_delete)
    ImageView deleteHistory;

    private Context context = SeacherActivity.this;
    private String historys[];

    //0 隐患 1 作业 2 搜索
    private String type;
    private List<TabBean> list_trouble;
    private List<TabBean> list_work;

    private MessageTypeAdapter messageTypeAdapter;
    private ListviewAdapter listViewAdapter;
    private DeviceAdapter deviceAdapter;
    private String historyString;
    private int selectTab;
    public List<MessageBean> listAllMessageTab = new ArrayList<>();
    public List<MessageBean> listYhMessageTab = new ArrayList<>();
    public List<MessageBean> listWzMessageTab = new ArrayList<>();
    public List<MessageBean> listAqMessageTab = new ArrayList<>();

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
        if (("0").equals(type)){
            TabBean bean3 = new TabBean();
            bean3.name = getString(R.string.search_security);
            list_trouble.add(bean3);
        }


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

        ViewGroup.LayoutParams lp = viewStatue.getLayoutParams();
        lp.height = Tools.getStatueHeight(context);
        viewStatue.setLayoutParams(lp);

        setBarColor(getResources().getColor(R.color.transparent_black));

        ArrayList list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("我这是假数据假数据假数据" + i + "itme");
        }
        listViewAdapter = new ListviewAdapter(context, list);

        messageTypeAdapter = new MessageTypeAdapter(context);

        List<DeviceBean> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DeviceBean deviceBean = new DeviceBean();
            list2.add(deviceBean);
        }
        deviceAdapter = new DeviceAdapter(context);
        deviceAdapter.addAll(list2);


        if (type.equals("0")) {
            historyString = SharePrefsUtils.getValue(context, Constant.MESSAGE_SEARCH, "");
            for (int i = 0; i < list_trouble.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(list_trouble.get(i).name));
            }
            lv_message.setAdapter(messageTypeAdapter);
        } else if (type.equals("1")) {
            historyString = SharePrefsUtils.getValue(context, Constant.WORK_SEARCH, "");
            for (int i = 0; i < list_work.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(list_work.get(i).name));
            }
            lv_message.setAdapter(listViewAdapter);
        } else if (type.equals("2")) {
            historyString = SharePrefsUtils.getValue(context, Constant.CHECK_SEARCH, "");
            tabLayout.setVisibility(View.GONE);
            lv_message.setAdapter(deviceAdapter);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ll_histroy.setVisibility(View.VISIBLE);
                ptrLayout.setVisibility(View.GONE);
                selectTab = tab.getPosition();
                if (type.equals("0")) {
                    messageTypeAdapter.clear();
                    messageTypeAdapter.notifyDataSetChanged();
                } else if (type.equals("1")) {

                } else if (type.equals("2")) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals("0")) {
                    Intent intent = new Intent(SeacherActivity.this, ItemDtailActivity.class);
                    Bundle bundle=new Bundle();
                    if (selectTab == 0){
                        bundle.putString("type",listAllMessageTab.get(position).getType());
                        bundle.putString("id",listAllMessageTab.get(position).getId());
                    }else if (selectTab == 1){
                        bundle.putString("type",listYhMessageTab.get(position).getType());
                        bundle.putString("id",listYhMessageTab.get(position).getId());
                    }else if (selectTab == 2){
                        bundle.putString("type",listWzMessageTab.get(position).getType());
                        bundle.putString("id",listWzMessageTab.get(position).getId());
                    }else {
                        bundle.putString("type",listAqMessageTab.get(position).getType());
                        bundle.putString("id",listAqMessageTab.get(position).getId());
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("1")) {
                    startActivity(ScanCodeActivity.getLauncher(context, ""));
                } else if (type.equals("2")) {
                    startActivity(CheckDeviceDetailActivity.getLauncher(context, new DeviceBean()));
                }
            }
        });

        if (!"".equals(historyString)) {
            historys = historyString.split(",");
            deleteHistory.setVisibility(View.VISIBLE);
            initData();
        }
        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = et_seach.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchText)) {
                        alterHistroySeach(searchText);
                        ll_histroy.setVisibility(View.GONE);
                        ptrLayout.setVisibility(View.VISIBLE);
                        if (historys != null && historys.length == 10) {
                            historyString = historyString.substring(historyString.indexOf(",") + 1, historyString.length());
                        }
                        if ("".equals(historyString)) {
                            if (type.equals("0")) {
                                SharePrefsUtils.putValue(context, Constant.MESSAGE_SEARCH, searchText + ",");
                            } else if (type.equals("1")) {
                                SharePrefsUtils.putValue(context, Constant.WORK_SEARCH, searchText + ",");
                            } else if (type.equals("2")) {
                                SharePrefsUtils.putValue(context, Constant.CHECK_SEARCH, searchText + ",");
                            }
                        }
                        if (type.equals("0")) {
                            boolean isHaveKey = false;
                            for (String history : historys){
                                if (history.equals(searchText)){
                                    isHaveKey = true;
                                }
                            }
                            if (!isHaveKey){
                                SharePrefsUtils.putValue(context, Constant.MESSAGE_SEARCH, historyString + searchText + ",");
                            }
                        } else if (type.equals("1")) {
                            SharePrefsUtils.putValue(context, Constant.WORK_SEARCH, historyString + searchText + ",");
                        } else if (type.equals("2")) {
                            SharePrefsUtils.putValue(context, Constant.CHECK_SEARCH, historyString + searchText + ",");
                        }
                    }
                    IMEUtil.closeIME(et_seach, context);
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
                if (TextUtils.isEmpty(s.toString())) {
                    ll_histroy.setVisibility(View.VISIBLE);
                    ptrLayout.setVisibility(View.GONE);
                }
            }
        });

        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
    }

    private void initData() {
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
    public void deletaHistory() {
        if (type.equals("0")) {
            SharePrefsUtils.putValue(context, Constant.MESSAGE_SEARCH, "");
        } else if (type.equals("1")) {
            SharePrefsUtils.putValue(context, Constant.WORK_SEARCH, "");
        } else if (type.equals("2")) {
            SharePrefsUtils.putValue(context, Constant.CHECK_SEARCH, "");
        }
        for (int i = 0; i < historys.length; i++) {
            flowLayout.removeAllViews();
        }
    }

    /**
     * 修改界面的历史搜索
     *
     * @param key
     */
    private void alterHistroySeach(String key) {
        if (type.equals("0")){
            String searchType;
            if (selectTab == 0){
                searchType = "all";
            }else if (selectTab == 1){
                searchType = "yh";
            }else if (selectTab == 2){
                searchType = "wz";
            }else {
                searchType = "aq";
            }
            showLoading();
            presenter.searchMessage(searchType,key);
        }else {

        }

    }

    @Override
    protected SeacherPresenter createPresenter() {
        return new SeacherPresenter();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach() {
        finish();
    }

    @Override
    public void searchSuccess(List<CheckPeopleBean> list) {

    }

    @Override
    public void getEachMessageListResult(EachMessageInfoBean data) {
        hideLoading();
        if (selectTab == 0){
            listAllMessageTab.clear();
            listAllMessageTab = data.list;
        }else if (selectTab == 1){
            listYhMessageTab.clear();
            listYhMessageTab = data.list;
        }else if (selectTab == 2) {
            listWzMessageTab.clear();
            listWzMessageTab = data.list;
        }else {
            listAqMessageTab.clear();
            listAqMessageTab = data.list;
        }
        messageTypeAdapter.addAll(data.list);
        messageTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkError() {
        getLoadingDialog().dismiss();
        hideLoading();
        startActivity(LoginActivity.getLauncher(this));
    }

    @Override
    public void reLogin() {
        ToastUtils.showtoast(context,getString(R.string.re_login));
        PreferencesHelper.saveData(Constant.LOGIN_STATUE,"");
        startActivity(LoginActivity.getLauncher(this));
    }
}
