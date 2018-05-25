package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BaseView;
import com.isoftston.issuser.conchapp.model.bean.CompanyBean;

import java.util.List;

public interface CompanySelectView extends BaseView {


    void getOrgList(List<CompanyBean> bean);

    void submit();
}
