package com.uguryasar.weatherforecast.network.rest;


import androidx.annotation.NonNull;

import com.uguryasar.weatherforecast.model.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherRestApiInterface {

    @NonNull
    @GET("weather")
    Observable<WeatherResponse> GetWeatherByLocation(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String apiKey);

    @NonNull
    @GET("weather")
    Observable<WeatherResponse> GetWeatherByCityName(@Query("q") String cityName, @Query("appid") String apiKey);

}