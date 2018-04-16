package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckPeopleAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by john on 2018/4/15.
 */

public class ChoiceCheckPeopleActivity extends BaseActivity{


    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.et_seach)
    EditText et_seach;
    @Bind(R.id.lv_people)
    ListView lv_people;

    private Context context=ChoiceCheckPeopleActivity.this;
    private CheckPeopleAdapter adapter;
    private String searchContent;
    private List<CheckPeopleBean> list;
    private Handler mHandler;
    private SearchTask mSearchTesk;

    public static Intent getLaucnher(Context context){
        Intent intent =new Intent(context,ChoiceCheckPeopleActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_check_people;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.choice_check_people));
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));

        adapter=new CheckPeopleAdapter(context);
        list =new ArrayList<>();
        for(int i=0;i<15;i++){
            CheckPeopleBean  bean =new CheckPeopleBean();
            bean.name="检修员"+i;
            list.add(bean);
        }
        adapter.addAll(list);
        lv_people.setAdapter(adapter);
        lv_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent();
                intent.putExtra(Constant.CHECK_PEOPLE,list.get(position).name);
                setResult(10,intent);
                finish();
            }
        });
        mHandler=new Handler();
        mSearchTesk=new SearchTask();
        et_seach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 0) {
                    searchContent=charSequence.toString();
                    mHandler.removeCallbacks(mSearchTesk);
                    mHandler.postDelayed(mSearchTesk,500);
                } else {
                    searchContent="";
                    mHandler.removeCallbacks(mSearchTesk);
                    adapter.replaceAll(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    /**
     * 搜索任务
     */
    class SearchTask implements Runnable {

        @Override
        public void run() {
            Log.e("---SearchTask---","开始查询");
            getSeacherResult(searchContent);
        }
    }

    public void getSeacherResult(String searchContent){
        List<CheckPeopleBean> listTemp=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).name.contains(searchContent.toUpperCase())){
                listTemp.add(list.get(i));
            }
        }
       adapter.replaceAll(listTemp);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach(){
        finish();
    }
}