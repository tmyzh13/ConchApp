package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.pagination.presenter.PagePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.UserApi;
import com.isoftston.issuser.conchapp.model.bean.AddFeedBackRequestBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.model.bean.UserRequestBean;
import com.isoftston.issuser.conchapp.views.interfaces.UserView;

/**
 * Created by issuser on 2018/4/17.
 */

public class UserPresenter extends PagePresenter<UserView> {
    private UserApi api;
    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(UserApi.class);
    }

    /**
     * @param language
     * @param userId
     */
   public void getUserInfo(String language,String userId){
       UserRequestBean bean=new UserRequestBean();
       bean.language=language;
       bean.userId=userId;
        api.getUserInfo(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>() {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        view.getUserInfo(userInfoBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getUserInfoError();
                    }

                    @Override
                    public boolean operationError(BaseData<UserInfoBean> userInfoBeanBaseData, int status, String message) {
                        view.getUserInfoError();
                        return super.operationError(userInfoBeanBaseData, status, message);
                    }
                });
    }
    public void  addFeedBack(String content){
        AddFeedBackRequestBean bean=new AddFeedBackRequestBean();
        bean.content=content;
        api.addFeedBack(bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData userInfoBeanBaseData) {
                        view.addFeedBackSuccess();
                    }


                });

    }
}
