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
    private WeatherRestApiInterface mWeatherRestApiInterface;
    private CityListRestApiInterface mWityListRestApiInterface;
    private final MutableLiveData _weatherByLocationData = new MutableLiveData();
    private final MutableLiveData<List<CityList>> _cityList = new MutableLiveData();

    public final LiveData weatherByLocationData() {
        return this._weatherByLocationData;
    }

    public final LiveData<List<CityList>> cityList() {
        return this._cityList;
    }

    public MainRepositoryImpl(@Named("WeatherApi") WeatherRestApiInterface _weatherRestApiInterface, @Named("CityListApi") CityListRestApiInterface _cityListRestApiInterface) {
        this.mWeatherRestApiInterface = _weatherRestApiInterface;
        this.mWityListRestApiInterface = _cityListRestApiInterface;
    }

    @Override
    public void GetWeatherByLocation(Double lat, Double lon) {
        NetworkRequest.performAsyncRequest(mWeatherRestApiInterface.GetWeatherByLocation(lat, lon, Constants.WEATHER_API_KEY),
                (data) -> {
                    _weatherByLocationData.postValue(data);
                }, (error) -> {
                    // Handle Error
                    Log.e("asd", "addPatient Error: " + error.toString());
                });
    }


    @Override
    public void GetICityList() {
        NetworkRequest.performAsyncRequest(mWityListRestApiInterface.GetCities(),
                (data) -> {
                    _cityList.postValue(data);
                }, (error) -> {
                    // Handle Error
                    Log.e("asd", "addPatient Error: " + error.toString());
                });
    }
}
