package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.conchapp.model.bean.CheckPeopleBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/10.
 */

public interface SeacherView extends BaseView{
    void searchSuccess(List<CheckPeopleBean> list);

    void getEachMessageListResult(EachMessageInfoBean data);

    void getEachMessageListResult(Object data,String type);

    void getWorkError();

    void reLogin();
}
