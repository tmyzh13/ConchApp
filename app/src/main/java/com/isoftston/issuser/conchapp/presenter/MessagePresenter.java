package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.ListPagePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.apis.MessageApi;
import com.isoftston.issuser.conchapp.model.bean.AirResponseBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.MessageUnReadBean;
import com.isoftston.issuser.conchapp.model.bean.MessageUnreadGetBean;
import com.isoftston.issuser.conchapp.model.bean.QueryMessageRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UpdateZgtpBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.WeatherResponseBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.MessageView;

/**
 * Created by issuser on 2018/4/20.
 */

public class MessagePresenter extends ListPagePresenter<MessageView> {
    private MessageApi api;

    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(MessageApi.class);
    }
    public void getMessageListInfo(String type, final String lastId){
        MessageListRequestBean bean=new MessageListRequestBean();
        bean.type=type;
        bean.lastId=lastId;
        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        api.getMessageListInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MessageListInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<MessageListInfoBean>>(view) {

                    @Override
                    public void success(BaseData<MessageListInfoBean> messageBaseData) {
                        if (messageBaseData.data.list.size() == 0 && "".equals(lastId)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getMessageListResult(messageBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<MessageListInfoBean> messageListInfoBeanBaseData, int status, String message) {
                        if (status==-200){
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }
                });

    }
    public void getMessageDetailInfo(String type,String id){
        MessageDetailRequestBean bean=new MessageDetailRequestBean();
        bean.type=type;
        bean.id=id;
        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token","--getMessageDetailInfo"+token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        api.getMessageDetailInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MessageDetailBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<MessageDetailBean>>(view) {

                    @Override
                    public void success(BaseData<MessageDetailBean> messageDetailBeanBaseData) {
                            view.getMessageDetailResult(messageDetailBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
    public void queryMessageInfo(String language,String type){
        QueryMessageRequestBean bean=new QueryMessageRequestBean();
        bean.language=language;
        bean.yhlx=type;
        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        api.queryMessageInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MessageQueryBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<MessageQueryBean>>(view) {

                    @Override
                    public void success(BaseData<MessageQueryBean> messageDetailBeanBaseData) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getUserInfo(){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.e("---getUserInfo--yzh","token--"+token);
        String token1=token.replaceAll("\"","");
        UserBean bean=new UserBean();
        api.getUserInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>() {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        PreferencesHelper.saveData(Constant.ORG_ID,userInfoBeanBaseData.data.getOrgId());
                        PreferencesHelper.saveData(Constant.ORG_NAME,userInfoBeanBaseData.data.getOrgName());
                    }

                });
    }

    public void getEachMessageListInfo(String type, final String lastId) {
        MessageListRequestBean bean=new MessageListRequestBean();
        bean.type=type;
        bean.lastId=lastId;
        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        view.showLoading();
        api.getEachMessageListInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<EachMessageInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<EachMessageInfoBean>>(view) {

                    @Override
                    public void success(BaseData<EachMessageInfoBean> messageBaseData) {
                        if (messageBaseData.data.list.size() == 0 && "".equals(lastId)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.hideLoading();
                        view.getEachMessageListResult(messageBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.hideLoading();
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<EachMessageInfoBean> messageListInfoBeanBaseData, int status, String message) {
                        view.hideLoading();
                        if (status==-200){
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }

                });
    }

    public void updateZgtp(UpdateZgtpBean updateZgtpBean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.updateZgtp(token1,updateZgtpBean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {

                    @Override
                    public void success(BaseData messageDetailBeanBaseData) {
                        view.updateSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.updateFailed();
                    }
                });
    }

    public void getWeatherInfo(String cityCode) {

        api.getWeatherInfo(cityCode)
                .compose(new ResponseTransformer<>(this.<WeatherResponseBean>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<WeatherResponseBean>() {

                    @Override
                    public void success(WeatherResponseBean messageBaseData) {
                        view.refreshWeather(messageBaseData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                        ToastMgr.show(getString(R.string.weather_fail));
                    }
                });
    }

    public void getAirInfo(String cityEnName) {

        api.getAirInfo(cityEnName)
                .compose(new ResponseTransformer<>(this.<AirResponseBean>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<AirResponseBean>() {

                    @Override
                    public void success(AirResponseBean messageBaseData) {
                        view.refreshAir(messageBaseData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastMgr.show(getString(R.string.weather_quality_fail));
                    }
                });
    }

    public void getUnReadMessageList(MessageUnReadBean bean) {

        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        view.showLoading();
        api.getUnReadMessageList(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MessageUnreadGetBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<MessageUnreadGetBean>>(view) {

                    @Override
                    public void success(BaseData<MessageUnreadGetBean> messageBaseData) {
                        view.hideLoading();
                        view.getUnreadMessageListResult(messageBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.hideLoading();
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<MessageUnreadGetBean> messageListInfoBeanBaseData, int status, String message) {
                        view.hideLoading();
                        if (status==-200){
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }

                });
    }
}
