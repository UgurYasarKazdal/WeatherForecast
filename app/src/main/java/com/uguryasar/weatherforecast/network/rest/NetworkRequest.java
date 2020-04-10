package com.uguryasar.weatherforecast.network.rest;

import androidx.annotation.NonNull;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NetworkRequest {
    public static <T> void performAsyncRequest(
            @NonNull Observable<T> observable, @NonNull Action1<? super T> onAction, @NonNull Action1<Throwable> onError) {
        // Specify a scheduler (Scheduler.newThread(), Scheduler.immediate(), ...)
        // We choose Scheduler.io() to perform network request in a thread pool
        observable.subscribeOn(Schedulers.io())
                // Observe emergency_notification_ in the main thread to be able to update UI
                .observeOn(AndroidSchedulers.mainThread())
                // Set callbacks actions
                .subscribe(onAction, onError);
    }
}