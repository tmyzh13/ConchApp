package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.utils.IMEUtil;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckPeopleAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;
import com.isoftston.issuser.conchapp.presenter.SeacherPresenter;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.SeacherView;
import com.isoftston.issuser.conchapp.views.work.NewWorkActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2018/4/15.
 */

public class ChoiceCheckPeopleActivity extends BaseActivity<SeacherView, SeacherPresenter> implements SeacherView {


    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_seach)
    EditText et_seach;
    @Bind(R.id.lv_people)
    AutoLoadMoreListView lvPeople;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    private Context context = ChoiceCheckPeopleActivity.this;
    private CheckPeopleAdapter adapter;
    private String searchContent = "";
    private List<CheckPeopleBean> list = new ArrayList<>();
    private List<CheckPeopleBean> list_checker;
    private Handler mHandler;

    private int code;
    SearchUserBean bean;
    private boolean isLastRow = false;
    private boolean isSeacher = false;

    public static Intent getLaucnher(Context context, int code) {//根据code设置头部标题文字
        Intent intent = new Intent(context, ChoiceCheckPeopleActivity.class);
        intent.putExtra("code", code);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_check_people;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        code = getIntent().getIntExtra("code", 11);
        if (code == 1) {
            nav.setNavTitle(getString(R.string.person_in_charge));
        } else if (code == 2) {
            nav.setNavTitle(getString(R.string.guardian));
        } else if (code == 3) {
            nav.setNavTitle(getString(R.string.auditor));
        } else if (code == 4) {
            nav.setNavTitle(getString(R.string.approver));
        } else if (code == 5) {
            nav.setNavTitle(getString(R.string.gas_checker));
        } else {
            nav.setNavTitle(getString(R.string.choice_check_people));
        }
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
        adapter = new CheckPeopleAdapter(context);
        bean = new SearchUserBean();
        bean.setCondition(searchContent);
        bean.setLastId("");
        adapter.addAll(list);
        lvPeople.setAdapter(adapter);
        presenter.searchUserAction(bean);
        lvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Constant.CHECK_PEOPLE, adapter.getItem(position).getRealName());
                intent.putExtra(Constant.CHECK_PEOPLE_ID, adapter.getItem(position).getId());
                setResult(NewWorkActivity.CHOSE_CHARGER_CODE, intent);
                finish();
            }
        });
        mHandler = new Handler();
        setOnclick();
    }

    private void setOnclick() {
        lvPeople.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //当滚到最后一行且停止滚动时，执行加载
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
                    loadNextPage(adapter.getCount());
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
        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search_content = et_seach.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_seach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = et_seach.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchText)) {
                        searchContent = searchText;
                        bean.setLastId("");
                        bean.setCondition(searchContent);
                        isSeacher = true;
                        presenter.searchUserAction(bean);
                    }
                }
                IMEUtil.closeIME(et_seach, context);
                return false;
            }
        });
    }

    private void loadNextPage(int count) {
        bean.setCondition(searchContent);
        bean.setLastId(adapter.getItem(count - 1).getId());
        presenter.searchUserAction(bean);
    }

    @Override
    public void searchSuccess(List<CheckPeopleBean> lists) {
        if (isSeacher) {
            adapter.clear();
        } else {
            list.addAll(lists);
        }
        isSeacher = false;
        adapter.addAll(lists);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getEachMessageListResult(EachMessageInfoBean data) {

    }

    @Override
    public void getEachMessageListResult(Object data, String type, String lastId) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void reLogin() {

    }



    @Override
    protected SeacherPresenter createPresenter() {
        return new SeacherPresenter();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach() {
        finish();
    }
}
