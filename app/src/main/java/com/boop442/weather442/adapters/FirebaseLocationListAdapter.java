package com.boop442.weather442.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.boop442.weather442.models.Location;
import com.boop442.weather442.util.ItemTouchHelperAdapter;
import com.boop442.weather442.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FirebaseLocationListAdapter extends FirebaseRecyclerAdapter<Location, FirebaseLocationViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseLocationListAdapter(Class<Location> modelClass, int modelLayout,
                                         Class<FirebaseLocationViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
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
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
