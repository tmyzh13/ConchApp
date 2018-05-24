package com.isoftston.issuser.conchapp.views.work;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.corelibs.base.BaseFragment;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.DangerTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceDetailBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceTypeRequstBean;
import com.isoftston.issuser.conchapp.model.bean.MessageBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkCountBean;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.presenter.WorkPresenter;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
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
    public List<WorkDetailBean> listMessage;
    private String type;
    private int bType;
    private String itemID;
    private boolean isUpRefresh;
    private boolean isLastRow=false;
    private boolean isDownRefresh;
    private String lastId="";
    private int lastCount;
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
        Log.i("type",type+"--"+bType);
        tv.setText(type);
        isUpRefresh=true;
        adapter=new WorkMessageItemAdapter(getContext());
        listMessage=new ArrayList<>();
        adapter.addAll(listMessage);
        lv_message.setAdapter(adapter);
        if (bType==2){
            if (type.equals(getString(R.string.my_approve))){
                presenter.getWorkList(lastId,2,"0");
            }else if (type.equals(getString(R.string.my_check))){
                presenter.getWorkList(lastId,2,"1");
            }else {
                presenter.getWorkList(lastId,2,"2");
            }
        }else {
            presenter.getWorkInfo();
        }

//        ptrLayout.disableLoading();
//        ptrLayout.setCanRefresh(false);
//        ptrLayout.setLoading();
        ptrLayout.setRefreshLoadCallback(new PtrAutoLoadMoreLayout.RefreshLoadCallback() {

            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                isUpRefresh = true;
                if (bType==2){
                    if (type.equals(getString(R.string.my_approve))){
                        presenter.getWorkList("",2,"0");
                    }else if (type.equals(getString(R.string.my_check))){
                        presenter.getWorkList("",2,"1");
                    }else {
                        presenter.getWorkList("",2,"2");
                    }
                }else {
                    lastId="";
                    presenter.getWorkInfo();
                }
            }

            @Override
            public void onLoading(PtrFrameLayout frame) {


            }
        });

        lv_message.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入消息详情界面
                String jobId = String.valueOf(adapter.getItem(position).getId());
                if("0".equals(bType)){
                    startActivity(ScanCodeActivity.getLauncher(getContext(),jobId,true));
                }else{
                    startActivity(ScanCodeActivity.getLauncher(getContext(),jobId,false));
                }


            }
        });

        lv_message.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //当滚到最后一行且停止滚动时，执行加载
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
                    loadNextPage(adapter.getCount());
                    isLastRow = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //判断是否滚到最后一行
                    if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                        if(adapter.getCount() > 0)
                        {
                            isLastRow = true;
                        }


                }
            }
        });
//        ptrLayout.setRefreshLoadCallback(this);

    }
    private void loadNextPage(int totalItemCount) {
        ptrLayout.setLoading();
        isDownRefresh = true;
        lastId = adapter.getData().get(totalItemCount-1).getId();
        if (bType==2){
            if (type.equals(getString(R.string.my_approve))){
                presenter.getWorkList(lastId,2,"0");
            }else if (type.equals(getString(R.string.my_check))){
                presenter.getWorkList(lastId,2,"1");
            }else {
                presenter.getWorkList(lastId,2,"2");
            }
        }else {
            presenter.getWorkInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isUpRefresh=true;
        if (bType==2){
            if (type.equals(getString(R.string.my_approve))){
                presenter.getWorkList("",2,"0");
            }else if (type.equals(getString(R.string.my_check))){
                presenter.getWorkList("",2,"1");
            }else {
                presenter.getWorkList("",2,"2");
            }
        }else {
            presenter.getWorkInfo();
        }
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

    }

    @Override
    public void renderData(WorkBean workBean) {

    }

    @Override
    public void getWorkListInfo(List<WorkBean> list) {
        if (list.size()==0){
            ptrLayout.complete();
            return;
        }
        for (WorkBean workBean:list){
            if (type.equals(getString(R.string.all))){
                presenter.getWorkList(lastId,bType,"");
            }else if (type.equals(workBean.getName())){
                presenter.getWorkList(lastId,bType,String.valueOf(workBean.getId()));
            }
        }

    }

    @Override
    public void getWorkList(List<WorkDetailBean> list) {
        hideLoading();
        if (isUpRefresh){
            adapter.clear();
            listMessage.clear();
            listMessage.addAll(list);
            isUpRefresh = false;
            ptrLayout.complete();
        }
        if (isDownRefresh){
            listMessage.addAll(list);
            isDownRefresh = false;
        }
        ptrLayout.complete();
        if (list.size() == 0 && adapter.getCount() > 0){
            return;
        }
        adapter.clear();
        adapter.replaceAll(listMessage);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkError() {
        ptrLayout.complete();
        hideLoading();
    }

    @Override
    public void addWorkSuccess() {

    }

    @Override
    public void getDangerWorkTypeResult(List<DangerTypeBean> list) {

    }

    @Override
    public void getDeviceTypeResult(List<DeviceTypeBean> list) {

    }

    @Override
    public void getDeviceDetailSuccess(List<DeviceDetailBean> list) {

    }

    @Override
    public void getWorkCountSuccess(List<WorkCountBean> list) {

    }

    @Override
    public void onLoading(PtrFrameLayout frame) {

    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {

    }

}
