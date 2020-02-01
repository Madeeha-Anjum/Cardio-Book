package com.example.manjum_cardiobook;

import androidx.annotation.NonNull;

public class City {
    private String cityName;
    private String provinceName;

    public City() {
        // VERY IMPORTANT TO HAVE TO USE "TOOBJECT()" method
    }

    public City(String cityName, String provinceName) {
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @NonNull
    @Override
    public String toString() {
        return "City: " + cityName + " Province: " + provinceName;
    }
}
