package com.isoftston.issuser.conchapp.presenter;

import android.util.Log;

import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.apis.SearchUserApi;
import com.isoftston.issuser.conchapp.model.bean.BaseData;
import com.isoftston.issuser.conchapp.model.bean.DeviceListBean;
import com.isoftston.issuser.conchapp.model.bean.EachMessageInfoBean;
import com.isoftston.issuser.conchapp.model.bean.MessageQueryBean;
import com.isoftston.issuser.conchapp.model.bean.ResponseUserBean;
import com.isoftston.issuser.conchapp.model.bean.SafeListBean;
import com.isoftston.issuser.conchapp.model.bean.SafeRequestBean;
import com.isoftston.issuser.conchapp.model.bean.SearchUserBean;
import com.isoftston.issuser.conchapp.model.bean.WorkListsBean;
import com.isoftston.issuser.conchapp.model.bean.WorkTypeRequestBean;
import com.isoftston.issuser.conchapp.utils.SharePrefsUtils;
import com.isoftston.issuser.conchapp.views.interfaces.SeacherView;

/**
 * Created by issuser on 2018/4/10.
 */

public class SeacherPresenter extends BasePresenter<SeacherView> {
    private SearchUserApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(SearchUserApi.class);
    }

    @Override
    public void onStart() {

    }

    public void searchUserAction(SearchUserBean userBean) {
        String token = SharePrefsUtils.getValue(getContext(), "token", null);
        String token1 = token.replaceAll("\"", "");
        api.searchUser(token1, userBean)
                .compose(new ResponseTransformer<BaseData<ResponseUserBean>>(this.<BaseData<ResponseUserBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<ResponseUserBean>>(view) {
                    @Override
                    public void success(BaseData<ResponseUserBean> checkPeopleBeanBaseData) {
                        view.searchSuccess(checkPeopleBeanBaseData.data.list);
                    }
                });
    }

    public void searchMessage(String searchType, String key, final String lastId) {
        MessageQueryBean bean = new MessageQueryBean();
        bean.setType(searchType);
        bean.setTitle(key);
        bean.setLastId(lastId);
        String token = SharePrefsUtils.getValue(getContext(), "token", null);
        Log.i("token", token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1 = token.replaceAll("\"", "");
        api.searchMessageListInfo(token1, bean)
                .compose(new ResponseTransformer<>(this.<BaseData<EachMessageInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<EachMessageInfoBean>>(view) {

                    @Override
                    public void success(BaseData<EachMessageInfoBean> messageBaseData) {
                        if (messageBaseData.data.list.size() == 0 && "".equals(lastId)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getEachMessageListResult(messageBaseData.data, "0",lastId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<EachMessageInfoBean> messageListInfoBeanBaseData, int status, String message) {
                        if (status == -200) {
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }

                });
    }

    public void searchSafeMessage(String searchType, String key, final String lastId) {
        SafeRequestBean bean = new SafeRequestBean();
        bean.type = searchType;
        bean.condition = key;
        bean.lastId = lastId;
        String token = SharePrefsUtils.getValue(getContext(), "token", null);
        Log.i("token", token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1 = token.replaceAll("\"", "");
        api.searchSafeMessageListInfo(token1, bean)
                .compose(new ResponseTransformer<BaseData<SafeListBean>>(this.<BaseData<SafeListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<SafeListBean>>(view) {
                    @Override
                    public void success(BaseData<SafeListBean> safeListBeanBaseData) {
                        if (safeListBeanBaseData.data.list.size() == 0 && "".equals(lastId)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getEachMessageListResult(safeListBeanBaseData.data, "1",lastId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<SafeListBean> safeListBeanBaseData, int status, String message) {
                        if (status == -200) {
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }
                });
    }

    public void searchWorkMessage(int searchType, String key, final String lastId) {
        WorkTypeRequestBean bean = new WorkTypeRequestBean();
        bean.type = searchType;
        bean.keyWord = key;
        bean.lastId = lastId;
        String token = SharePrefsUtils.getValue(getContext(), "token", null);
        Log.i("token", token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1 = token.replaceAll("\"", "");
        api.searchWorkMessage(token1, bean)
                .compose(new ResponseTransformer<BaseData<WorkListsBean>>(this.<BaseData<WorkListsBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<WorkListsBean>>(view) {
                    @Override
                    public void success(BaseData<WorkListsBean> safeListBeanBaseData) {
                        if (safeListBeanBaseData.data.list.size() == 0 && "".equals(lastId)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getEachMessageListResult(safeListBeanBaseData.data, "2",lastId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<WorkListsBean> safeListBeanBaseData, int status, String message) {
                        if (status == -200) {
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }
                });
    }

    public void searchDeviceMessage(String key, final String lastId) {
        SafeRequestBean bean = new SafeRequestBean();
        bean.condition = key;
        bean.lastId = lastId;
        String token = SharePrefsUtils.getValue(getContext(), "token", null);
        Log.i("token", token);//94c29a2eaf903a1f4b3cc5996385dcd2
        String token1 = token.replaceAll("\"", "");
        api.searchDeviceMessage(token1, bean)
                .compose(new ResponseTransformer<BaseData<DeviceListBean>>(this.<BaseData<DeviceListBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<DeviceListBean>>(view) {
                    @Override
                    public void success(BaseData<DeviceListBean> safeListBeanBaseData) {
                        if (safeListBeanBaseData.data.list.size() == 0 && "".equals(lastId)){
                            ToastMgr.show(R.string.no_such_info);
                        }
                        view.getEachMessageListResult(safeListBeanBaseData.data, "3",lastId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getWorkError();
                    }

                    @Override
                    public boolean operationError(BaseData<DeviceListBean> safeListBeanBaseData, int status, String message) {
                        if (status == -200) {
                            view.reLogin();
                        }
                        view.getWorkError();
                        return false;
                    }
                });
    }
}
