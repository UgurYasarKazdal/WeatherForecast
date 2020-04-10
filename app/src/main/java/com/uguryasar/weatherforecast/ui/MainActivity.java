package com.uguryasar.weatherforecast.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.uguryasar.weatherforecast.util.Constants;
import com.uguryasar.weatherforecast.R;
import com.uguryasar.weatherforecast.di.Injectable;
import com.uguryasar.weatherforecast.model.CityList;
import com.uguryasar.weatherforecast.model.WeatherResponse;
import com.uguryasar.weatherforecast.network.rest.NetworkRequest;
import com.uguryasar.weatherforecast.network.rest.WeatherRestApiInterface;
import com.uguryasar.weatherforecast.util.MathHelper;
import com.uguryasar.weatherforecast.viewmodel.MainViewModel;

import static br.com.zbra.androidlinq.Linq.*;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends AppCompatActivity implements Injectable {

    private final int REQUEST_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    @Inject
    MainViewModel mainViewModel;
    @Inject
    @Named("WeatherApi")
    WeatherRestApiInterface weatherRestApiInterface;

    private TextView mTv_locationName;
    private TextView mTv_temp;
    private TextView mTv_coords;
    private List<CityList> mCityList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv_locationName = findViewById(R.id.tv_main_locName);
        mTv_temp = findViewById(R.id.tv_main_temp);
        mTv_coords = findViewById(R.id.tv_main_coords);

        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinner = findViewById(R.id.spn_main_cityList);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            getLocation();
        }

        mainViewModel.weatherByLocationData().observe(this, data -> {
            doWeatherByLocationWorks((WeatherResponse) data);
        });

        mainViewModel.cityList().observe(this, this::doCityListWorks);

        mainViewModel.GetCityList();

    }

    private void doWeatherByLocationWorks(WeatherResponse data) {
        mTv_locationName.setText(data.getName());
        //Convert Kelvin to Celcius
        mTv_temp.setText(MathHelper.getCelsiusStr(data.getMain().getTemp()));
        mTv_coords.setText(data.getCoord().getLat().toString() + " / " + data.getCoord().getLon().toString());
    }

    private void doCityListWorks(List<CityList> data) {
        mCityList = data;
        List<String> cities = stream(data).select(CityList::getIl).toList();
        cities.add(0, "Please select a city");
        setRecycleAdapter();

        spinner.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cities));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the value selected by the user
                // e.g. to store it as a field or immediately call a method
                if (position != 0)
                    getWeatherValues(mCityList.get(position - 1).getIl(), position, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        for (int i = 0; i < mCityList.size(); i++) {
            getWeatherValues(mCityList.get(i).getIl(), i, false);

        }
    }

    private void getWeatherValues(String _city, int _pos, boolean _fromSpinner) {
        NetworkRequest.performAsyncRequest(weatherRestApiInterface.GetWeatherByCityName(_city + ",tr", Constants.WEATHER_API_KEY),
                (response) -> {
                    if (_fromSpinner) {
                        doWeatherByLocationWorks(response);

                    } else {
                        mCityList.get(_pos).setTemp(MathHelper.getCelsiusStr(response.getMain().getTemp()));
                        mAdapter.notifyDataSetChanged();
                    }

                }, (error) -> {
                    Log.e("asd", "Error: " + error.toString());
                });
    }


    private void setRecycleAdapter() {
        mAdapter = new CityAdapter(mCityList);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //Success Perform Task Here
                    getLocation();
                    break;
                case Activity.RESULT_CANCELED:

                    break;
            }
        }
    }

    private void getLocation() {
        LocationRequest request = new LocationRequest();

        request.setInterval(60000);

        //Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationProviderClient.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation() != null) {
                    mainViewModel.GetWeatherByLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                }
            }
        }, null);

    }
}