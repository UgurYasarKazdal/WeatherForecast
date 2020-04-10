package com.uguryasar.weatherforecast.di;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.MapKey;

@Retention(RetentionPolicy.RUNTIME)
@MapKey
@Documented
@java.lang.annotation.Target({ElementType.METHOD})
@interface ViewModelKey {
    Class value();
}