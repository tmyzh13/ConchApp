package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.apis.CompanyApi;
import com.isoftston.issuser.conchapp.model.apis.SecurityApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.CompanyBean;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.CompanySelectView;

import java.util.List;

public class CompanySelectPresenter extends BasePresenter<CompanySelectView> {

    private CompanyApi api;

    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(CompanyApi.class);
    }

    public void getCompanyChoiceList(final String id){
        CompanyBean bean =new CompanyBean();
        bean.setId(id);
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.findCompanyList(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<List<CompanyBean>>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<List<CompanyBean>>>(view) {

                    @Override
                    public void success(BaseData<List<CompanyBean>> messageBeanBaseData) {
                        if (messageBeanBaseData.data.size() > 0){
                            view.getOrgList(messageBeanBaseData.data);
                        }else {
                            ToastMgr.show(getString(R.string.no_company));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void updateUserCompany(UserInfoBean bean) {
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.updateUserCompany(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {

                    @Override
                    public void success(BaseData message) {
                        if (message.code == 1){
                            ToastMgr.show(R.string.submit_success);
                            view.submit();
                        }else{
                            ToastMgr.show(R.string.submit_fail);
                            view.submit();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.submit();
                    }
                });
    }
}
