package com.isoftston.issuser.conchapp.views.interfaces;

import com.corelibs.base.BasePaginationView;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/20.
 */

public interface SecuryView extends BasePaginationView {

    void addSuccess();

    void getSafeListSuccess(SafeListBean data);

    void addFailed();

    void getSafeChoiceList(SecuritySearchBean bean);

    void getOrgList(List<OrgBean> bean);

	  void getOrgId(String orgId);
}
