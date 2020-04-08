package com.uguryasar.weatherforecast.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uguryasar.weatherforecast.network.rest.CityListRestApiInterface;
import com.uguryasar.weatherforecast.util.Constants;
import com.uguryasar.weatherforecast.network.rest.WeatherRestApiInterface;
import com.uguryasar.weatherforecast.network.extra.JavaNetCookieJar;
import com.uguryasar.weatherforecast.network.extra.JsonDateDeserializer;
import com.uguryasar.weatherforecast.network.extra.LoggingInterceptor;

import java.net.CookieManager;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module

public final class NetModule {
   @Provides
   public final HttpLoggingInterceptor providedHttpInterceptor() {
      return new HttpLoggingInterceptor().setLevel(Level.BODY);
   }

   @Provides
   public final Gson provideGson() {
      return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient().setPrettyPrinting().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();

   }

   @Provides
   public final OkHttpClient provideOkHttpClient( HttpLoggingInterceptor interceptor) {
 return  new Builder().addInterceptor(interceptor).addNetworkInterceptor(new LoggingInterceptor()).cookieJar(new JavaNetCookieJar(new CookieManager())).connectTimeout(90L, TimeUnit.SECONDS).writeTimeout(90L, TimeUnit.SECONDS).readTimeout(90L, TimeUnit.SECONDS).build();

   }

   @Provides
   @Named("Weather")
   public final Retrofit provideWeatherRetrofit( Gson gson, OkHttpClient okHttpClient) {

      return
              new Retrofit.Builder().callbackExecutor(Executors.newSingleThreadExecutor()).baseUrl(Constants.WEATHER_API_URL).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).build();
   }
    @Provides
    @Named("CityList")
    public final Retrofit provideCityListRetrofit( Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(Constants.CITY_LIST_API).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).build();
    }

   @Provides
   @Singleton
   @Named("WeatherApi")
   public final WeatherRestApiInterface providedWeatherRestApiInterface(@Named("Weather") Retrofit retrofit) {
      return retrofit.create(WeatherRestApiInterface.class);
   }
    @Provides
    @Singleton
    @Named("CityListApi")
    public final CityListRestApiInterface providedCityRestApiInterface(@Named("CityList")  Retrofit retrofit) {
        return retrofit.create(CityListRestApiInterface.class);
    }

}