package com.uguryasar.weatherforecast.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public final class GithubViewModelFactory implements Factory {
    private final Map creators;

    public ViewModel create(Class modelClass) {
        Provider creator = (Provider) this.creators.get(modelClass);
        if (creator == null) {
            for (Object o : this.creators.entrySet()) {
                Class key = (Class) ((Entry) o).getKey();
                Provider value = (Provider) ((Entry) o).getValue();
                if (modelClass.isAssignableFrom(key)) {
                    creator = value;
                    break;
                }
            }
        }

        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        } else {
            try {
                return (ViewModel) creator.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Inject
    public GithubViewModelFactory(Map creators) {
        super();
        this.creators = creators;
    }
}