package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.ListPagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.corelibs.subscriber.ResponseSubscriber;
import com.google.gson.JsonObject;
import com.isoftston.issuser.conchapp.model.UserHelper;
import com.isoftston.issuser.conchapp.model.apis.CheckApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CheckAllDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.CheckAllDevicesBean;
import com.isoftston.issuser.conchapp.model.bean.CheckBean;
import com.isoftston.issuser.conchapp.model.bean.CheckConditionDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.CheckDeviceRequestBean;
import com.isoftston.issuser.conchapp.model.bean.CheckOneDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;

import org.json.JSONObject;

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
//        bean.userId=UserHelper.getUserId()+"";
        bean.userId="1";
        bean.location="湖北";
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
    //查询设备说明信息
    public void getDeviceInfo(String deviceId){
        CheckBean checkBean=new CheckBean();
        checkBean.descId=deviceId;
        api.getDeviceInfo(checkBean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceBean>>() {
                    @Override
                    public void success(BaseData<DeviceBean> deviceBeanBaseData) {
                        view.checkDeviceResult(deviceBeanBaseData.data);
                    }

                });
    }
    //获取所有设备列表
    public void getAllDeviceInfo(String content){
        CheckAllDeviceBean checkAllDeviceBean=new CheckAllDeviceBean();
//        checkAllDeviceBean.userId=UserHelper.getUserId()+"";
        checkAllDeviceBean.userId = "1";
        checkAllDeviceBean.equipId=content;
        api.getAllDeviceInfo(checkAllDeviceBean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceListBean>>() {
                    @Override
                    public void success(BaseData<DeviceListBean> deviceBeanBaseData) {
                        Log.i("zt",deviceBeanBaseData.data.list.size()+"");
                        view.CheckAllDeviceResult(deviceBeanBaseData.data.list);
                    }

                });
    }
    //条件查询设备
    public void getConditionDeviceInfo(String content){
        CheckConditionDeviceBean bean=new CheckConditionDeviceBean();
        bean.userId=UserHelper.getUserId()+"";
        bean.equipId="";
        bean.condition=content;
        api.getConditionDeviceInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceListBean>>() {
                    @Override
                    public void success(BaseData<DeviceListBean> deviceBeanBaseData) {
                        view.CheckAllDeviceResult(deviceBeanBaseData.data.list);
                    }

                });

    }
    //查询一条设备信息
    public void getOneDeviceInfo(String content){
        CheckOneDeviceBean bean=new CheckOneDeviceBean();
//        bean.userId=UserHelper.getUserId()+"";
        bean.userId="1";
        bean.equipId=content;
        api.getOneDeviceInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceBean>>() {
                    @Override
                    public void success(BaseData<DeviceBean> deviceBeanBaseData) {
                        view.checkDeviceResult(deviceBeanBaseData.data);
                    }

                });
    }
}
