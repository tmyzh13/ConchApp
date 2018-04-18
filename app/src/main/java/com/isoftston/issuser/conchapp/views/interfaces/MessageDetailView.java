package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.MessageDetailBean;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;

/**
 * Created by issuser on 2018/4/18.
 */

public interface MessageDetailView extends BasePaginationView {
//    void renderData(MessageDetailBean messageDetailBean);

    void getWorkInfo(MessageDetailBean messageDetailBean);

    void getWorkError();

}
