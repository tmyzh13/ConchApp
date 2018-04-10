package com.isoftston.issuser.conchapp.views.seacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.presenter.SeacherPresenter;
import com.isoftston.issuser.conchapp.utils.Tools;
import com.isoftston.issuser.conchapp.views.interfaces.SeacherView;
import com.isoftston.issuser.conchapp.weight.FlowLayout;

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

    private  Context context=SeacherActivity.this;
    private String historys[]={"安全","事件1","我的发布","项目中的隐患"
                                ,"重大违章事故的发生"};

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,SeacherActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searcher;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ViewGroup.LayoutParams lp =viewStatue.getLayoutParams();
        lp.height= Tools.getStatueHeight(context);
        viewStatue.setLayoutParams(lp);

        setBarColor(getResources().getColor(R.color.transparent_black));

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.search_all)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.search_hidden_trouble)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.search_illegal)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.search_my_send)));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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

                    }
                    return true;
                }
                return false;
            }
        });
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
                }
            });
            flowLayout.addView(tv);//添加到父View
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

}
