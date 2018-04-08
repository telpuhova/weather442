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
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
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

    @BindView(R.id.addButton)
    Button mAddButton;
    @BindView(R.id.authenticatedUserTextView)
    TextView mAuthenticatedUserTextView;
    @BindView(R.id.locationsRecyclerView)
    RecyclerView mRecyclerView;

    private FirebaseLocationListAdapter mFirebaseAdapter;


    String mWoeid = "";
    ArrayList<Location> locations = new ArrayList<>();


    @Override
    public void cancel() {
        Log.d("DEBUG:-----------------", "CANCEL------------------------");
    }

    @Override
    public void dismiss() {
        Log.d("DEBUG:-----------------", "DISSMISS------------------------");
    }

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

        Log.d("----LOCATIONS-UID------", mUid);


        mLocationReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_LOCATIONS)
                .child(mUid);


        locationQuery = mLocationReference.getRef();

        //!!!
//        locationQuery = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("locations");
        setUpFirebaseAdapter();

        mAddButton.setOnClickListener(this);

        mAuthenticatedUserTextView.setText(userEmail);

        return view;

    }




    public void setUpFirebaseAdapter() {

//        FirebaseRecyclerOptions<Location> options =
//                new FirebaseRecyclerOptions.Builder<Location>()
//                        .setQuery(locationQuery, Location.class)
//                        .build();


        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_LOCATIONS)
                .child(mUid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseLocationListAdapter(Location.class,
                R.layout.location_list_item, FirebaseLocationViewHolder.class,
                query, this, getActivity());



//        mFirebaseAdapter = new FirebaseLocationListAdapter(Location.class,
//                R.layout.location_list_item, FirebaseLocationViewHolder.class,
//                locationQuery, this, getActivity());



//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Location, FirebaseLocationViewHolder>(options) {
//
//            @Override
//            public FirebaseLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.location_list_item, parent, false);
//                return new FirebaseLocationViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(FirebaseLocationViewHolder viewHolder, int position, Location model) {
//                viewHolder.bindLocation(model);
//            }
//        };
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

//            Bundle bundle = new Bundle();
//            bundle.putParcelable("locationObject", Parcels.wrap(newLocation));
//            bundle.putString("dataToShow", dataToShow);


//            DialogFragment dialog = DialogFragment.instantiate(getActivity(), "Hello world");
//            dialog.show(getFragmentManager(), "dialog");

            FragmentManager fm = getActivity().getFragmentManager();
            addLocationDialogFragment = new AddLocationDialogFragment();
//            addLocationDialogFragment.setArguments(bundle);
            addLocationDialogFragment.show(fm, "Sample Fragment");

            //new
            addLocationDialogFragment.setOnDismissListener(this);


//            fm.executePendingTransactions();
//            addLocationDialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
//                    //do whatever you want when dialog is dismissed
//                    dismiss();
//                }
//            });
        }
    }




    @Override
    public void onDismiss(DialogInterface dialogInterface) {





        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        String uid = user.getUid();
                        DatabaseReference restaurantRef = FirebaseDatabase
                                .getInstance()
                                .getReference(Constants.FIREBASE_CHILD_LOCATIONS)
                                .child(mUid);

                        DatabaseReference pushRef = restaurantRef.push();
                        String pushId = pushRef.getKey();
                        locationObject.setPushId(mUid);
                        pushRef.setValue(locationObject);//push location to database



//                        DatabaseReference locationsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_LOCATIONS);
//                        locationsRef.push().setValue(locationObject);//push location to database

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mFirebaseAdapter.startListening();
//    }
}
