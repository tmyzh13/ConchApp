package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.MessageListInfoBean;

/**
 * Created by issuser on 2018/4/20.
 */

public interface MessageView extends BasePaginationView {

    void getMessageDetailResult(MessageDetailBean bean);
    void  getMessageListResult(MessageListInfoBean data);
    void getWorkError();
    void  reLogin();
}
