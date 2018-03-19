package com.boop442.habittracker;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.locationsListView) ListView mLocationsListView;
    @BindView(R.id.addButton) Button mAddButton;

    String[] locations = new String[] {"Portland", "Moscow", "Berlin", "London"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        ButterKnife.bind(this);

        LocationsArrayAdapter adapter = new LocationsArrayAdapter(this, android.R.layout.simple_list_item_1, locations);
        mLocationsListView.setAdapter(adapter);

        mLocationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocationsActivity.this, WeatherActivity.class);
                intent.putExtra("location", locations[position]);
                startActivity(intent);
            }
        });

        mAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        if (v == mAddButton) {

//            Bundle bundle = new Bundle();
//            bundle.putString("dataToShow", dataToShow);

//            gameDialogFragment.setArguments(bundle);


            FragmentManager fm = getFragmentManager();
            AddLocationDialogFragment moodDialogFragment = new AddLocationDialogFragment();
            moodDialogFragment.show(fm, "Sample Fragment");
        }
    }
}
