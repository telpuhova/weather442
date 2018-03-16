package com.boop442.habittracker;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by boop442 on 3/16/2018.
 */

public class HabitArrayAdapter extends ArrayAdapter{
    private Context mContext;
    private String[] mHabits;

    public HabitArrayAdapter(Context mContext, int resource, String[] mHabits){
        super(mContext, resource);
        this.mContext = mContext;
        this.mHabits = mHabits;
    }

    @Override
    public Object getItem(int position) {
        String habit = mHabits[position];
        return String.format("%s", habit);
    }

    @Override
    public int getCount() {
        return mHabits.length;
    }
}
