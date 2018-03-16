package com.boop442.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HabitsActivity extends AppCompatActivity {
    @BindView(R.id.habitsListView) ListView mHabitsListView;
    @BindView(R.id.addButton) Button mAddButton;

    String[] habits = new String[] {"play a music instrument", "sports", "socialize", "code", "eat fruits", "call mom"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        ButterKnife.bind(this);

        HabitArrayAdapter adapter = new HabitArrayAdapter(this, android.R.layout.simple_list_item_1, habits);
        mHabitsListView.setAdapter(adapter);

    }
}
