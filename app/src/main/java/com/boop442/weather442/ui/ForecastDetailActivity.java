package com.boop442.weather442.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boop442.weather442.R;
import com.boop442.weather442.adapters.ForecastPagerAdapter;
import com.boop442.weather442.models.Location;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private ForecastPagerAdapter forecastPagerAdapter;
    ArrayList<Location> mLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);
        ButterKnife.bind(this);

        mLocations = Parcels.unwrap(getIntent().getParcelableExtra("locations"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        forecastPagerAdapter = new ForecastPagerAdapter(getSupportFragmentManager(), mLocations);
        mViewPager.setAdapter(forecastPagerAdapter);
        mViewPager.setCurrentItem(startingPosition);
    }
}
