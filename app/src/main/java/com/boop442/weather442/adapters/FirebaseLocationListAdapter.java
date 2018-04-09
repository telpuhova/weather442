package com.boop442.weather442.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.boop442.weather442.models.Location;
import com.boop442.weather442.ui.ForecastDetailActivity;
import com.boop442.weather442.util.ItemTouchHelperAdapter;
import com.boop442.weather442.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseLocationListAdapter extends FirebaseRecyclerAdapter<Location, FirebaseLocationViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Location> mLocations = new ArrayList<>();

    public FirebaseLocationListAdapter(Class<Location> modelClass, int modelLayout,
                                         Class<FirebaseLocationViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mLocations.add(dataSnapshot.getValue(Location.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseLocationViewHolder viewHolder, Location model, int position) {
        viewHolder.bindLocation(model);
        viewHolder.mLocTitleTextViewFDF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (viewHolder.getmOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
                    viewHolder.createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, ForecastDetailActivity.class);
                    intent.putExtra("position", viewHolder.getAdapterPosition());
                    intent.putExtra("locations", Parcels.wrap(mLocations));
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mLocations, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mLocations.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Location restaurant : mLocations) {
            int index = mLocations.indexOf(restaurant);
            DatabaseReference ref = getRef(index);
            restaurant.setIndex(Integer.toString(index));
            ref.setValue(restaurant);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }

    @Override
    public FirebaseLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FirebaseLocationViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        viewHolder.setmLocations(mLocations);
        return viewHolder;
    }
}
