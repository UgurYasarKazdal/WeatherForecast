package com.uguryasar.weatherforecast.di;


import com.uguryasar.weatherforecast.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module

public abstract class ActivityModule {
    @ContributesAndroidInjector
    public abstract MainActivity contributeMainActivity();

}