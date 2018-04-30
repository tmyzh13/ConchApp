package com.isoftston.issuser.conchapp.views.security;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypePageAdapter;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/12.
 */

public class IllegalMessageFragment extends BaseFragment {

    @Bind(R.id.viewPagerIllegal)
    ViewPager viewPager;

    public String[] tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_illegal_msg;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.e("yzh","illegalMessageFragment");
        tabs=new String[]{getString(R.string.alls)};
        MessageTypePageAdapter adapter=new MessageTypePageAdapter(getActivity().getSupportFragmentManager(),tabs,1);
        viewPager.setAdapter(adapter);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
