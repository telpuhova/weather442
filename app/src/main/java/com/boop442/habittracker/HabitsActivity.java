package com.boop442.habittracker;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HabitsActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.habitsListView) ListView mHabitsListView;
    @BindView(R.id.addButton) Button mAddButton;

    String[] habits = new String[] {"play a guitar", "sports", "socialize", "code", "eat fruits", "call mom"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        ButterKnife.bind(this);

        HabitArrayAdapter adapter = new HabitArrayAdapter(this, android.R.layout.simple_list_item_1, habits);
        mHabitsListView.setAdapter(adapter);

        mAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        if (v == mAddButton) {

//            Bundle bundle = new Bundle();
//            bundle.putString("dataToShow", dataToShow);

//            gameDialogFragment.setArguments(bundle);


            FragmentManager fm = getFragmentManager();
            AddHabitDialogFragment moodDialogFragment = new AddHabitDialogFragment();
            moodDialogFragment.show(fm, "Sample Fragment");
        }
    }
}
