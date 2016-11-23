package com.example.administrator.weather.model;

/**
 * Created by Administrator on 2016/8/18.
 */
public class Country {
    private int id;
    private int cityId;
    private String countryName;
    private String countryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCoutryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCoutryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
