package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.WorkApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;
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
     * 默认获取数据
     */
    public void getWorklInfo(String language) {
        api.getWorkInfo(language)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkBean>>() {
                    @Override
                    public void success(BaseData<WorkBean> workBeanBaseData) {
                        view.getWorkInfo(workBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<WorkBean> workBeanBaseData, int status, String message) {
                        view.getWorkError();
                        return super.operationError(workBeanBaseData, status, message);
                    }
                });

    }
}
