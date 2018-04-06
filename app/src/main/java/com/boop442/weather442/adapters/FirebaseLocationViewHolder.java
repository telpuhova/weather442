package com.boop442.weather442.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.ui.ForecastDetailActivity;
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

    public TextView mLocTitleTextViewFDF;


    public FirebaseLocationViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindLocation(Location location) {
        mLocTitleTextViewFDF = (TextView) mView.findViewById(R.id.locationTextView);
//        RecyclerView recyclerViewFDF = (RecyclerView) mView.findViewById(R.id.recyclerViewFDF);

        mLocTitleTextViewFDF.setText(location.getTitle());
//        recyclerViewFDF.
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
