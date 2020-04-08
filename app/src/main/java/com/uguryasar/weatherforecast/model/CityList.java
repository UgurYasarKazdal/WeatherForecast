package com.uguryasar.weatherforecast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList {

@SerializedName("il")
@Expose
private String il;
@SerializedName("plaka")
@Expose
private Integer plaka;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    private String temp;

public String getIl() {
return il;
}

public void setIl(String il) {
this.il = il;
}

public Integer getPlaka() {
return plaka;
}

public void setPlaka(Integer plaka) {
this.plaka = plaka;
}

}