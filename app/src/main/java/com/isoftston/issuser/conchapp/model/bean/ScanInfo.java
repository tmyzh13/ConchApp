package com.isoftston.issuser.conchapp.model.bean;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/16.
 */

public class ScanInfo implements Serializable {
    private String scanTime;//上次扫码时间
    private String address;//地址

    public ScanInfo(String scanTime, String address) {
        this.scanTime = scanTime;
        this.address = address;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
