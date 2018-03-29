package com.boop442.weather442.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.boop442.weather442.models.Location;
import com.boop442.weather442.ui.ForecastDetailFragment;

import java.util.ArrayList;

public class ForecastPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Location> mLocations;

    public ForecastPagerAdapter(FragmentManager fm, ArrayList<Location> mLocations) {
        super(fm);
        this.mLocations = mLocations;
    }

    @Override
    public Fragment getItem(int position) {
        return ForecastDetailFragment.newInstance(mLocations.get(position));
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mLocations.get(position).getTitle();
    }

}
