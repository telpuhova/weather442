package com.boop442.weather442.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.adapters.FirebaseLocationListAdapter;
import com.boop442.weather442.adapters.FirebaseLocationViewHolder;
import com.boop442.weather442.models.Forecast;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.services.MetaWeatherService;
import com.boop442.weather442.util.OnStartDragListener;
import com.boop442.weather442.util.SimpleItemTouchHelperCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LocationsFragment extends Fragment implements DialogInterface.OnDismissListener, DialogInterface, View.OnClickListener, OnStartDragListener {
    private SharedPreferences mSharedPreferences;
    private String mRecentLocation;
    private List<Forecast> mForecasts = new ArrayList<>();
    private Query locationQuery;
    private DatabaseReference mLocationReference;
    private String mUid;

    private ItemTouchHelper mItemTouchHelper;
    AddLocationDialogFragment addLocationDialogFragment;

    @BindView(R.id.addButton) Button mAddButton;
    @BindView(R.id.authenticatedUserTextView) TextView mAuthenticatedUserTextView;
    @BindView(R.id.locationsRecyclerView) RecyclerView mRecyclerView;

    private FirebaseLocationListAdapter mFirebaseAdapter;


    String mWoeid = "";
    ArrayList<Location> locations = new ArrayList<>();


    @Override
    public void cancel() {}

    @Override
    public void dismiss() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        ButterKnife.bind(this, view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userEmail = "guest";
        mUid = "123456";
        if ((user != null) && !user.isAnonymous()) {
            mUid = user.getUid();
            userEmail = user.getEmail();
        }

        mLocationReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_LOCATIONS)
                .child(mUid);


        locationQuery = mLocationReference.getRef();
        setUpFirebaseAdapter();
        mAddButton.setOnClickListener(this);
        mAuthenticatedUserTextView.setText(userEmail);

        return view;
    }




    public void setUpFirebaseAdapter() {

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_LOCATIONS)
                .child(mUid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseLocationListAdapter(Location.class,
                R.layout.location_list_item, FirebaseLocationViewHolder.class,
                query, this, getActivity());


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick (View v) {

        if (v == mAddButton) {

            FragmentManager fm = getActivity().getFragmentManager();
            addLocationDialogFragment = new AddLocationDialogFragment();
//            addLocationDialogFragment.setArguments(bundle);
            addLocationDialogFragment.show(fm, "Sample Fragment");

            addLocationDialogFragment.setOnDismissListener(this);
        }
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mRecentLocation = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        if (mRecentLocation.equals("cancelStr")) {
            return;
        }

        final Location locationObject = new Location(mRecentLocation, "123");

        final MetaWeatherService weatherService = new MetaWeatherService();

        weatherService.getWoeid(mRecentLocation, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mWoeid = MetaWeatherService.processWoeidCall(response);
                locationObject.setWoeid(mWoeid);

                final MetaWeatherService weatherService = new MetaWeatherService();

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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference restaurantRef = FirebaseDatabase
                                .getInstance()
                                .getReference(Constants.FIREBASE_CHILD_LOCATIONS)
                                .child(mUid);

                        DatabaseReference pushRef = restaurantRef.push();
                        String pushId = pushRef.getKey();
                        locationObject.setPushId(mUid);
                        pushRef.setValue(locationObject);//push location to database
                    }
                });
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}
