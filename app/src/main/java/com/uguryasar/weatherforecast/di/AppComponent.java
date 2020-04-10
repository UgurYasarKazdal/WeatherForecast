package com.uguryasar.weatherforecast.di;

import com.uguryasar.weatherforecast.WeatherForecastApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(
        modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityModule.class, ViewModelModule.class, RepositoryModule.class, NetModule.class}
)

public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(WeatherForecastApp weatherForecastApp);
}