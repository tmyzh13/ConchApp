package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.SecurityApi;
import com.isoftston.issuser.conchapp.model.bean.AddYHBean;
import com.isoftston.issuser.conchapp.model.bean.AddWZMessageRequestBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by issuser on 2018/4/20.
 */

public class SecurityPresenter extends BasePresenter<SecuryView> {
    private SecurityApi api;
    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(SecurityApi.class);
    }
    public void addWZMessage(String language,AddYHBean bean){
        AddWZMessageRequestBean addWZMessageRequestBean=new AddWZMessageRequestBean();
        List<AddYHBean> list=new ArrayList<>();
        list.add(bean);
        addWZMessageRequestBean.language=language;
        addWZMessageRequestBean.list=list;
        api.addWZMessage(addWZMessageRequestBean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {

                    @Override
                    public void success(BaseData messageDetailBeanBaseData) {
                        view.addSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.addFailed();
                    }
                });
    }
    public void addYHMessage(String language,AddYHBean bean){
        AddWZMessageRequestBean addWZMessageRequestBean=new AddWZMessageRequestBean();
        List<AddYHBean> list=new ArrayList<>();
        list.add(bean);
        addWZMessageRequestBean.language=language;
        addWZMessageRequestBean.list=list;
        api.addYHMessage(addWZMessageRequestBean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {

                    @Override
                    public void success(BaseData messageDetailBeanBaseData) {
                        view.addSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.addFailed();
                    }
                });
    }
    public void getSafeMessageList(String userid,String type,String item,String lastid){
        SafeRequestBean bean=new SafeRequestBean();
        bean.userId=userid;
        bean.item=item;
        bean.type=type;
        bean.lastId=lastid;
        api.getSefeMessageList(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<SafeListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<SafeListBean>>() {

                    @Override
                    public void success(BaseData<SafeListBean> messageDetailBeanBaseData) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
