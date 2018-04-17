package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.WorkDetailBean;

/**
 * Created by issuser on 2018/4/17.
 */

public interface WorkDetailView extends BasePaginationView {
    void renderData(WorkDetailBean workDetailBean);

    void getWorkDetailInfo(WorkDetailBean workDetailBean);

    void getWorkDetailError();
}
