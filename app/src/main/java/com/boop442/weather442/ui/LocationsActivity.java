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
import com.boop442.weather442.adapters.FirebaseLocationViewHolder;
import com.boop442.weather442.adapters.ForecastListAdapter;
import com.boop442.weather442.adapters.LocationListAdapter;
import com.boop442.weather442.models.Forecast;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.services.MetaWeatherService;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {

    private SharedPreferences mSharedPreferences;
    private String mRecentLocation;
    private List<Forecast> mForecasts = new ArrayList<>();

    @BindView(R.id.addButton) Button mAddButton;
    @BindView(R.id.locationsRecyclerView) RecyclerView mRecyclerView;

    private DatabaseReference mLocationsReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    String mWoeid = "";
    ArrayList<Location> locations = new ArrayList<>();
    private LocationListAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);

//        locations.add(new Location("Moscow", "2122265"));
//        locations.add(new Location("Portland", "2475687"));

//        DatabaseReference locationsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_LOCATIONS);
//        locationsRef.push().setValue(locations.get(0));//push location to database
//        locationsRef.push().setValue(locations.get(1));//push location to database

        mLocationsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_LOCATIONS);
        setUpFirebaseAdapter();

//        mAdapter = new LocationListAdapter(locations, getApplicationContext());
//        mRecyclerView.setAdapter(mAdapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LocationsActivity.this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);


        mAddButton.setOnClickListener(this);
    }

    public void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Location, FirebaseLocationViewHolder>
                (Location.class, R.layout.location_list_item, FirebaseLocationViewHolder.class,
                        mLocationsReference) {

            @Override
            protected void populateViewHolder(FirebaseLocationViewHolder viewHolder,
                                              Location model, int position) {
                viewHolder.bindLocation(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onClick (View v) {

        if (v == mAddButton) {

//            Bundle bundle = new Bundle();
//            bundle.putParcelable("locationObject", Parcels.wrap(newLocation));
//            bundle.putString("dataToShow", dataToShow);

            FragmentManager fm = getFragmentManager();
            AddLocationDialogFragment addLocationDialogFragment = new AddLocationDialogFragment();
//            addLocationDialogFragment.setArguments(bundle);
            addLocationDialogFragment.show(fm, "Sample Fragment");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentLocation = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        final Location locationObject = new Location(mRecentLocation, "123");

        final MetaWeatherService weatherService = new MetaWeatherService();

        weatherService.getWoeid(mRecentLocation, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("LOCATIONS_ACTIVITY", "weatherService.getWoeid callback function --- onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("LOCATIONS_ACTIVITY", "weatherService.getWoeid callback function --- onResponse");
                mWoeid = MetaWeatherService.processWoeidCall(response);
                Log.d("LOCATIONS_ACTIVITY", mWoeid);
                locationObject.setWoeid(mWoeid);




                final MetaWeatherService weatherService = new MetaWeatherService();
                Log.v("WEATHER_ACTIVITY", "getForecast function");

                //second API call
                weatherService.findForecast(mWoeid, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        mForecasts = MetaWeatherService.processResults(response);


                        //add to list
                        locationObject.setForecasts(mForecasts);
                        locations.add(locationObject);

                        //write to firebase
                        DatabaseReference locationsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_LOCATIONS);
                        locationsRef.push().setValue(locationObject);//push location to database

                    }
                });
            }
        });

    }
}
