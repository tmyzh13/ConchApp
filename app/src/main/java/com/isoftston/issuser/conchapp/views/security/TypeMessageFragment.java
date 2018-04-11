package com.isoftston.issuser.conchapp.views.security;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypeAdapter;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/10.
 */

public class TypeMessageFragment extends BaseFragment {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.lv_message)
    AutoLoadMoreListView lv_message;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    public String type;
    public MessageTypeAdapter adapter;
    public List<MessageBean> listMessage;

    public static TypeMessageFragment newInstance(String type){
        TypeMessageFragment fragment =new TypeMessageFragment();
        Bundle b =new Bundle();
        b.putString("type",type);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type=getArguments().getString("type");
        Log.e("yzh","1111111"+type);
        tv.setText(type);

        adapter=new MessageTypeAdapter(getContext());
        listMessage=new ArrayList<>();
        for(int i=0;i<10;i++){
            listMessage.add(new MessageBean());
        }
        adapter.addAll(listMessage);
        lv_message.setAdapter(adapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
