package com.isoftston.issuser.conchapp.model.bean;

import com.isoftston.issuser.conchapp.utils.Node;

public class CompanyBean extends Node<String> {

    /**
     * id : 8a88fee258426ec50158427d54d4005a
     * address : null
     * bzjed : 0
     * code : 010309
     * description : null
     * isDisabled : null
     * fax : null
     * imgSrc : null
     * layer : 31
     * map : null
     * order : 0
     * orgName : 江苏区域管理委员会
     * qq : null
     * shops : null
     * shortName : null
     * tel : null
     * xsfw : null
     * parentId : 8a88fee2576b867e01576b8776780000
     * pCode : null
     * isCompany : 0
     * companyType : null
     * hasChild : 1
     */

    private String id;
    private Object address;
    private int bzjed;
    private String code;
    private Object description;
    private Object isDisabled;
    private Object fax;
    private Object imgSrc;
    private String layer;
    private Object map;
    private int order;
    private String orgName;
    private Object qq;
    private Object shops;
    private Object shortName;
    private Object tel;
    private Object xsfw;
    private String parentId;
    private Object pCode;
    private String isCompany;
    private Object companyType;
    private String hasChild;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public int getBzjed() {
        return bzjed;
    }

    public void setBzjed(int bzjed) {
        this.bzjed = bzjed;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Object isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public Object getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(Object imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Object getMap() {
        return map;
    }

    public void setMap(Object map) {
        this.map = map;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Object getQq() {
        return qq;
    }

    public void setQq(Object qq) {
        this.qq = qq;
    }

    public Object getShops() {
        return shops;
    }

    public void setShops(Object shops) {
        this.shops = shops;
    }

    public Object getShortName() {
        return shortName;
    }

    public void setShortName(Object shortName) {
        this.shortName = shortName;
    }

    public Object getTel() {
        return tel;
    }

    public void setTel(Object tel) {
        this.tel = tel;
    }

    public Object getXsfw() {
        return xsfw;
    }

    public void setXsfw(Object xsfw) {
        this.xsfw = xsfw;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Object getPCode() {
        return pCode;
    }

    public void setPCode(Object pCode) {
        this.pCode = pCode;
    }

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }

    public Object getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Object companyType) {
        this.companyType = companyType;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    @Override
    public String get_id() {
        return id;
    }

    @Override
    public String get_parentId() {
        return parentId;
    }

    @Override
    public String get_label() {
        return orgName;
    }

    @Override
    public boolean parent(Node dest) {
        if(id == null)
        {
            return false;
        }

        if (id .equals(dest.get_parentId())){
            return true;
        }
        return false;
    }

    @Override
    public boolean child(Node dest) {
        if(parentId == null)
        {
            return  false;
        }

        if (parentId.equals(dest.get_id())){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasChild() {
        return hasChild.equals("1");
    }
}
