package com.isoftston.issuser.conchapp.views.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.adapters.MessageTypeAdapter;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.SafeBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.views.message.ItemDtailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/10.
 */

public class TypeMessageFragment extends BaseFragment<SecuryView,SecurityPresenter> implements SecuryView {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.lv_message)
    AutoLoadMoreListView lv_message;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    public String type;
    public MessageTypeAdapter adapter;
    public List<MessageBean> listMessage;
    private int bType;

    public static TypeMessageFragment newInstance(String type, int bigType){
        TypeMessageFragment fragment =new TypeMessageFragment();
        Bundle b =new Bundle();
        b.putString("type",type);
        b.putInt("bigType",bigType);
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
        bType=getArguments().getInt("bigType");
//        ToastUtils.showtoast(getActivity(),type);
        tv.setText(type);

        adapter=new MessageTypeAdapter(getContext());
        listMessage=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            listMessage.add(new MessageBean());
//        }
        if (bType==0){
            presenter.getSafeMessageList("yh","wzg","");
        }else if (bType==1){
            presenter.getSafeMessageList("wz","","");
        }else {
            presenter.getSafeMessageList("wz","wzg","");
        }
        adapter.addAll(listMessage);
        lv_message.setAdapter(adapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);

        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入消息详情界面
                Intent intent =new Intent(getActivity(),ItemDtailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type",listMessage.get(position).getType());
                bundle.putString("id",listMessage.get(position).getId());
                startActivity(intent);
                //test
//                Log.e("yzh","onItemclick");
//                RxBus.getDefault().send(new Object(),"ssss");
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
        SafeBean bean=data.total;
        listMessage=data.list;
        adapter.addAll(listMessage);
        adapter.notifyDataSetChanged();
        lv_message.setAdapter(adapter);
    }

    @Override
    public void addFailed() {

    }
}
