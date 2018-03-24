package com.boop442.weather442.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public LocationListAdapter(ArrayList<Location> mLocations, Context mContext) {
        this.mLocations = mLocations;
        this.mContext = mContext;
    }

    @Override
    public LocationListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        LocationListAdapter.LocationViewHolder viewHolder = new LocationListAdapter.LocationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationListAdapter.LocationViewHolder holder, int position) {
        holder.bindLocation(mLocations.get(position));
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
    }
}
