package com.boop442.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HabitsActivity extends AppCompatActivity {
    String[] habits = new String[] {"play a music instrument", "sports", "socialize", "code", "eat fruits", "call mom"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
    }
}
