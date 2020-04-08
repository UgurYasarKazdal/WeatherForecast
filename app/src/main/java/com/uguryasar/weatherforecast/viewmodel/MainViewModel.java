package com.uguryasar.weatherforecast.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.uguryasar.weatherforecast.model.CityList;
import com.uguryasar.weatherforecast.repository.MainRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

public final class MainViewModel extends ViewModel {
    private final MutableLiveData _weatherByLocationData = new MutableLiveData();
    ;;
    private final MutableLiveData<List<CityList>> _cityList = new MutableLiveData<>();
    ;

    private final MainRepositoryImpl repo;

    public final LiveData weatherByLocationData() {
        return this._weatherByLocationData;
    }


    public final LiveData<List<CityList>> cityList() {
        return this._cityList;
    }


    public final void GetWeatherByLocation(Double lat, Double lon) {
        this.repo.GetWeatherByLocation(lat, lon);
    }

    public final void GetCityList() {
        this.repo.GetICityList();
    }


    @Inject
    public MainViewModel(Activity activity, MainRepositoryImpl repo) {
        super();
        this.repo = repo;

        repo.weatherByLocationData().observe((LifecycleOwner) activity, _weatherByLocationData::postValue);

        repo.cityList().observe((LifecycleOwner) activity, (Observer) var1 -> _cityList.postValue((List<CityList>) var1));

    }
}