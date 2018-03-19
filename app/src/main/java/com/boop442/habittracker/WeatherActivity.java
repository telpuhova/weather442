package com.boop442.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    }
}
