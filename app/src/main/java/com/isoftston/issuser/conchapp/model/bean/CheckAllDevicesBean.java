package com.isoftston.issuser.conchapp.model.bean;

import java.util.List;

/**
 * Created by issuser on 2018/4/19.
 */

public class CheckAllDevicesBean {
    private boolean ok;
    private int code;
    private String mess;
    private List data;
    public void setOk(boolean ok) {
        this.ok = ok;
    }
    public boolean getOk() {
        return ok;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
    public String getMess() {
        return mess;
    }

    public void setData(List data) {
        this.data = data;
    }
    public List getData() {
        return data;
    }

//    private class List {
//        private List<CheckBean> list;
//        public void setList(List<CheckBean> list) {
//            this.list = list;
//        }
//        public List<CheckBean> getList() {
//            return list;
//        }
//    }

    @Override
    public String toString() {
        return "CheckAllDevicesBean{" +
                "ok=" + ok +
                ", code=" + code +
                ", mess='" + mess + '\'' +
                ", data=" + data +
                '}';
    }
}
