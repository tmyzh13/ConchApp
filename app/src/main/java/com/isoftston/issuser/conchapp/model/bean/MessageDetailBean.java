package com.isoftston.issuser.conchapp.model.bean;

/**
 * Created by issuser on 2018/4/18.
 * 信息详情界面
 */

public class MessageDetailBean {
    private int id;//主键
    private String gsid;//公司Id
    private String gsmc;//公司名称
    private String jcdwid;//检查单位编号
    private String jcdwmc;//检查单位名称
    private String fxrmc;
    private String fxrid;
    private String sjdwid;
    private String sjdwmc;
    private String fxrq;
    private String yhly;
    private String cjdwid;
    private String cjdwmc;
    private String cjsj;
    private String cjrid;
    private String cjrmc;
    private String xgrid;
    private String xgrmc;
    private String xgsj;
    private String yhms;
    private String yhmc;
    private String yhlx;
    private String yhlbb;
    private String yhjb;
    private String knzchg;
    private String yhdd;
    private String yhbw;
    private String sfxczg;
    private String jlje;
    private String zgyjlx;
    private String zgyjms;
    private String yhbh;
    private String fbzt;
    private String yhzt;
    private String sprid;
    private String sprmc;
    private String spsj;
    private String spzt;
    private String ischoose;
    private String spjs;
    private String sjbmid;
    private String nm;
    private String tplj;
    private String ts;
    private String ispush;

/*"id":294,"fxrmc":"孙武","fxrCompany":"安全环保处","findByUnit":"吊车班","dangerSite":"北京","dangerPart":
        "新疆","dangerLevel":"ZDYH","dangerType":"TFKQZLBLQY","fxsj":"1525072920000","zgqx":null,"yhzgzt"
        :null,"yhly":"4","yhms":"太垃圾了，太垃圾了。","dangerPhoto":"upload/20180430/524e5101f5f142f98f2c61f709bac3f4.jpg," +
        "upload/20180430/e4922f8758634d20af9dd3a4d8391057.jpg," +
        "upload/20180430/0074c87ec19140b69731887cb2559020.jpg,","repairPhoto":null*/
    private String fxrCompany;
    private String findByUnit;
    private String dangerSite;
    private String dangerPart;

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public String getFxrCompany() {
        return fxrCompany;
    }

    public void setFxrCompany(String fxrCompany) {
        this.fxrCompany = fxrCompany;
    }

    public String getFindByUnit() {
        return findByUnit;
    }

    public void setFindByUnit(String findByUnit) {
        this.findByUnit = findByUnit;
    }

    public String getDangerSite() {
        return dangerSite;
    }

    public void setDangerSite(String dangerSite) {
        this.dangerSite = dangerSite;
    }

    public String getDangerPart() {
        return dangerPart;
    }

    public void setDangerPart(String dangerPart) {
        this.dangerPart = dangerPart;
    }

    public String getDangerType() {
        return dangerType;
    }

    public void setDangerType(String dangerType) {
        this.dangerType = dangerType;
    }

    public String getFxsj() {
        return fxsj;
    }

    public void setFxsj(String fxsj) {
        this.fxsj = fxsj;
    }

    public String getZgqx() {
        return zgqx;
    }

    public void setZgqx(String zgqx) {
        this.zgqx = zgqx;
    }

    public String getYhzgzt() {
        return yhzgzt;
    }

    public void setYhzgzt(String yhzgzt) {
        this.yhzgzt = yhzgzt;
    }

    public String getDangerPhoto() {
        return dangerPhoto;
    }

    public void setDangerPhoto(String dangerPhoto) {
        this.dangerPhoto = dangerPhoto;
    }

    public String getRepairPhoto() {
        return repairPhoto;
    }

    public void setRepairPhoto(String repairPhoto) {
        this.repairPhoto = repairPhoto;
    }

    private String dangerLevel;
    private String dangerType;
    private String fxsj;
    private String zgqx;
    private String yhzgzt;
    private String dangerPhoto;
    private String repairPhoto;



    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setGsid(String gsid) {
        this.gsid = gsid;
    }
    public String getGsid() {
        return gsid;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }
    public String getGsmc() {
        return gsmc;
    }

    public void setJcdwid(String jcdwid) {
        this.jcdwid = jcdwid;
    }
    public String getJcdwid() {
        return jcdwid;
    }

    public void setJcdwmc(String jcdwmc) {
        this.jcdwmc = jcdwmc;
    }
    public String getJcdwmc() {
        return jcdwmc;
    }

    public void setFxrmc(String fxrmc) {
        this.fxrmc = fxrmc;
    }
    public String getFxrmc() {
        return fxrmc;
    }

    public void setFxrid(String fxrid) {
        this.fxrid = fxrid;
    }
    public String getFxrid() {
        return fxrid;
    }

    public void setSjdwid(String sjdwid) {
        this.sjdwid = sjdwid;
    }
    public String getSjdwid() {
        return sjdwid;
    }

    public void setSjdwmc(String sjdwmc) {
        this.sjdwmc = sjdwmc;
    }
    public String getSjdwmc() {
        return sjdwmc;
    }

    public void setFxrq(String fxrq) {
        this.fxrq = fxrq;
    }
    public String getFxrq() {
        return fxrq;
    }

    public void setYhly(String yhly) {
        this.yhly = yhly;
    }
    public String getYhly() {
        return yhly;
    }

    public void setCjdwid(String cjdwid) {
        this.cjdwid = cjdwid;
    }
    public String getCjdwid() {
        return cjdwid;
    }

    public void setCjdwmc(String cjdwmc) {
        this.cjdwmc = cjdwmc;
    }
    public String getCjdwmc() {
        return cjdwmc;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }
    public String getCjsj() {
        return cjsj;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }
    public String getCjrid() {
        return cjrid;
    }

    public void setCjrmc(String cjrmc) {
        this.cjrmc = cjrmc;
    }
    public String getCjrmc() {
        return cjrmc;
    }

    public void setXgrid(String xgrid) {
        this.xgrid = xgrid;
    }
    public String getXgrid() {
        return xgrid;
    }

    public void setXgrmc(String xgrmc) {
        this.xgrmc = xgrmc;
    }
    public String getXgrmc() {
        return xgrmc;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }
    public String getXgsj() {
        return xgsj;
    }

    public void setYhms(String yhms) {
        this.yhms = yhms;
    }
    public String getYhms() {
        return yhms;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }
    public String getYhmc() {
        return yhmc;
    }

    public void setYhlx(String yhlx) {
        this.yhlx = yhlx;
    }
    public String getYhlx() {
        return yhlx;
    }

    public void setYhlbb(String yhlbb) {
        this.yhlbb = yhlbb;
    }
    public String getYhlbb() {
        return yhlbb;
    }

    public void setYhjb(String yhjb) {
        this.yhjb = yhjb;
    }
    public String getYhjb() {
        return yhjb;
    }

    public void setKnzchg(String knzchg) {
        this.knzchg = knzchg;
    }
    public String getKnzchg() {
        return knzchg;
    }

    public void setYhdd(String yhdd) {
        this.yhdd = yhdd;
    }
    public String getYhdd() {
        return yhdd;
    }

    public void setYhbw(String yhbw) {
        this.yhbw = yhbw;
    }
    public String getYhbw() {
        return yhbw;
    }

    public void setSfxczg(String sfxczg) {
        this.sfxczg = sfxczg;
    }
    public String getSfxczg() {
        return sfxczg;
    }

    public void setJlje(String jlje) {
        this.jlje = jlje;
    }
    public String getJlje() {
        return jlje;
    }

    public void setZgyjlx(String zgyjlx) {
        this.zgyjlx = zgyjlx;
    }
    public String getZgyjlx() {
        return zgyjlx;
    }

    public void setZgyjms(String zgyjms) {
        this.zgyjms = zgyjms;
    }
    public String getZgyjms() {
        return zgyjms;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }
    public String getYhbh() {
        return yhbh;
    }

    public void setFbzt(String fbzt) {
        this.fbzt = fbzt;
    }
    public String getFbzt() {
        return fbzt;
    }

    public void setYhzt(String yhzt) {
        this.yhzt = yhzt;
    }
    public String getYhzt() {
        return yhzt;
    }

    public void setSprid(String sprid) {
        this.sprid = sprid;
    }
    public String getSprid() {
        return sprid;
    }

    public void setSprmc(String sprmc) {
        this.sprmc = sprmc;
    }
    public String getSprmc() {
        return sprmc;
    }

    public void setSpsj(String spsj) {
        this.spsj = spsj;
    }
    public String getSpsj() {
        return spsj;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }
    public String getSpzt() {
        return spzt;
    }

    public void setIschoose(String ischoose) {
        this.ischoose = ischoose;
    }
    public String getIschoose() {
        return ischoose;
    }

    public void setSpjs(String spjs) {
        this.spjs = spjs;
    }
    public String getSpjs() {
        return spjs;
    }

    public void setSjbmid(String sjbmid) {
        this.sjbmid = sjbmid;
    }
    public String getSjbmid() {
        return sjbmid;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }
    public String getNm() {
        return nm;
    }

    public void setTplj(String tplj) {
        this.tplj = tplj;
    }
    public String getTplj() {
        return tplj;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
    public String getTs() {
        return ts;
    }

    public void setIspush(String ispush) {
        this.ispush = ispush;
    }
    public String getIspush() {
        return ispush;
    }
}
