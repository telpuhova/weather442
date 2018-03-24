package com.boop442.weather442.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.boop442.weather442.AddLocationDialogFragment;
import com.boop442.weather442.LocationsArrayAdapter;
import com.boop442.weather442.R;
import com.boop442.weather442.adapters.LocationListAdapter;
import com.boop442.weather442.models.Location;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener{
//    @BindView(R.id.locationsListView) ListView mLocationsListView;
    @BindView(R.id.addButton) Button mAddButton;

    String[] locationsTest = new String[] {"Portland", "Moscow", "Berlin", "44418", "London"};
    ArrayList<Location> locations = new ArrayList<>();

    @BindView(R.id.locationsRecyclerView) RecyclerView mRecyclerView;
    private LocationListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);

//        LocationsArrayAdapter adapter = new LocationsArrayAdapter(this, android.R.layout.simple_list_item_1, locations);
//        mLocationsListView.setAdapter(adapter);
        locations.add(new Location("Moscow", "123"));
        locations.add(new Location("Portland", "456"));


        mAdapter = new LocationListAdapter(locations, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LocationsActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


//        mRecyclerView.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(LocationsActivity.this, WeatherActivity.class);
////                intent.putExtra("location", locations.get(position).getClass());
//                intent.putExtra("location_title", locations.get(position).getTitle());
//                intent.putExtra("location_woeid", locations.get(position).getWoeid());
//                startActivity(intent);
//            }
//        });

//        mLocationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(LocationsActivity.this, WeatherActivity.class);
////                intent.putExtra("location", locations.get(position).getClass());
//                intent.putExtra("location_title", locations.get(position).getTitle());
//                intent.putExtra("location_woeid", locations.get(position).getWoeid());
//                startActivity(intent);
//            }
//        });

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
