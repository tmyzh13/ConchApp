package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.WorkDetailApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;
import com.isoftston.issuser.conchapp.views.interfaces.WorkDetailView;

/**
 * Created by issuser on 2018/4/17.
 */

public class WorkDetailPresenter extends BasePresenter<WorkDetailView> {
    private WorkDetailApi api;

    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(WorkDetailApi.class);
    }

    /**
     * 默认获取数据
     */
    public void getWorkDetailInfo() {
        api.getWorkDetailInfo("", 1)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkDetailBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkDetailBean>>() {

                    @Override
                    public void success(BaseData<WorkDetailBean> workDetailBeanBaseData) {
                        view.getWorkDetailInfo(workDetailBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkDetailError();
                    }

                    @Override
                    public boolean operationError(BaseData<WorkDetailBean> workDetailBeanBaseData, int status, String message) {
                        view.getWorkDetailError();
                        return super.operationError(workDetailBeanBaseData, status, message);
                    }
                });
    }
}
