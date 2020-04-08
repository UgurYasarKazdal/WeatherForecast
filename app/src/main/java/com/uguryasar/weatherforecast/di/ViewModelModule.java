package com.uguryasar.weatherforecast.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

import com.uguryasar.weatherforecast.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module

public abstract class ViewModelModule {
   @Binds
   @IntoMap
   @ViewModelKey(MainViewModel.class)
   public abstract ViewModel bindLoginViewModel( MainViewModel var1);

   @Binds
   public abstract Factory bindViewModelFactory( GithubViewModelFactory var1);
}
