package com.uguryasar.weatherforecast.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uguryasar.weatherforecast.network.rest.CityListRestApiInterface;
import com.uguryasar.weatherforecast.util.Constants;
import com.uguryasar.weatherforecast.network.rest.WeatherRestApiInterface;
import com.uguryasar.weatherforecast.model.CityList;
import com.uguryasar.weatherforecast.network.rest.NetworkRequest;

import java.util.List;

import javax.inject.Named;

public class MainRepositoryImpl implements MainRepository {
    WeatherRestApiInterface weatherRestApiInterface;
    CityListRestApiInterface cityListRestApiInterface;
    private final MutableLiveData _weatherByLocationData = new MutableLiveData();
    private final MutableLiveData<List<CityList>> _cityList = new MutableLiveData();

    public final LiveData weatherByLocationData() {
        return this._weatherByLocationData;
    }

    public final LiveData<List<CityList>> cityList() {
        return this._cityList;
    }

    public MainRepositoryImpl(@Named("WeatherApi") WeatherRestApiInterface weatherRestApiInterface, @Named("CityListApi") CityListRestApiInterface cityListRestApiInterface) {
        this.weatherRestApiInterface = weatherRestApiInterface;
        this.cityListRestApiInterface = cityListRestApiInterface;
    }

    @Override
    public void GetWeatherByLocation(Double lat, Double lon) {
        NetworkRequest.performAsyncRequest(weatherRestApiInterface.GetWeatherByLocation(lat, lon, Constants.API_KEY),
                (data) -> {
                    _weatherByLocationData.postValue(data);
                }, (error) -> {
                    // Handle Error
                    Log.e("asd", "addPatient Error: " + error.toString());
                });
    }


    @Override
    public void GetICityList() {
        NetworkRequest.performAsyncRequest(cityListRestApiInterface.GetCities(),
                (data) -> {
                    _cityList.postValue(data);
                }, (error) -> {
                    // Handle Error
                    Log.e("asd", "addPatient Error: " + error.toString());
                });
    }
}
