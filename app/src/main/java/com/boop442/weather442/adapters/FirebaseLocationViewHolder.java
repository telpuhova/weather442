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

public class FirebaseLocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    private int mOrientation;
    private ArrayList<Location> mLocations = new ArrayList<>();

    public TextView mLocTitleTextViewFDF;


    public FirebaseLocationViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        mLocations = mLocations;
//        itemView.setOnClickListener(this);

        // Determines the current orientation of the device:
        mOrientation = itemView.getResources().getConfiguration().orientation;

        // Checks if the recorded orientation matches Android's landscape configuration.
        // if so, we create a new DetailFragment to display in our special landscape layout:
//        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Log.d("----------------------","ORIENTATION_LANDSCAPE");
//            createDetailFragment(0);
//        }
    }

    public void setmLocations(ArrayList<Location> mLocations) {
        this.mLocations = mLocations;
        Log.d("VH---------------------", mLocations.toString());
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("----------------------","ORIENTATION_LANDSCAPE");
            createDetailFragment(0);
        }
    }



    // Takes position of restaurant in list as parameter:
    private void createDetailFragment(int position) {
        if (mLocations.size() != 0) {
            // Creates new RestaurantDetailFragment with the given position:
            ForecastDetailFragment detailFragment = ForecastDetailFragment.newInstance(mLocations.get(position));
            // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            //  Replaces the FrameLayout with the RestaurantDetailFragment:
            ft.replace(R.id.locationDetailContainer, detailFragment);
            // Commits these changes:
            ft.commit();
        } else {
            Log.d("VH-cDF-----------------", "NULL");
        }


    }

    public void bindLocation(Location location) {
        mLocTitleTextViewFDF = (TextView) mView.findViewById(R.id.locationTextView);
//        RecyclerView recyclerViewFDF = (RecyclerView) mView.findViewById(R.id.recyclerViewFDF);

        mLocTitleTextViewFDF.setText(location.getTitle());
//        recyclerViewFDF.
    }

    @Override
    public void onClick(View v) {
        // Determines the position of the restaurant clicked:
        int itemPosition = getLayoutPosition();
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//            createDetailFragment(itemPosition);
        } else {
//            Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
//            intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
//            intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
//            mContext.startActivity(intent);
        }
    }

//    @Override
//    public void onClick(View view) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid;
//        if (!(user.isAnonymous()) && (user != null)) {
//            uid = user.getUid();
//        } else {
//            uid = "123456";
//        }
//
//        final ArrayList<Location> locations = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_LOCATIONS).child(uid);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    locations.add(snapshot.getValue(Location.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, ForecastDetailActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("locations", Parcels.wrap(locations));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.wtf("FireBaseLocationViewHolder------------------------", databaseError.getMessage());
//            }
//        });
//    }
}
