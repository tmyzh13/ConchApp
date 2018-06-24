package com.isoftston.issuser.conchapp.model.bean;

import java.util.List;

/**
 * Created by issuser on 2018/5/22.
 */

public class WorkCountBean {
    private int total;
    private int type;
    private List<CountBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CountBean> getList() {
        return list;
    }

    public void setList(List<CountBean> list) {
        this.list = list;
    }


}
