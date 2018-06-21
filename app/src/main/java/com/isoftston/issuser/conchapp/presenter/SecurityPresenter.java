package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.apis.SecurityApi;
import com.isoftston.issuser.conchapp.model.bean.AddYHBean;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.OrgBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestOrgBean;
import com.isoftston.issuser.conchapp.model.bean.SecuritySearchBean;
import com.isoftston.issuser.conchapp.model.bean.UpdateZgtpBean;
import com.isoftston.issuser.conchapp.model.bean.UserBean;
import com.isoftston.issuser.conchapp.model.bean.UserInfoBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.SecuryView;

import java.io.File;
import java.util.List;

/**
 * Created by issuser on 2018/4/20.
 */

public class SecurityPresenter extends BasePresenter<SecuryView> {

    private final String TAG = SecurityPresenter.class.getSimpleName();

    private SecurityApi api;
    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(SecurityApi.class);
    }
    public void addWZMessage(AddYHBean bean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.addWZMessage(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {

                    @Override
                    public void success(BaseData messageDetailBeanBaseData) {
                        view.addSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.addFailed();
                        super.onError(e);

                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        if (status==0){
                            view.addFailed();
                        }
                        return super.operationError(baseData, status, message);
                    }
                });
    }
    public void addYHMessage(AddYHBean bean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.addYHMessage(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {

                    @Override
                    public void success(BaseData messageDetailBeanBaseData) {
                        view.addSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.addFailed();
                    }
                });
    }
    public void getSafeMessageList(final String type, String item, final String lastId){
        SafeRequestBean bean=new SafeRequestBean();
        bean.item=item;
        bean.type=type;
        bean.lastId=lastId;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.getSefeMessageList(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<SafeListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<SafeListBean>>(view) {

                    @Override
                    public void success(BaseData<SafeListBean> messageBeanBaseData) {
                        view.getSafeListSuccess(messageBeanBaseData.data);
                        if (messageBeanBaseData.data.list.size() == 0 && "wz".equals(type)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getWorkError();
                        super.onError(e);
                    }

                    @Override
                    public boolean operationError(BaseData<SafeListBean> safeListBeanBaseData, int status, String message) {
                        view.getWorkError();
                        return false;
                    }
                });
    }
    public void uploadImg(List<File> listFiles){
        for (int i=0;i<listFiles.size();i++){
            File file=listFiles.get(i);
            String token= SharePrefsUtils.getValue(getContext(),"token",null);
            Log.i("token",token);
            String token1=token.replaceAll("\"","");
            api.uploadImage(token1,file)
                    .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                    .subscribe(new ResponseSubscriber<BaseData>() {

                        @Override
                        public void success(BaseData messageBeanBaseData) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                        }
                    });
        }
    }
    public void getCompanyChoiceList(){
        SafeRequestBean bean =new SafeRequestBean();
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.findCompanyList(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<SecuritySearchBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<SecuritySearchBean>>(view) {

                    @Override
                    public void success(BaseData<SecuritySearchBean> messageBeanBaseData) {
                        if (messageBeanBaseData.data() == null){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getSafeChoiceList(messageBeanBaseData.data);

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private String mainId = "";

    public void getCompanyChoiceNextList(String id){
        SafeRequestOrgBean bean =new SafeRequestOrgBean();
        bean.orgId = id;
        mainId = id;
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.findCompanyListNext(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<List<OrgBean>>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<List<OrgBean>>>(view) {

                    @Override
                    public void success(BaseData<List<OrgBean>> messageBeanBaseData) {
                        for(OrgBean bean:messageBeanBaseData.data)
                        {
                            bean.setPARENT_ID_(mainId);
                        }
                        view.getOrgList(messageBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    public void getUserInfo(){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.e("yzh","token--"+token);
        String token1=token.replaceAll("\"","");
        UserBean bean=new UserBean();
        api.getUserInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>() {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        Log.i("yzh","saveUser--"+userInfoBeanBaseData);
                        view.setUserInfo(userInfoBeanBaseData.data);
                    }
                });
    }

    public void checkUpload(UpdateZgtpBean updateZgtpBean){
        String token= SharePrefsUtils.getValue(getContext(),"token",null);
        Log.i("token",token);
        String token1=token.replaceAll("\"","");
        api.checkUpload(token1,updateZgtpBean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>(view) {

                    @Override
                    public void success(BaseData messageDetailBeanBaseData) {
                        if (messageDetailBeanBaseData.code == 1){
                            view.addSuccess();
                        }else {
                            ToastMgr.show(R.string.not_change_img);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
