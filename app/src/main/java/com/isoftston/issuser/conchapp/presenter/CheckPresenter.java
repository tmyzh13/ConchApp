package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.ListPagePresenter;
import com.corelibs.subscriber.PaginationSubscriber;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.UserHelper;
import com.isoftston.issuser.conchapp.model.apis.CheckApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CheckAllDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.CheckBean;
import com.isoftston.issuser.conchapp.model.bean.CheckConditionDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.CheckDeviceRequestBean;
import com.isoftston.issuser.conchapp.model.bean.CheckOneDeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceBean;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.CheckView;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.getDevices(token1,"1",getPageNo()+"")
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
    public void checkDevice(String content,String cityName){
        CheckDeviceRequestBean bean =new CheckDeviceRequestBean();

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(content);
        String all = matcher.replaceAll("");

        bean.equipId=content;
//        bean.userId=UserHelper.getUserId()+"";
//        bean.userId= String.valueOf(UserHelper.getUserId());

        bean.location=cityName;

        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.checkDevices(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceBean>>(view) {
                    @Override
                    public void success(BaseData<DeviceBean> deviceBeanBaseData) {
                        view.checkDeviceResult(deviceBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.checkDeviceResultError(e.getMessage());
                    }

                    @Override
                    public boolean operationError(BaseData<DeviceBean> deviceBeanBaseData, int status, String message) {
                        view.checkDeviceResultError(message);
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
    public void getAllDeviceInfo(String lastId){
        CheckAllDeviceBean checkAllDeviceBean=new CheckAllDeviceBean();
        checkAllDeviceBean.lastId=lastId;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.getAllDeviceInfo(token1,checkAllDeviceBean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceListBean>>(view) {
                    @Override
                    public void success(BaseData<DeviceListBean> deviceBeanBaseData) {
                        Log.i("zt",deviceBeanBaseData.data.list.size()+"");
                        view.CheckAllDeviceResult(deviceBeanBaseData.data.list,deviceBeanBaseData.data.total);
                    }

                    @Override
                    public boolean operationError(BaseData<DeviceListBean> deviceBeanBaseData, int status, String message) {
                        if (status==-200){
                            view.reLogin();
                        }
                        return false;
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
                        view.CheckAllDeviceResult(deviceBeanBaseData.data.list,deviceBeanBaseData.data.total);
                    }

                });

    }
    //查询一条设备信息
    public void getOneDeviceInfo(String content){
        CheckOneDeviceBean bean=new CheckOneDeviceBean();
        bean.equipId=content;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.getOneDeviceInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<DeviceBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceBean>>(view) {
                    @Override
                    public void success(BaseData<DeviceBean> deviceBeanBaseData) {
                        view.checkDeviceResult(deviceBeanBaseData.data);
                    }

                    @Override
                    public boolean operationError(BaseData<DeviceBean> deviceBeanBaseData, int status, String message) {
                        if (status==-200){
                            view.reLogin();
                        }
                        return false;
                    }

                });
    }

    public void getUserInfo(){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.e("yzh","token--"+token);
        String token1=token.replaceAll("\"","");
        UserBean bean=new UserBean();
        api.getUserInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>(view) {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        Log.i("yzh","saveUser--"+userInfoBeanBaseData);
                        view.setUserInfo(userInfoBeanBaseData.data);
                    }

                    @Override
                    public boolean operationError(BaseData<UserInfoBean> userInfoBeanBaseData, int status, String message) {
                        if (status==-200){
                            view.reLogin();
                        }
                        return false;
                    }

                });
    }

    public void getDescription(Map<String,String> descId){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.e("yzh","token--"+token);
        String token1=token.replaceAll("\"","");
        api.getDeviceDescription(token1,descId)
                .compose(new ResponseTransformer<>(this.<BaseData<String>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<String>>(view) {
                    @Override
                    public void success(BaseData<String> description) {
                        Log.i("yzh","description--"+description);
                        view.setDescription(description.data);
                    }

                    @Override
                    public boolean operationError(BaseData<String> description, int status, String message) {
                        if (status==-200){
                            view.reLogin();
                        }
                        return false;
                    }

                });
    }
}
