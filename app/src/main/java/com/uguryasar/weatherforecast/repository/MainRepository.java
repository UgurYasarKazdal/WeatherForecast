package com.uguryasar.weatherforecast.repository;

interface MainRepository {
    void GetWeatherByLocation(Double lat, Double lon);

    void GetICityList();
}
