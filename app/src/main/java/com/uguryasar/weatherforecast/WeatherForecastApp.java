package com.uguryasar.weatherforecast;

import android.app.Activity;

import com.uguryasar.weatherforecast.di.AppComponent;
import com.uguryasar.weatherforecast.di.AppInjector;
import com.uguryasar.weatherforecast.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public final class WeatherForecastApp extends DaggerApplication {
    public Activity activity;
    public static WeatherForecastApp app;

    protected AndroidInjector applicationInjector() {
        AppInjector.INSTANCE.init(this);
        DaggerAppComponent.create().inject(this);
        AppComponent appComponent = DaggerAppComponent.builder().build();

        appComponent.inject(this);
        return appComponent;
    }

    public void onCreate() {
        super.onCreate();
        app = this;
        AppInjector.INSTANCE.init(this);
    }
}