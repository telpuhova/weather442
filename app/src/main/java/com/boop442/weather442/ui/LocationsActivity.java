package com.boop442.weather442.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.adapters.LocationListAdapter;
import com.boop442.weather442.models.Location;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {

    private SharedPreferences mSharedPreferences;
    private String mRecentLocation;

//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;


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



//        mRecyclerView.setAdapter(new LocationListAdapter(locations, getApplicationContext(), new LocationListAdapter.OnItemClickListener() {
//            @Override public void onItemClick(Location item, Context context) {
//                Toast.makeText(context, item.getTitle(), Toast.LENGTH_LONG).show();
//            }
//        }));


        mAdapter = new LocationListAdapter(locations, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LocationsActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);




        mAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
//        Log.d("TEST-------------------", "TEST");
        if (v == mAddButton) {



//            Bundle bundle = new Bundle();
//            bundle.putParcelable("locationObject", Parcels.wrap(newLocation));
//            bundle.putString("dataToShow", dataToShow);



            FragmentManager fm = getFragmentManager();
            AddLocationDialogFragment addLocationDialogFragment = new AddLocationDialogFragment();
//            addLocationDialogFragment.setArguments(bundle);
            addLocationDialogFragment.show(fm, "Sample Fragment");

//            addLocationDialogFragment.onDismiss(addLocationDialogFragment);

        }

//        if (v == mSubmitButton) {
//            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            mRecentLocation = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
//            Log.d("SHARED PREF LOCATION---", mRecentLocation);
//        }
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Log.d("ON_DISMISS-------------", "--------------------------------");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mSharedPreferences = getSharedPreferences("numberPicker.preferences", 0);//this is null right now

//        mSharedPreferences.edit().putString(Constants.PREFERENCES_LOCATION_KEY, "wasssssup").apply();

        mRecentLocation = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        Log.d("SHARED PREF LOCATION---", mRecentLocation);


        Location newLocation = new Location(mRecentLocation, "123");
        locations.add(newLocation);

    }
}
