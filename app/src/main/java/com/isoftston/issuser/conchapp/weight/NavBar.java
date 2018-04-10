package com.isoftston.issuser.conchapp.weight;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corelibs.views.navigation.TranslucentNavBar;
import com.isoftston.issuser.conchapp.R;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

/**
 * Created by issuser on 2018/4/9.
 */

public class NavBar extends TranslucentNavBar {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.ll_right)
    LinearLayout ll_right;
    @Bind(R.id.iv_seach)
    ImageView iv_seach;
    @Bind(R.id.iv_add)
    ImageView iv_add;


    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public NavBar(Context context) {
        super(context);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.v_nav, this);
        ButterKnife.bind(this, this);

        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v_nav;
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(defaultBackListener);
    }

    public void setNavTitle(String title){
        tv_title.setText(title);
    }

    public void hideBack(){
        iv_back.setVisibility(GONE);
    }

    private final OnClickListener defaultBackListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) getContext()).finish();
        }
    };


    public void showSeach(OnClickListener listener){
        ll_right.setVisibility(VISIBLE);
        iv_seach.setVisibility(VISIBLE);
        iv_seach.setOnClickListener(listener);
    }

    public void showAdd(OnClickListener listener){
        ll_right.setVisibility(VISIBLE);
        iv_add.setVisibility(VISIBLE);
        iv_add.setOnClickListener(listener);
    }
}
