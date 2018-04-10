package com.isoftston.issuser.conchapp.views.mine;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;

import java.lang.reflect.Field;

/**
 * Created by issuser on 2018/4/9.
 */

public class MineFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }



    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
