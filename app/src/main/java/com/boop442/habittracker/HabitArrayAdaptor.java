package com.boop442.habittracker;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by boop442 on 3/16/2018.
 */

public class HabitArrayAdaptor extends ArrayAdapter{
    private Context mContext;
    private String[] mHabits;

    public HabitArrayAdaptor(Context mContext, int resource, String[] mHabits){
        super(mContext, resource);
        this.mContext = mContext;
        this.mHabits = mHabits;
    }
}
