package com.isoftston.issuser.conchapp.model.bean;

public class CountryModelBean {

    private String id;

    private String CityCode;

    private String EnglishName;

    private String ReadmeName;

    private String ChineseName;

    private String Country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getReadmeName() {
        return ReadmeName;
    }

    public void setReadmeName(String readmeName) {
        ReadmeName = readmeName;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public void setChineseName(String chineseName) {
        ChineseName = chineseName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
