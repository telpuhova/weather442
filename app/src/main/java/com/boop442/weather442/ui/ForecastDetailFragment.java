package com.boop442.weather442.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.adapters.ForecastListAdapter;
import com.boop442.weather442.models.Forecast;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.services.MetaWeatherService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ForecastDetailFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.recyclerViewFDF) RecyclerView mRecyclerView;
    @BindView(R.id.locTitleTextViewFDF) TextView mTextView;
    @BindView(R.id.refreshButton) Button mRefreshButton;

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference();

    private Location mCurrentLocation;
    private ArrayList<Forecast> forecasts;
    private ForecastListAdapter mAdapter;

    public static ForecastDetailFragment newInstance(Location location) {
        ForecastDetailFragment restaurantDetailFragment = new ForecastDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("location", Parcels.wrap(location));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentLocation = Parcels.unwrap(getArguments().getParcelable("location"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
        ButterKnife.bind(this, view);

        mTextView.setText(mCurrentLocation.getTitle());

        mRefreshButton.setOnClickListener(this);

        displayForecasts();

//        Picasso.with(view.getContext()).load(mRestaurant.getImageUrl()).into(mImageLabel);

//        mNameLabel.setText(mRestaurant.getName());
//        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
//        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
//        mPhoneLabel.setText(mRestaurant.getPhone());
//        mAddressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getAddress()));

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mRefreshButton) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("RUN--------------------", getContext().toString());
                    mAdapter = new ForecastListAdapter(mCurrentLocation.getForecasts(), getContext());
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                }
            });
        }
    }

    public void displayForecasts() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("RUN--------------------", getContext().toString());
                mAdapter = new ForecastListAdapter(mCurrentLocation.getForecasts(), getContext());
                mRecyclerView.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
            }
        });
    }
}
