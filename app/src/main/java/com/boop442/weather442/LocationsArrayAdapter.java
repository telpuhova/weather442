package com.boop442.weather442;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by boop442 on 3/16/2018.
 */

public class LocationsArrayAdapter extends ArrayAdapter{
    private Context mContext;
    private String[] mLocations;

    public LocationsArrayAdapter(Context mContext, int resource, String[] mLocations){
        super(mContext, resource);
        this.mContext = mContext;
        this.mLocations = mLocations;
    }

    @Override
    public Object getItem(int position) {
        String habit = mLocations[position];
        return String.format("%s", habit);
    }

    @Override
    public int getCount() {
        return mLocations.length;
    }
}
