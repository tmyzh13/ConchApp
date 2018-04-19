package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.WorkBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/18.
 */

public interface WorkView extends BasePaginationView {
    void renderData(WorkBean workBean);

    void getWorkListInfo(List<WorkBean> list);

    void getWorkError();

}
