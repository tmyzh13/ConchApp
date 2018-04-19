package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.UserHelper;
import com.isoftston.issuser.conchapp.model.apis.WorkApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.WorkTypeRequestBean;
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
    public void getWorkInfo(String id,String language,String type,String item) {
        WorkListRequestBean bean=new WorkListRequestBean();
        bean.jobId=id;
        bean.language=language;
        bean.type=type;
        bean.item=item;
        api.getWorkInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkListBean>>() {
                    @Override
                    public void success(BaseData<WorkListBean> workBeanBaseData) {
                        view.getWorkListInfo(workBeanBaseData.data.list);
                        Log.i("zhoutao",workBeanBaseData.data.list.size()+"");
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
                        Log.i("zhoutao",workBeanBaseData.data.list.size()+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                });

    }
}
