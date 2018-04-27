package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.SearchUserApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListRequestBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.SeacherView;

/**
 * Created by issuser on 2018/4/10.
 */

public class SeacherPresenter extends BasePresenter<SeacherView> {
    private SearchUserApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(SearchUserApi.class);
    }

    @Override
    public void onStart() {

    }

    public void searchUserAction(SearchUserBean userBean) {
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        String token1=token.replaceAll("\"","");
        api.searchUser(token1,userBean)
                .compose(new ResponseTransformer<BaseData<ResponseUserBean>>(this.<BaseData<ResponseUserBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<ResponseUserBean>>() {
                    @Override
                    public void success(BaseData<ResponseUserBean> checkPeopleBeanBaseData) {
                        view.searchSuccess(checkPeopleBeanBaseData.data.list);
                    }
                });
    }

    public void searchMessage(String searchType, String key) {
        MessageQueryBean bean=new MessageQueryBean();
        bean.setType(searchType);
        bean.setTitle(key);
        String token=SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1=token.replaceAll("\"","");
        api.searchMessageListInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<EachMessageInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<EachMessageInfoBean>>() {

                    @Override
                    public void success(BaseData<EachMessageInfoBean> messageBaseData) {
                        view.getEachMessageListResult(messageBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<EachMessageInfoBean> messageListInfoBeanBaseData, int status, String message) {
                        if (status==-200){
                            view.reLogin();
                        }
                        return false;
                    }

                });
    }
}
