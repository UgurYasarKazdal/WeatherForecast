package com.uguryasar.weatherforecast.network.rest;


import androidx.annotation.NonNull;

import com.uguryasar.weatherforecast.model.CityList;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface CityListRestApiInterface {

        @NonNull
        @GET("weeq/3fdc85b0a81cc3a1ed6f019cb3c3abc7/raw/20bc117285562d35ccd2d81317432c03c50d009b/Turkiye-Il-Ilce.json")
        Observable<List<CityList>> GetCities();

    }