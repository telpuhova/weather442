package com.boop442.weather442.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boop442.weather442.R;
import com.boop442.weather442.models.Forecast;
import com.boop442.weather442.models.Location;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boop442 on 3/23/2018.
 */

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>{
    private ArrayList<Location> mLocations = new ArrayList<>();
    Context mContext;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Location item, Context context);
    }


    public LocationListAdapter(ArrayList<Location> mLocations, Context mContext, OnItemClickListener listener) {
        this.mLocations = mLocations;
        this.mContext = mContext;
        this.listener = listener;
    }

//    @Override
//    public void onItemClick(Location item, Context context) {
//        Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show();
//    }

    @Override
    public LocationListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        LocationListAdapter.LocationViewHolder viewHolder = new LocationListAdapter.LocationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationListAdapter.LocationViewHolder holder, int position) {
        holder.bindLocation(mLocations.get(position));
        holder.bind(mLocations.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.locationTextView) TextView mLocationTextView;
        private Context mContext;

        public LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindLocation(Location location) {
            mLocationTextView.setText(location.getTitle());
        }

        public void bind(final Location item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item, mContext);
                }
            });
        }
    }
}
