package com.isoftston.issuser.conchapp.views.security;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
import com.isoftston.issuser.conchapp.adapters.SecurityAdapter;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.bean.HiddenTroubleMsgNumBean;
import com.isoftston.issuser.conchapp.model.bean.MineMsgNumBean;
import com.isoftston.issuser.conchapp.model.bean.MsgTotalCountBean;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.SecurityTroubleBean;
import com.isoftston.issuser.conchapp.model.bean.SecurityUpdateBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.presenter.SecurityPresenter;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;
import com.isoftston.issuser.conchapp.views.message.ItemDangerDtailActivity;
import com.isoftston.issuser.conchapp.views.message.ItemDtailActivity;
import com.isoftston.issuser.conchapp.views.message.utils.PushBroadcastReceiver;
import com.isoftston.issuser.conchapp.views.message.utils.PushCacheUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    private final String TAG = TypeMessageFragment.class.getSimpleName();

    private boolean isLastRow = false;
    private boolean isUpRefresh;
    private boolean isDownRefresh;
    private Integer lastCount = 0;

    public String type;
    public SecurityAdapter adapter;
    public List<SecurityTroubleBean> listMessage;
    private int bType;
    private int currrentPage;

    private PushBroadcastReceiver broadcastReceiver;

    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            updateList();
        }
    };

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
        EventBus.getDefault().register(this);
        type=getArguments().getString("type");
        bType=getArguments().getInt("bigType");
        Log.i(TAG,"type:" + type + ",bType:" + bType);
//        ToastUtils.showtoast(getActivity(),type);
        tv.setText(type);
        String item = "";
        if (getString(R.string.alls).equals(type)){
            item = "all";
        }else if(getString(R.string.unapproval).equals(type)){
            item = "wsp";
        }else if(getString(R.string.no_send).equals(type)){
            item = "wfs";
        }else if(getString(R.string.unsales).equals(type)){
            item = "wxa";
        }else if(getString(R.string.send).equals(type)){
            item = "fb";
        }else if (getString(R.string.not_alter).equals(type)){
            item = "wzg";
        }else if (getString(R.string.overdue).equals(type)){
            item = "yq";
        }else if (getString(R.string.altered).equals(type)){
            item = "yzg";
        }else if (getString(R.string.not_check).equals(type)){
            item = "wys";
        }else if (getString(R.string.weizhang).equals(type)){
            item = "wz";
        }else if (getString(R.string.trouble).equals(type)){
            item = "yh";
        }else if (getString(R.string.my_send).equals(type)){
            item = "fb";
        }
        else if (getString(R.string.my_approval).equals(type)){
            item = "sp";
        }else if (getString(R.string.my_fix).equals(type)){
            item = "zg";
        }else if (getString(R.string.my_delay).equals(type)){
            item = "yq";
        }else if (getString(R.string.my_sales).equals(type)){
            item = "xa";
        }else if (getString(R.string.my_accept).equals(type)){
            item = "ys";
        }

        adapter=new SecurityAdapter(getContext());
        listMessage=new ArrayList<>();
        isUpRefresh = true;
        if (bType==0){
            presenter.getSafeMessageList("yh",item,"");
        }else if (bType==1){
            presenter.getSafeMessageList("wz",item,"");
        }else {
            presenter.getSafeMessageList("wd",item,"");
        }
        lv_message.setAdapter(adapter);
        ptrLayout.setRefreshLoadCallback(new PtrAutoLoadMoreLayout.RefreshLoadCallback() {
            @Override
            public void onLoading(PtrFrameLayout frame) {
            }

            @Override
            public void onRefreshing(PtrFrameLayout frame) {
                isUpRefresh = true;
                setRuestData("");

            }
        });

        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入消息详情界面
                Intent intent;
                View  readStatus= view.findViewById(R.id.view_read_statue);
                readStatus.setVisibility(View.GONE);
                Bundle bundle=new Bundle();
                String troubleType = "";
                Log.d(TAG,"position:" + position + ",count:" + listMessage.size());
                if ("false".equals(PreferencesHelper.getData(Constant.YH_DETAIL))){
                    ToastMgr.show(getString(R.string.view_purview));
                    return;
                }
                if ("yh".equals(listMessage.get(position).getYhlxbm())) {
                    troubleType = "yh";
                    intent =new Intent(getActivity(),ItemDangerDtailActivity.class);
                }else {
                    troubleType = "wz";
                    intent =new Intent(getActivity(),ItemDtailActivity.class);
                }
                bundle.putString("type",troubleType);
                bundle.putString("id",listMessage.get(position).getId());
                listMessage.get(position).setRead(true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lv_message.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //当滚到最后一行且停止滚动时，执行加载
                if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //加载元素
                    Log.i(TAG,"type:" + type + ",count:" + adapter.getCount());
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
    }

    private void setRuestData(String lastid) {
        String item = getType();
        if (bType==0){
            presenter.getSafeMessageList("yh",item,lastid);
        }else if (bType==1){
            presenter.getSafeMessageList("wz",item,lastid);
        }else {
            presenter.getSafeMessageList("wd",item,lastid);
        }
    }

    public String getType(){
        if (getString(R.string.alls).equals(type)){
            return  "all";
        }else if(getString(R.string.send).equals(type)){
            return "fb";
        }else if(getString(R.string.unapproval).equals(type)){
            return "wsp";
        }else if(getString(R.string.no_send).equals(type)){
            return "wfs";
        }else if(getString(R.string.unsales).equals(type)){
            return "wxa";
        }else if (getString(R.string.not_alter).equals(type)){
            return "wzg";
        }else if (getString(R.string.overdue).equals(type)){
            return "yq";
        }else if (getString(R.string.altered).equals(type)){
            return "yzg";
        }else if (getString(R.string.not_check).equals(type)){
            return "wys";
        }else if (getString(R.string.weizhang).equals(type)){
            return "wz";
        }else if (getString(R.string.trouble).equals(type)){
            return "yh";
        }else if (getString(R.string.my_send).equals(type)){
            return "fb";
        }
        else if (getString(R.string.my_approval).equals(type)){
            return "sp";
        }else if (getString(R.string.my_fix).equals(type)){
            return "zg";
        }else if (getString(R.string.my_delay).equals(type)){
            return "yq";
        }else if (getString(R.string.my_sales).equals(type)){
            return "xa";
        }else if (getString(R.string.my_accept).equals(type)){
            return "ys";
        }
        return  "";
    }

    private void loadNextPage(int totalItemCount){
        ptrLayout.setLoading();
        isDownRefresh = true;
        String item = getType();
        String lastId = adapter.getData().get(totalItemCount-1).getId();
        setRuestData(lastId);
    }


    @Subscribe
    public void refreshPage(SecurityUpdateBean bean)
    {
        if(bean != null&& bean.getType()==1){
            isUpRefresh = true;
            setRuestData("");
        }
    }


    private void registerBroadcast() {
        broadcastReceiver = new PushBroadcastReceiver(mHander);
        IntentFilter intentFilter = new IntentFilter("getThumbService");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

    }
    public void updateList(){
        PushCacheUtils.getInstance().compareLocalSecurityPushMessage(getContext(),adapter.getData());
        adapter.notifyDataSetChanged();
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
        listMessage.addAll(data.list);
        MsgTotalCountBean msg = new MsgTotalCountBean();
        msg.setYhCount(data.total.getYH());
        msg.setWzCount(data.total.getWZ());
        msg.setIsUpdate(0);
        EventBus.getDefault().post(msg);

        HiddenTroubleMsgNumBean HiddenMsg = new HiddenTroubleMsgNumBean();
        HiddenMsg.setAll(data.total.getYH());
        HiddenMsg.setWsp(data.total.getYHWSP());
        HiddenMsg.setWfs(data.total.getYHWFS());
        HiddenMsg.setFb(data.total.getYHFB());
        HiddenMsg.setWxa(data.total.getYHWXA());
        HiddenMsg.setWys(data.total.getYHWYS());
        HiddenMsg.setWzg(data.total.getYHWZG());
        HiddenMsg.setYq(data.total.getYHYQ());
        HiddenMsg.setYzg(data.total.getYHYZG());
        EventBus.getDefault().post(HiddenMsg);

        MineMsgNumBean mineMsgNumBean = new MineMsgNumBean();
        mineMsgNumBean.setWDFB(data.total.getWDFB());
        mineMsgNumBean.setWDSP(data.total.getWDSP());
        mineMsgNumBean.setWDZG(data.total.getWDZG());
        mineMsgNumBean.setWDYS(data.total.getWDYS());
        mineMsgNumBean.setWDXA(data.total.getWDXA());
        EventBus.getDefault().post(mineMsgNumBean);

        PushCacheUtils.getInstance().compareLocalSecurityPushMessage(getContext(),listMessage);

        if (isUpRefresh){
            adapter.clear();
            listMessage.clear();
            listMessage.addAll(data.list);
            isUpRefresh = false;
        }
        if (isDownRefresh){
            isDownRefresh = false;
        }
        ptrLayout.complete();
        if (data.list.size() == 0 && adapter.getCount() > 0){
            return;
        }

        adapter.addAll(data.list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void addFailed() {

    }

    @Override
    public void getSafeChoiceList(SecuritySearchBean b) {

    }

    @Override
    public void getOrgList(List<OrgBean> bean) {

    }

    @Override
    public void getOrgId(String orgId) {

    }

    @Override
    public void getWorkError() {
        ptrLayout.complete();
        hideLoading();
    }

    @Override
    public void setUserInfo(UserInfoBean userInfo) {

    }

    @Override
    public void onResume() {
        registerBroadcast();
        super.onResume();
    }

    @Override
    public void onPause() {
        if(broadcastReceiver != null){
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
