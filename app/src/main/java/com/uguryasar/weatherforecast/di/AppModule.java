package com.uguryasar.weatherforecast.di;

import android.app.Activity;
import android.content.Context;

import com.uguryasar.weatherforecast.WeatherForecastApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(
   includes = {ViewModelModule.class}
)

public final class AppModule {
   @Provides
   @Singleton
   public final WeatherForecastApp providedApp() {
      return WeatherForecastApp.app;
   }

   @Provides
   public final Context providedContext( WeatherForecastApp app) {
      return app.getApplicationContext();
   }

   @Provides
   public final Activity providedActivity$app_debug( WeatherForecastApp app) {
      return app.activity;
   }
}