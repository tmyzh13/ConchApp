package com.isoftston.issuser.conchapp.presenter;

import com.corelibs.api.ApiFactory;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.model.apis.ConchNewsApi;
import com.isoftston.issuser.conchapp.views.interfaces.ConchNewsView;

/**
 * Created by issuser on 2018/4/17.
 */

public class ConchNewsPresenter extends BasePresenter<ConchNewsView> {

    private ConchNewsApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api= ApiFactory.getFactory().create(ConchNewsApi.class);
    }
    @Override
    public void onStart() {

    }
}
