package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.isoftston.issuser.conchapp.model.apis.GetSecurityDataApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListBean;
import com.isoftston.issuser.conchapp.views.interfaces.TypeMessageView;

import java.util.List;

/**
 * Created by issuser on 2018/4/17.
 */

public class TypeMessagePresenter extends PagePresenter<TypeMessageView> {

    private GetSecurityDataApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(GetSecurityDataApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getDatas(boolean reload){
        api.getSecurityData("en","0","",0)
                .compose(new ResponseTransformer<>(this.<BaseData<MessageListBean>>bindToLifeCycle()))
                .subscribe(new PaginationSubscriber<BaseData<MessageListBean>>(view,this,reload){

                    @Override
                    protected void onDataNotNull(BaseData<MessageListBean> deviceListBeanBaseData) {
//                        view.renderDatas(reload,deviceListBeanBaseData.data.list);
                    }

                    @Override
                    protected Object getCondition(BaseData<MessageListBean> deviceListBeanBaseData, boolean dataNotNull) {
                        return (deviceListBeanBaseData.page.totalCount/deviceListBeanBaseData.page.pageSize+1);
                    }

                    @Override
                    protected List getListResult(BaseData<MessageListBean> deviceListBeanBaseData, boolean dataNotNull) {
                        if (dataNotNull) {
                            return deviceListBeanBaseData.data.list;
                        }
                        return null;
                    }
                });
    }
}
