package com.uguryasar.weatherforecast.di;

import com.uguryasar.weatherforecast.network.rest.CityListRestApiInterface;
import com.uguryasar.weatherforecast.network.rest.WeatherRestApiInterface;
import com.uguryasar.weatherforecast.repository.MainRepositoryImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public final class RepositoryModule {
    @Provides
    public final MainRepositoryImpl providedMainRepo(@Named("WeatherApi") WeatherRestApiInterface weatherRest, @Named("CityListApi") CityListRestApiInterface cityRest) {
        return new MainRepositoryImpl(weatherRest, cityRest);
    }
}