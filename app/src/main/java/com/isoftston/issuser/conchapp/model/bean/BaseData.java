package com.isoftston.issuser.conchapp.model.bean;

import com.corelibs.subscriber.ResponseHandler;
import com.isoftston.issuser.conchapp.model.bean.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by issuser on 2018/4/9.
 */

public class BaseData<T> implements Serializable,ResponseHandler.IBaseData {

    public int code;
    public String mess;
    public T data;
    public Page page;

    @Override
    public boolean isSuccess() {
        return code==1;
    }



    @Override
    public int status() {
        return code;
    }

    @Override
    public Object data() {
        return data;
    }

    @Override
    public String msg() {
        return mess;
    }
}
