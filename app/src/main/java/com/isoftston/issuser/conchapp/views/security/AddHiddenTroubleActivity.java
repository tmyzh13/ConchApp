package com.isoftston.issuser.conchapp.views.security;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.weight.NavBar;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/10.
 */

public class AddHiddenTroubleActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;

    public static Intent getLauncher(Context context){
        Intent intent =new Intent(context,AddHiddenTroubleActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_hidden_trouble;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
