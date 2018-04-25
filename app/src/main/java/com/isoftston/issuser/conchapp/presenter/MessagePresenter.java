package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.ListPagePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.conchapp.constants.Constant;
import com.isoftston.issuser.conchapp.model.apis.MessageApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.QueryMessageRequestBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.utils.ToastUtils;
import com.isoftston.issuser.conchapp.views.interfaces.MessageView;

import java.util.List;

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
    public void getMessageListInfo(String type,String lastId){
        MessageListRequestBean bean=new MessageListRequestBean();
        bean.type=type;
        bean.lastId=lastId;
        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        api.getMessageListInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<MessageListInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<MessageListInfoBean>>() {

                    @Override
                    public void success(BaseData<MessageListInfoBean> messageBaseData) {
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
                .subscribe(new ResponseSubscriber<BaseData<MessageDetailBean>>() {

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
                .subscribe(new ResponseSubscriber<BaseData<MessageQueryBean>>() {

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
                        PreferencesHelper.saveData(Constant.ORG_NAME,userInfoBeanBaseData.data.getOrgName());
                    }

                });
    }
}
