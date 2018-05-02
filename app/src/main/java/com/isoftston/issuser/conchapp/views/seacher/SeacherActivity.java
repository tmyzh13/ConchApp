package com.isoftston.issuser.conchapp.views.seacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.IMEUtil;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.DeviceAdapter;
import com.isoftston.issuser.conchapp.adapters.MessageTypeAdapter;
import com.isoftston.issuser.conchapp.adapters.SecurityAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SecurityTroubleBean;
import com.isoftston.issuser.conchapp.model.bean.TabBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListsBean;
import com.isoftston.issuser.conchapp.presenter.SeacherPresenter;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.LoginActivity;
import com.isoftston.issuser.conchapp.views.check.CheckDeviceDetailActivity;
import com.isoftston.issuser.conchapp.views.interfaces.SeacherView;
import com.isoftston.issuser.conchapp.views.message.ItemDtailActivity;
import com.isoftston.issuser.conchapp.views.work.ScanCodeActivity;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkMessageItemAdapter;
import com.isoftston.issuser.conchapp.weight.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/10.
 */

public class SeacherActivity extends BaseActivity<SeacherView, SeacherPresenter> implements SeacherView {

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

    private String type;
    private List<TabBean> list_trouble;

    private MessageTypeAdapter messageTypeAdapter;
    private SecurityAdapter securityAdapter;
    private WorkMessageItemAdapter workAdapter;
    private DeviceAdapter deviceAdapter;
    private String historyString;
    private int selectTab;
    private boolean isLastRow = false;

    private String searchKey;

    public static Intent getLauncher(Context context, String type) {
        Intent intent = new Intent(context, SeacherActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searcher;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        setTypeTabView();
        ViewGroup.LayoutParams lp = viewStatue.getLayoutParams();
        lp.height = Tools.getStatueHeight(context);
        viewStatue.setLayoutParams(lp);
        setBarColor(getResources().getColor(R.color.transparent_black));
        setTypeTabLayout();
        initData();
        setListOnclik();
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
    }

    private void setListOnclik() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ll_histroy.setVisibility(View.VISIBLE);
                ptrLayout.setVisibility(View.GONE);
                flowLayout.setVisibility(View.VISIBLE);
                selectTab = tab.getPosition();
                initData();
                if (type.equals("0")) {
                    messageTypeAdapter.clear();
                    messageTypeAdapter.notifyDataSetChanged();
                } else if (type.equals("1")) {
                    securityAdapter.clear();
                    securityAdapter.notifyDataSetChanged();
                } else if (type.equals("2")) {
                    workAdapter.clear();
                    workAdapter.notifyDataSetChanged();
                } else if (type.equals("3")) {
                    deviceAdapter.clear();
                    deviceAdapter.notifyDataSetChanged();
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
                    Bundle bundle = new Bundle();
                    bundle.putString("type", messageTypeAdapter.getItem(position).getType());
                    bundle.putString("id", messageTypeAdapter.getItem(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("1")) {
                    Intent intent = new Intent(SeacherActivity.this, ItemDtailActivity.class);
                    Bundle bundle = new Bundle();
                    String troubleType = "";
                    if ("ZYZYWZBD".equals(securityAdapter.getItem(position).getYhlx()) || "QT".equals(securityAdapter.getItem(position).getYhlx())
                            || "YHSW".equals(securityAdapter.getItem(position).getYhlx()) || "WCZWZZY".equals(securityAdapter.getItem(position).getYhlx())
                            || "ZHSWWZZH".equals(securityAdapter.getItem(position).getYhlx()) || "GRFHZBBQ".equals(securityAdapter.getItem(position).getYhlx())) {
                        troubleType = "wz";
                    } else {
                        troubleType = "yh";
                    }
                    bundle.putString("type", troubleType);
                    bundle.putString("id", securityAdapter.getItem(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("2")) {
                    String jobId = String.valueOf(workAdapter.getItem(position).getId());
                    startActivity(ScanCodeActivity.getLauncher(SeacherActivity.this, jobId));
                } else if (type.equals("3")) {
                    startActivity(CheckDeviceDetailActivity.getLauncher(SeacherActivity.this, deviceAdapter.getItem(position)));
                }
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
        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = et_seach.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchText)) {
                        alterHistroySeach(searchText);
                        ll_histroy.setVisibility(View.GONE);
                        ptrLayout.setVisibility(View.VISIBLE);
                        searchKey = searchText;
                        wirteLocalHistory(searchText);
                    }
                    IMEUtil.closeIME(et_seach, context);
                    return true;
                }
                return false;
            }
        });
        lv_message.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //当滚到最后一行且停止滚动时，执行加载
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
                    loadNextPage();
                    isLastRow = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //判断是否滚到最后一行
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    isLastRow = true;
                }
            }
        });
    }

    private void loadNextPage() {
        ptrLayout.setLoading();
        String key = getSeachType();
        switch (type) {
            case "0":
                if (messageTypeAdapter.getCount() > 0) {
                    presenter.searchMessage(key,searchKey , messageTypeAdapter.getItem(messageTypeAdapter.getCount() - 1).getmId());
                } else {
                    presenter.searchMessage(key, searchKey, "");
                }
                break;
            case "1":
                if (securityAdapter.getCount() > 0) {
                    presenter.searchSafeMessage(key, searchKey, securityAdapter.getItem(securityAdapter.getCount() - 1).getId());
                } else {
                    presenter.searchSafeMessage(key, searchKey, "");
                }
                break;
            case "2":
                if (workAdapter.getCount() > 0) {
                    presenter.searchWorkMessage(Integer.parseInt(key), searchKey, workAdapter.getItem(workAdapter.getCount() - 1).getId()+"");
                } else {
                    presenter.searchWorkMessage(Integer.parseInt(key), searchKey, "");
                }
                break;
            case "3":
                if (deviceAdapter.getCount() > 0) {
                    presenter.searchDeviceMessage(searchKey, deviceAdapter.getItem(deviceAdapter.getCount() - 1).getId()+"");
                } else {
                    presenter.searchDeviceMessage(searchKey, "");
                }
                break;
        }
    }


    private void wirteLocalHistory(String searchText) {
        if (type.equals("0")) {
            if (!isHaveKey(searchText)) {
                isMaxHistory();
                historyString = historyString + searchText + ",";
                SharePrefsUtils.putValue(context, Constant.MESSAGE_SEARCH, historyString);
            }
        } else if (type.equals("1")) {
            if (!isHaveKey(searchText)) {
                isMaxHistory();
                historyString = historyString + searchText + ",";
                SharePrefsUtils.putValue(context, Constant.SAFE_SEARCH, historyString);
            }
        } else if (type.equals("2")) {
            if (!isHaveKey(searchText)) {
                isMaxHistory();
                historyString = historyString + searchText + ",";
                SharePrefsUtils.putValue(context, Constant.WORK_SEARCH, historyString);
            }
        } else if (type.equals("3")) {
            if (!isHaveKey(searchText)) {
                isMaxHistory();
                historyString = historyString + searchText + ",";
                SharePrefsUtils.putValue(context, Constant.CHECK_SEARCH, historyString);
            }
        }
    }

    private void isMaxHistory() {
        if (historys != null && historys.length == 10) {
            historyString = historyString.substring(historyString.indexOf(",") + 1, historyString.length());
        }
    }

    public boolean isHaveKey(String searchText) {
        boolean isHaveKey = false;
        if (historys != null && historys.length > 0) {
            for (String history : historys) {
                if (history.equals(searchText)) {
                    isHaveKey = true;
                }
            }
        }
        return isHaveKey;
    }

    private void setTypeTabLayout() {
        if (list_trouble != null && list_trouble.size() > 0) {
            for (int i = 0; i < list_trouble.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(list_trouble.get(i).name));
            }
        }
        if (type.equals("0")) {
            messageTypeAdapter = new MessageTypeAdapter(context);
            historyString = SharePrefsUtils.getValue(context, Constant.MESSAGE_SEARCH, "");
            lv_message.setAdapter(messageTypeAdapter);
        } else if (type.equals("1")) {
            securityAdapter = new SecurityAdapter(context);
            historyString = SharePrefsUtils.getValue(context, Constant.SAFE_SEARCH, "");
            tabLayout.setVisibility(View.VISIBLE);
            lv_message.setAdapter(securityAdapter);
        } else if (type.equals("2")) {
            workAdapter = new WorkMessageItemAdapter(context);
            historyString = SharePrefsUtils.getValue(context, Constant.WORK_SEARCH, "");
            tabLayout.setVisibility(View.VISIBLE);
            lv_message.setAdapter(workAdapter);
        } else {
            deviceAdapter = new DeviceAdapter(context);
            historyString = SharePrefsUtils.getValue(context, Constant.CHECK_SEARCH, "");
            tabLayout.setVisibility(View.GONE);
            lv_message.setAdapter(deviceAdapter);
        }
    }

    private void setTypeTabView() {
        list_trouble = new ArrayList<>();
        TabBean bean = new TabBean();
        TabBean bean1 = new TabBean();
        TabBean bean2 = new TabBean();
        TabBean bean3 = new TabBean();
        if ("0".equals(type) || "1".equals(type)) {
            bean.name = getString(R.string.search_all);
            bean1.name = getString(R.string.search_hidden_trouble);
            bean2.name = getString(R.string.search_illegal);
        } else if ("2".equals(type)) {
            bean.name = getString(R.string.danger_work);
            bean1.name = getString(R.string.common_work);
            bean2.name = getString(R.string.home_mine);
        }
        list_trouble.add(bean);
        list_trouble.add(bean1);
        list_trouble.add(bean2);
        if (("0").equals(type)) {
            bean3.name = getString(R.string.search_security);
            list_trouble.add(bean3);
        } else if (("1").equals(type)) {
            bean3.name = getString(R.string.home_mine);
            list_trouble.add(bean3);
        }
    }

    private void initData() {
        if (!TextUtils.isEmpty(historyString)) {
            historys = historyString.split(",");
            deleteHistory.setVisibility(View.VISIBLE);
        }
        if (historys != null && historys.length > 0) {
            flowLayout.removeAllViews();
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
    }


    @OnClick(R.id.iv_delete)
    public void deletaHistory() {
        if (type.equals("0")) {
            SharePrefsUtils.putValue(context, Constant.MESSAGE_SEARCH, "");
        } else if (type.equals("1")) {
            SharePrefsUtils.putValue(context, Constant.SAFE_SEARCH, "");
        } else if (type.equals("2")) {
            SharePrefsUtils.putValue(context, Constant.WORK_SEARCH, "");
        } else if (type.equals("3")) {
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
        String searchType = getSeachType();
        showLoading();
        if (type.equals("0")) {
            presenter.searchMessage(searchType, key, "");
        } else if (type.equals("1")) {
            presenter.searchSafeMessage(searchType, key, "");
        } else if (type.equals("2")) {
            presenter.searchWorkMessage(Integer.parseInt(searchType), key,"");
        } else if (type.equals("3")) {
            presenter.searchDeviceMessage(key,"");
        }

    }

    public String getSeachType() {
        String searchType = "";
        if (type.equals("0")) {
            if (selectTab == 0) {
                searchType = "all";
            } else if (selectTab == 1) {
                searchType = "yh";
            } else if (selectTab == 2) {
                searchType = "wz";
            } else {
                searchType = "aq";
            }
        } else if (type.equals("1")) {
            if (selectTab == 0) {
                searchType = "all";
            } else if (selectTab == 1) {
                searchType = "yh";
            } else if (selectTab == 2) {
                searchType = "wz";
            } else {
                searchType = "wd";
            }
        } else if (type.equals("2")) {
            if (selectTab == 0) {
                searchType = "1";
            } else if (selectTab == 1) {
                searchType = "0";
            } else if (selectTab == 2) {
                searchType = "2";
            }
        }
        return searchType;
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

    }

    @Override
    public void getEachMessageListResult(Object data, String type, String lastId) {
        hideLoading();
        ptrLayout.complete();
        if ("0".equals(type)) {
            updateMessage(((EachMessageInfoBean) data).list, lastId);
        } else if ("1".equals(type)) {
            updateSafe(((SafeListBean) data).list, lastId);
        } else if ("2".equals(type)) {
            updateWork(((WorkListsBean) data).list, lastId);
        } else if ("3".equals(type)) {
            updateDevice(((DeviceListBean) data).list, lastId);
        }
    }

    private void updateDevice(List<DeviceBean> list, String lastId) {
        if (TextUtils.isEmpty(lastId)) {
            deviceAdapter.clear();
        }
        deviceAdapter.addAll(list);
        deviceAdapter.notifyDataSetChanged();
    }

    private void updateWork(List<WorkDetailBean> list, String lastId) {
        if (TextUtils.isEmpty(lastId)) {
            workAdapter.clear();
        }
        workAdapter.addAll(list);
        workAdapter.notifyDataSetChanged();
    }

    private void updateSafe(List<SecurityTroubleBean> list, String lastId) {
        if (TextUtils.isEmpty(lastId)) {
            securityAdapter.clear();
        }
        securityAdapter.addAll(list);
        securityAdapter.notifyDataSetChanged();
    }

    private void updateMessage(List<MessageBean> list, String lastId) {
        if (TextUtils.isEmpty(lastId)) {
            messageTypeAdapter.clear();
        }
        messageTypeAdapter.addAll(list);
        messageTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkError() {
        getLoadingDialog().dismiss();
        hideLoading();
        ptrLayout.complete();
    }

    @Override
    public void reLogin() {
        ToastUtils.showtoast(context, getString(R.string.re_login));
        PreferencesHelper.saveData(Constant.LOGIN_STATUE, "");
        startActivity(LoginActivity.getLauncher(this));
    }
}
