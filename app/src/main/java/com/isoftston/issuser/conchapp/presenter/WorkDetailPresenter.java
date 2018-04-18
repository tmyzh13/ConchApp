package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.WorkDetailApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.ResponseDataBean;
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
     * @param language 语言
     * @param id 作业id
     */
    public void getWorkDetailInfo(String language,long id) {
        api.getWorkDetailInfo(language, id)
                .compose(new ResponseTransformer<>(this.<BaseData<WorkDetailBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkDetailBean>>() {

                    @Override
                    public void success(BaseData<WorkDetailBean> workDetailBeanBaseData) {
                        view.getWorkDetailInfo(workDetailBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.responseError(0);
                    }

                    @Override
                    public boolean operationError(BaseData<WorkDetailBean> workDetailBeanBaseData, int status, String message) {
                        view.responseError(0);
                        return super.operationError(workDetailBeanBaseData, status, message);
                    }
                });
    }

    /**
     * 撤销
     * @param id
     */
    public void revokeJob(long id){
        api.cancelJob(id)
                .compose(new ResponseTransformer<BaseData<ResponseDataBean>>(this.<BaseData<ResponseDataBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<ResponseDataBean>>() {
                    @Override
                    public void success(BaseData<ResponseDataBean> responseDataBeanBaseData) {
                        view.revokeJob(responseDataBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.responseError(1);
                    }
                });
    }

    /**
     *
     * @param id 主键
     * @param code 设备号
     * @param imgs 图片路径，以逗号分隔开的
     */
    public void submitJob(long id,String code,String imgs){
       api.submitJob(id,code,imgs)
               .compose(new ResponseTransformer<BaseData<ResponseDataBean>>(this.<BaseData<ResponseDataBean>>bindToLifeCycle()))
               .subscribe(new ResponseSubscriber<BaseData<ResponseDataBean>>() {
                   @Override
                   public void success(BaseData<ResponseDataBean> responseDataBeanBaseData) {
                       view.submitJob(responseDataBeanBaseData.data);
                   }

                   @Override
                   public void onError(Throwable e) {
                       super.onError(e);
                       view.responseError(2);
                   }
               });
    }
}
