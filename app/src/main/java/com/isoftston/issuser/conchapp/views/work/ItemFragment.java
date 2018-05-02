package com.isoftston.issuser.conchapp.views.work;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
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
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
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
    public List<WorkDetailBean> listMessage=new ArrayList<>();
    private String type;
    private int bType;
    private String itemID;
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
        adapter=new WorkMessageItemAdapter(getContext());
        adapter.addAll(listMessage);
        lv_message.setAdapter(adapter);
        ptrLayout.disableLoading();
        ptrLayout.setCanRefresh(false);

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
        ptrLayout.setRefreshLoadCallback(this);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        ptrLayout.disableLoading();
    }

    @Override
    public void renderData(WorkBean workBean) {

    }

    @Override
    public void getWorkListInfo(List<WorkBean> list) {
        for (WorkBean workBean:list){
            if (type.equals(getString(R.string.all))){
                presenter.getWorkList("",bType,"");
            }else if (type.equals(workBean.getName())){
                presenter.getWorkList("",bType,String.valueOf(workBean.getId()));
            }
        }

    }

    @Override
    public void getWorkList(List<WorkDetailBean> list) {
        listMessage.clear();
        listMessage=list;
        adapter.replaceAll(listMessage);
        lv_message.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getWorkError() {
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
    public void onLoading(PtrFrameLayout frame) {

    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {

    }
}
