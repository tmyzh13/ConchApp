package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.UserHelper;
import com.isoftston.issuser.conchapp.model.apis.WorkApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.FixWorkBean;
import com.isoftston.issuser.conchapp.model.bean.NewWorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.WorkTypeRequestBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.WorkView;

/**
 * Created by issuser on 2018/4/18.
 */

public class WorkPresenter extends BasePresenter<WorkView> {
    private WorkApi api;
    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(WorkApi.class);
    }
    /**
     * 获取作业列表数据
     */
    public void getWorkInfo(String type,String item,String lastid) {
        WorkListRequestBean bean=new WorkListRequestBean();
        bean.type=type;
        bean.item=item;
        bean.lastId=lastid;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.getWorkInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkListBean>>() {
                    @Override
                    public void success(BaseData<WorkListBean> workBeanBaseData) {

                        view.getWorkListInfo(workBeanBaseData.data.list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                });

    }
    public void getWorkTypeInfo(String lastid,String type) {
        WorkTypeRequestBean bean=new WorkTypeRequestBean();
        bean.userId= UserHelper.getUserId()+"";
        bean.lastId=lastid;
        bean.type=type;
        api.getWorkTypeInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkListBean>>() {
                    @Override
                    public void success(BaseData<WorkListBean> workBeanBaseData) {
                        view.getWorkListInfo(workBeanBaseData.data.list);
                        Log.i("zt",workBeanBaseData.data.list.size()+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                });

    }
    public void addWork(NewWorkBean bean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.addWork(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData >bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData workBeanBaseData) {
                        view.addWorkSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
    public void fixWork(FixWorkBean bean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.fixWork(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData workBeanBaseData) {
                        view.addWorkSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
