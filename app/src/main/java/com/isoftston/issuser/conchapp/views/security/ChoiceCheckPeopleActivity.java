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
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckPeopleAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
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
    ListView lv_people;

    private Context context=ChoiceCheckPeopleActivity.this;
    private CheckPeopleAdapter adapter;
    private String searchContent="";
    private List<String> list;
    private Handler mHandler;

    private int code;

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

        adapter=new CheckPeopleAdapter(context);
        list =new ArrayList<>();
        SearchUserBean bean = new SearchUserBean();
        bean.setCondition(searchContent);
        bean.setLastId("");
        presenter.searchUserAction(bean);
        adapter.addAll(list);
        lv_people.setAdapter(adapter);
        lv_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent();
                intent.putExtra(Constant.CHECK_PEOPLE,list.get(position));
                intent.putExtra(Constant.CHECK_PEOPLE_ID,list_checker.get(position).getId());
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

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private List<CheckPeopleBean> list_checker;

    @Override
    public void searchSuccess(List<CheckPeopleBean> lists) {
        list_checker=lists;
        list.clear();
        for(int i=0;i<lists.size();i++){
            list.add(lists.get(i).getUserName());
        }
        adapter.addAll(list);
        lv_people.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    public void getSeacherResult(String searchContent){
        List<String> listTemp=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).contains(searchContent.toUpperCase())|| Tools.getHanyuPinyin(list.get(i)).contains(searchContent.toUpperCase())){
                listTemp.add(list.get(i));
            }
        }
       adapter.replaceAll(listTemp);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected SeacherPresenter createPresenter() {
        return new SeacherPresenter();
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach(){
        finish();
    }
}
