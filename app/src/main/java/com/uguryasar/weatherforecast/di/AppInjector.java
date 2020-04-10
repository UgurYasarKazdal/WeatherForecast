package com.uguryasar.weatherforecast.di;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks;

import com.uguryasar.weatherforecast.WeatherForecastApp;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;


public final class AppInjector {
    public static final AppInjector INSTANCE;

    public final void init(final Application app) {
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ((WeatherForecastApp) app).activity = activity;
                handleActivity(activity);

            }

            public void onActivityStarted(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
                ((WeatherForecastApp) app).activity = activity;

            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private void handleActivity(Activity activity) {
        if (activity instanceof Injectable) {
            AndroidInjection.inject(activity);
        }

        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
        }

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {
                public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                    if (f instanceof Injectable) {
                        AndroidSupportInjection.inject(f);
                    }

                }
            }, true);
        }

    }

    static {
        INSTANCE = new AppInjector();
    }
}
