package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;

/**
 * Created by issuser on 2018/4/18.
 */

public interface WorkView extends BasePaginationView {
    void renderData(WorkBean workBean);

    void getWorkInfo(WorkBean workBean);

    void getWorkError();

}
