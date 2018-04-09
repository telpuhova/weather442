package com.boop442.weather442.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.ui.ForecastDetailActivity;
import com.boop442.weather442.ui.ForecastDetailFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseLocationViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    private int mOrientation;
    private ArrayList<Location> mLocations = new ArrayList<>();

    public TextView mLocTitleTextViewFDF;


    public FirebaseLocationViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();

        mOrientation = itemView.getResources().getConfiguration().orientation;
    }

    public void setmLocations(ArrayList<Location> mLocations) {
        this.mLocations = mLocations;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }
    }

    public int getmOrientation() {
        return mOrientation;
    }


    public void createDetailFragment(int position) {
        if (mLocations.size() != 0) {
            ForecastDetailFragment detailFragment = ForecastDetailFragment.newInstance(mLocations.get(position));
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.locationDetailContainer, detailFragment);
            ft.commit();
        } else {}
    }

    public void bindLocation(Location location) {
        mLocTitleTextViewFDF = (TextView) mView.findViewById(R.id.locationTextView);
        mLocTitleTextViewFDF.setText(location.getTitle());
    }
}
