package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.CheckDeviceAdapter;
import com.isoftston.issuser.conchapp.adapters.CheckTypeAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.YhlxBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.views.work.NewWorkActivity;
import com.isoftston.issuser.conchapp.weight.NavBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/26.
 */

public class ChoiceTypeActivity extends BaseActivity<SecuryView,SecurityPresenter> implements SecuryView  {
    private Context context = ChoiceTypeActivity.this;
    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.lv_device)
    ListView lv_device;
    private List<YhlxBean> yhlxList=new ArrayList<>();
    private List<YhlxBean> yhlList=new ArrayList<>();
    private List<YhlxBean> yhList=new ArrayList<>();
    private CheckTypeAdapter adapter;
    private int mark;
    public static Intent getLaucnher(Context context,int i){//根据code设置头部标题文字
        Intent intent =new Intent(context,ChoiceTypeActivity.class);
        intent.putExtra("mark",i);
        return intent;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_check_device;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setNavTitle(getString(R.string.yh_varies));
        nav.setColorRes(R.color.white);
        nav.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        nav.showBack(2);
        mark=getIntent().getIntExtra("mark",-1);
        presenter.getCompanyChoiceList();
        adapter = new CheckTypeAdapter(context);
        lv_device.setAdapter(adapter);
        lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String code=adapter.getItem(i).getCODE_();
                yhList.clear();
                for (YhlxBean bean :yhlxList){
                    if (code.equals(bean.getPCODE_())){
                        yhList.add(bean);
                    }
                }
                if (yhList != null && yhList.size() != 0) {
                    adapter.replaceAll(yhList);
//                    adapter.addAll(listdata_next);
                    lv_device.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Intent intent =new Intent();
                    intent.putExtra(Constant.CHECK_PEOPLE,adapter.getItem(i).getNAME_());
                    setResult(NewWorkActivity.CHOSE_DEVICE_CODE,intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected SecurityPresenter createPresenter() {
        return new SecurityPresenter();
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

    @Override
    public void addSuccess() {

    }

    @Override
    public void getSafeListSuccess(SafeListBean data) {

    }

    @Override
    public void addFailed() {

    }

    @Override
    public void getSafeChoiceList(SecuritySearchBean bean) {
        yhlList.clear();
        if (mark == 1) {
            yhlxList = bean.YHLX;
            for (int i=0;i<yhlxList.size();i++){
                if (TextUtils.isEmpty(yhlxList.get(i).getPCODE_())||null==yhlxList.get(i).getPCODE_()||" ".equals(yhlxList.get(i).getPCODE_())){
                    yhlList.add(yhlxList.get(i));
                }
            }

        }else if (mark==2){
            yhlxList=bean.SFYXCZG;
            for (int i=0;i<yhlxList.size();i++){
                if (TextUtils.isEmpty(yhlxList.get(i).getPCODE_())||null==yhlxList.get(i).getPCODE_()||" ".equals(yhlxList.get(i).getPCODE_())){
                    yhlList.add(yhlxList.get(i));
                }
            }
        }
        adapter.addAll(yhlList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getOrgId(String orgId) {

    }

    @OnClick(R.id.tv_cancel)
    public void cancelSeach() {
        finish();
    }
}
