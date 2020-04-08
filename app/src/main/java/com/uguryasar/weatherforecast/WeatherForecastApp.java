package com.uguryasar.weatherforecast;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.uguryasar.weatherforecast.di.AppComponent;
import com.uguryasar.weatherforecast.di.AppInjector;
import com.uguryasar.weatherforecast.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public final class WeatherForecastApp extends DaggerApplication implements HasSupportFragmentInjector {
   private AppComponent appComponent;
   public Activity activity;
   public DispatchingAndroidInjector mFragmentInjector;
   public static WeatherForecastApp app;

   protected AndroidInjector applicationInjector() {
      AppInjector.INSTANCE.init(this);
       DaggerAppComponent.create().inject(this);
      appComponent=  DaggerAppComponent.builder().build();

      appComponent.inject(this);
      return appComponent;
   }

   public void onCreate() {
      super.onCreate();
      app = this;
      AppInjector.INSTANCE.init(this);
   }

@Override
   public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
         return this.mFragmentInjector;
   }
}
