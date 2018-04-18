package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.pagination.core.BasePaginationPresenter;
import com.corelibs.pagination.presenter.ListPagePresenter;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.UserHelper;
import com.isoftston.issuser.conchapp.model.apis.CheckApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CheckDeviceRequestBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;

import java.util.List;

/**
 * Created by issuser on 2018/4/16.
 */

public class CheckPresenter extends ListPagePresenter<CheckView> {

    private CheckApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(CheckApi.class);
    }

    @Override
    public void onStart() {

    }

    /**
     * 获取设备列表
     * @param reload
     */
    public void getDevice(final boolean reload){
        if(!doPagination(reload)){
            return;
        }
        if(reload){
            view.showLoading();
        }
        api.getDevices("1",getPageNo()+"")
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceListBean>>bindToLifeCycle()))
                .subscribe(new PaginationSubscriber<BaseData<DeviceListBean>>(view,this,reload){

                    @Override
                    protected void onDataNotNull(BaseData<DeviceListBean> deviceListBeanBaseData) {
                        view.renderDatas(reload,deviceListBeanBaseData.data.list);
                    }

                    @Override
                    protected Object getCondition(BaseData<DeviceListBean> deviceListBeanBaseData, boolean dataNotNull) {
                        return deviceListBeanBaseData.data.list;
                    }

                    @Override
                    protected List getListResult(BaseData<DeviceListBean> deviceListBeanBaseData, boolean dataNotNull) {
                        if (dataNotNull) {
                            return deviceListBeanBaseData.data.list;
                        }
                        return null;
                    }
                });
    }

    /**
     * 扫描之后获取设备信息
     * @param content
     */
    public void checkDevice(String content){
        CheckDeviceRequestBean bean =new CheckDeviceRequestBean();
        bean.equipId=content;
        bean.userId=UserHelper.getUserId()+"";
        bean.location="";
        api.checkDevices(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceBean>>() {
                    @Override
                    public void success(BaseData<DeviceBean> deviceBeanBaseData) {
                        view.checkDeviceResult(deviceBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.checkDeviceResultError();
                    }

                    @Override
                    public boolean operationError(BaseData<DeviceBean> deviceBeanBaseData, int status, String message) {
                        view.checkDeviceResultError();
                        return super.operationError(deviceBeanBaseData, status, message);
                    }
                });
    }
    public void getDeviceInfo(String deviceId){
        api.getDeviceInfo(deviceId)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceBean>>() {
                    @Override
                    public void success(BaseData<DeviceBean> deviceBeanBaseData) {
                        view.checkDeviceResult(deviceBeanBaseData.data);
                    }

                });
    }
}
