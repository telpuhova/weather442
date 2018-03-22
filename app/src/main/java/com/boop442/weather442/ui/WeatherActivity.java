package com.boop442.weather442.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.boop442.weather442.R;
import com.boop442.weather442.services.MetaWeatherService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    @BindView(R.id.locTitleTextView) TextView mLocTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String location = intent.getStringExtra("location").toString();

        mLocTitleTextView.setText(location);

        getForecast(location);
    }

    private void getForecast(String location) {
        final MetaWeatherService weatherService = new MetaWeatherService();
        weatherService.findForecast(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v("weatherActivity", jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
