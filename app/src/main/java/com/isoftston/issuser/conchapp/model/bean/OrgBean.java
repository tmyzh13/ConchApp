package com.isoftston.issuser.conchapp.model.bean;

import android.util.Log;

import com.isoftston.issuser.conchapp.utils.Node;

/**
 * Created by issuser on 2018/4/26.
 */

public class OrgBean extends Node<String> {

    private static final String TAG = OrgBean.class.getSimpleName();

    /**
     * 此处返回节点ID
     * @return
     */
    @Override
    public String get_id() {
        return ID_;
    }

    /**
     * 此处返回父亲节点ID
     * @return
     */
    @Override
    public String get_parentId() {
        return PARENT_ID_;
    }

    @Override
    public String get_label() {
        return ORG_NAME_;
    }

    @Override
    public boolean parent(Node dest) {
        if(ID_ == null)
        {
            return false;
        }

        if (ID_ .equals(dest.get_parentId())){
            return true;
        }
        return false;
    }

    @Override
    public boolean child(Node dest) {

        if(PARENT_ID_ == null)
        {
            return  false;
        }

        if (PARENT_ID_.equals(dest.get_id())){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasChild() {
        return HASCHILD.equals("1");
    }

    private String ID_;

    public String getPARENT_ID_() {
        return PARENT_ID_;
    }

    public void setPARENT_ID_(String PARENT_ID_) {
        this.PARENT_ID_ = PARENT_ID_;
    }

    private String PARENT_ID_;
    private String ORG_NAME_;
    private String ISCOMPANY_;
    private String HASCHILD = "1";

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public String getORG_NAME_() {
        return ORG_NAME_;
    }

    public void setORG_NAME_(String ORG_NAME_) {
        this.ORG_NAME_ = ORG_NAME_;
    }


    public String getISCOMPANY_() {
        return ISCOMPANY_;
    }

    public void setISCOMPANY_(String ISCOMPANY_) {
        this.ISCOMPANY_ = ISCOMPANY_;
    }

    public String getHASCHILD() {
        return HASCHILD;
    }

    public void setHASCHILD(String HASCHILD) {
        this.HASCHILD = HASCHILD;
    }
}
