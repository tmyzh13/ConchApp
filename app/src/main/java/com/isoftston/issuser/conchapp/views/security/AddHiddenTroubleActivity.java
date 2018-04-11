package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.IMEUtil;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.InputView;
import com.isoftston.issuser.conchapp.weight.NavBar;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by issuser on 2018/4/10.
 */

public class AddHiddenTroubleActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_find_company)
    InputView input_find_company;
    @Bind(R.id.input_trouble_company)
    InputView input_trouble_company;
    @Bind(R.id.input_check_worker)
    InputView input_check_worker;
    @Bind(R.id.input_place)
    InputView input_place;
    @Bind(R.id.input_position)
    InputView input_position;
    @Bind(R.id.input_source)
    InputView input_source;
    @Bind(R.id.tv_detail_name_content)
    TextView tv_detail_name_content;
    @Bind(R.id.tv_trouble_name)
    TextView tv_trouble_name;
    @Bind(R.id.ll_alter)
    LinearLayout ll_alter;
    @Bind(R.id.et_name)
    EditText et_name;

    private Context context =AddHiddenTroubleActivity.this;
    //0隐患 1违章
    private String type;

    public static Intent getLauncher(Context context,String type){
        Intent intent =new Intent(context,AddHiddenTroubleActivity.class);
        intent.putExtra("type",type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_hidden_trouble;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.hidden_trouble));
        navBar.setColorRes(R.color.white);
        navBar.setTitleColor(getResources().getColor(R.color.black));
        setBarColor(getResources().getColor(R.color.transparent_black));
        input_find_company.setInputType(getString(R.string.hidden_trouble_find_company));
        input_trouble_company.setInputType(getString(R.string.hidden_trouble_company));
        input_check_worker.setInputType(getString(R.string.hidden_trouble_check));
        input_place.setInputType(getString(R.string.hidden_trouble_place));
        input_position.setInputType(getString(R.string.hidden_trouble_position));
        input_source.setInputType(getString(R.string.hidden_trouble_source));

        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(et_name.getText().toString())){
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_detail_name_content.setText(et_name.getText().toString());
                    }else{
                        ll_alter.setVisibility(View.VISIBLE);
                    }
                    et_name.setVisibility(View.GONE);
                    IMEUtil.closeIME(et_name,context);
                    return true;
                }
                return false;
            }
        });
        et_name.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if(!TextUtils.isEmpty(et_name.getText().toString())){
                        tv_detail_name_content.setVisibility(View.VISIBLE);
                        tv_detail_name_content.setText(et_name.getText().toString());
                    }else{
                        ll_alter.setVisibility(View.VISIBLE);
                    }
                    et_name.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.ll_photo)
    public void goPhoto(){
        //进入照片选择界面
    }

    @OnClick(R.id.ll_alter)
    public void alterNamebtn(){
        ll_alter.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_detail_name_content)
    public void alterNameText(){
        tv_detail_name_content.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
    }
}
