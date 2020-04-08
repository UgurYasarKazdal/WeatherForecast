package com.uguryasar.weatherforecast.repository;

import rx.Subscription;

public interface MainRepository {
   void GetWeatherByLocation(Double lat, Double lon);
   void GetICityList();
}
