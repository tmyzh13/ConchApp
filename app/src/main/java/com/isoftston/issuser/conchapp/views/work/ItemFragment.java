package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;
import com.isoftston.issuser.conchapp.views.work.adpter.WorkMessageItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by issuser on 2018/4/9.
 */

public class ItemFragment extends BaseFragment<WorkView,WorkPresenter> implements WorkView, PtrAutoLoadMoreLayout.RefreshLoadCallback{

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.lv_message)
    AutoLoadMoreListView lv_message;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptrLayout;

    public WorkMessageItemAdapter adapter;
    public List<WorkBean> listMessage;
    private String type;
    private int bType;
    public static Fragment newInstance(String type, int bigType) {
        ItemFragment fragment =new ItemFragment();
        Bundle b =new Bundle();
        b.putString("type",type);
        b.putInt("bigType",bigType);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type=getArguments().getString("type");
        bType=getArguments().getInt("bigType");
        tv.setText(type);
        listMessage=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            listMessage.add(new WorkBean());
//        }
        if (bType==0){
            presenter.getWorkInfo("0","3","");
        }else if (bType==1){
            presenter.getWorkInfo("1","3","");
        }else {
            presenter.getWorkInfo("2","3","");
        }
        adapter=new WorkMessageItemAdapter(getContext());
        adapter.addAll(listMessage);
        lv_message.setAdapter(adapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);

        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入消息详情界面
                startActivity(ScanCodeActivity.getLauncher(getContext(),adapter.getItem(position)));

            }
        });
        ptrLayout.setRefreshLoadCallback(this);
    }

    @Override
    protected WorkPresenter createPresenter() {
        return new WorkPresenter();
    }

    @Override
    public void onLoadingCompleted() {
        hideLoading();
    }

    @Override
    public void onAllPageLoaded() {
        ptrLayout.disableLoading();
    }

    @Override
    public void renderData(WorkBean workBean) {

    }

    @Override
    public void getWorkListInfo(List<WorkBean> list) {
        listMessage=list;
        adapter.addAll(listMessage);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getWorkError() {

    }

    @Override
    public void addWorkSuccess() {

    }

    @Override
    public void onLoading(PtrFrameLayout frame) {

    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {

    }
}
