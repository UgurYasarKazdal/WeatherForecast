package com.uguryasar.weatherforecast.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton

public final class GithubViewModelFactory implements Factory {
   private final Map creators;

   public ViewModel create( Class modelClass) {
      Provider creator = (Provider)this.creators.get(modelClass);
      if (creator == null) {
         Iterator var4 = this.creators.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            boolean var8 = false;
            Class key = (Class)var3.getKey();
            var8 = false;
            Provider value = (Provider)var3.getValue();
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
         }
      }
      return null;
   }

   @Inject
   public GithubViewModelFactory( Map creators) {
      super();
      this.creators = creators;
   }
}