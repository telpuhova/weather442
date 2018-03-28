package com.boop442.weather442.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.boop442.weather442.R;
import com.boop442.weather442.adapters.ForecastListAdapter;
import com.boop442.weather442.models.Forecast;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.services.MetaWeatherService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
//    @BindView(R.id.locTitleTextView) TextView mLocTitleTextView;
//    @BindView(R.id.listView) ListView mListView;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.locTitleTextView) TextView mLocTitleTextView;
    private ForecastListAdapter mAdapter;

    String woeid = "44418";
    ArrayList<Forecast> forecasts = new ArrayList<>();
    String[] forecastsDatesTest = {"123", "234", "345", "456", "567", "678"};
    ArrayList<Location> mLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        mLocations = Parcels.unwrap(getIntent().getParcelableExtra("locations"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        String location_title = mLocations.get(startingPosition).getTitle();
        String location_woeid = mLocations.get(startingPosition).getWoeid();

//        mLocTitleTextView.setText(location);

        mLocTitleTextView.setText(location_title);

        getForecast(location_title);
    }

    private void getForecast(String location) {
        final MetaWeatherService weatherService = new MetaWeatherService();
        Log.v("WEATHER_ACTIVITY", "getForecast function");


//        weatherService.getWoeid(location, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.v("WEATHER_ACTIVITY", "weatherService.getWoeid callback function --- onFailure");
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.v("WEATHER_ACTIVITY", "weatherService.getWoeid callback function --- onResponse");
//                woeid = String.valueOf(MetaWeatherService.processWoeidCall(response));
//            }
//        });

        weatherService.findForecast(woeid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                forecasts = MetaWeatherService.processResults(response);

                //displaying asynchronously in a ui thread
                WeatherActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new ForecastListAdapter(forecasts, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WeatherActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
