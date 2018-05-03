package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.corelibs.base.BaseActivity;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckPeopleAdapter;
import com.isoftston.issuser.conchapp.adapters.ChoicePeopleAdapter;
import com.isoftston.issuser.conchapp.adapters.MessageTypeAdapter;
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

public class ChoiceCheckPeopleActivity extends BaseActivity<SeacherView,SeacherPresenter>implements SeacherView{


    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_seach)
    EditText et_seach;
    @Bind(R.id.lv_people)
    AutoLoadMoreListView lv_people;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    private Context context=ChoiceCheckPeopleActivity.this;
    private String searchContent="";
    private List<CheckPeopleBean> list=new ArrayList<>();
    private List<String> search_list=new ArrayList<>();
    private Handler mHandler;

    private int code;
    private ChoicePeopleAdapter mAdapter;
    private boolean isUpRefresh;
    private boolean isLastRow;
    private boolean isDownRefresh;
    private boolean isSearch;

    public static Intent getLaucnher(Context context,int code){//根据code设置头部标题文字
        Intent intent =new Intent(context,ChoiceCheckPeopleActivity.class);
        intent.putExtra("code",code);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_check_people;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        code = getIntent().getIntExtra("code",11);
        if (code==1){
            nav.setNavTitle(getString(R.string.person_in_charge));
        }else if (code == 2){
            nav.setNavTitle(getString(R.string.guardian));
        }else if (code == 3){
            nav.setNavTitle(getString(R.string.auditor));
        }else if (code == 4){
            nav.setNavTitle(getString(R.string.approver));
        }else if (code == 5){
            nav.setNavTitle(getString(R.string.gas_checker));
        }else {
            nav.setNavTitle(getString(R.string.choice_check_people));
        }
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);

        mAdapter = new ChoicePeopleAdapter(context);
        mAdapter.addAll(list);
        lv_people.setAdapter(mAdapter);
        ptrLayout.setLoading();
        ptrLayout.setRefreshLoadCallback(new PtrAutoLoadMoreLayout.RefreshLoadCallback() {
            @Override
            public void onLoading(PtrFrameLayout frame) {


            }

            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                isUpRefresh = true;
                SearchUserBean bean = new SearchUserBean();
                bean.setCondition(searchContent);
                bean.setLastId("");
                presenter.searchUserAction(bean);

            }
        });
        SearchUserBean bean = new SearchUserBean();
        bean.setCondition(searchContent);
        bean.setLastId("");
        presenter.searchUserAction(bean);
        lv_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent();
                intent.putExtra(Constant.CHECK_PEOPLE,list.get(position).getRealName());
                intent.putExtra(Constant.CHECK_PEOPLE_ID,list.get(position).getId());
                setResult(NewWorkActivity.CHOSE_CHARGER_CODE,intent);
                finish();
            }
        });
        mHandler=new Handler();
        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search_content=et_seach.getText().toString().trim();
                search_list.clear();
                isSearch = true;
                if (null!=search_content){
                    SearchUserBean bean = new SearchUserBean();
                    bean.setCondition(search_content);
                    bean.setLastId("");
                    presenter.searchUserAction(bean);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lv_people.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //当滚到最后一行且停止滚动时，执行加载
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
                    loadNextPage(mAdapter.getCount());
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

    private void loadNextPage(int totalItemCount) {
        if(mAdapter.getCount()==0){ //加载更多的机制是需要最后一条信息ID作为检索条件，如果适配器是空的就不需要加载更多
            return;
        }
        ptrLayout.setLoading();
        isDownRefresh = true;
        SearchUserBean bean = new SearchUserBean();
        bean.setCondition(searchContent);
        bean.setLastId(mAdapter.getData().get(totalItemCount-1).getId());
        presenter.searchUserAction(bean);
    }

//    private List<CheckPeopleBean> list_checker;

    @Override
    public void searchSuccess(List<CheckPeopleBean> lists) {
        Log.e("DP","----searchSuccess");
        Log.e("DP","----searchSuccess:"+lists.size());
        hideLoading();
        if (isUpRefresh){
            mAdapter.clear();
            isUpRefresh = false;
            ptrLayout.complete();
        }
        if (isDownRefresh){
            isDownRefresh = false;
        }
        if (isSearch){
            list.clear();
            isSearch=false;
            list=lists;
            mAdapter.replaceAll(list);
            lv_people.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            return;
        }
        ptrLayout.complete();
        list=lists;
        mAdapter.replaceAll(list);
        lv_people.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getEachMessageListResult(EachMessageInfoBean data) {

    }

    @Override
    public void getEachMessageListResult(Object data, String type,String lastId) {

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void reLogin() {

    }


//    public void getSeacherResult(String searchContent){
//        List<String> listTemp=new ArrayList<>();
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).getRealName().contains(searchContent.toUpperCase())|| Tools.getHanyuPinyin(list.get(i).getRealName()).contains(searchContent.toUpperCase())){
//                listTemp.add(list.get(i).getRealName());
//            }
//        }
//        mAdapter.replaceAll(listTemp.);
//        mAdapter.notifyDataSetChanged();
//    }
    @Override
    protected SeacherPresenter createPresenter() {
        return new SeacherPresenter();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach(){
        finish();
    }
}
