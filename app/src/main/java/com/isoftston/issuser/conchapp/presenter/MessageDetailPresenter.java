package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.isoftston.issuser.conchapp.model.apis.MessageDetailApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.views.interfaces.MessageDetailView;

/**
 * Created by issuser on 2018/4/18.
 */

public class MessageDetailPresenter extends BasePresenter<MessageDetailView> {
    private MessageDetailApi api;
    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(MessageDetailApi.class);
    }
    public void getMessageDetailInfo() {
        api.getMessageDetailInfo("ch","","","")
                .compose(new ResponseTransformer<>(this.<BaseData<MessageDetailBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<MessageDetailBean>>() {

                    @Override
                    public void success(BaseData<MessageDetailBean> messageDetailBeanBaseData) {
                        view.getWorkInfo(messageDetailBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }
                });


    }
}
