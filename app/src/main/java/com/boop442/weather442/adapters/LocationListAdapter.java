package com.boop442.weather442.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boop442.weather442.R;
import com.boop442.weather442.models.Forecast;
import com.boop442.weather442.models.Location;
import com.boop442.weather442.ui.ForecastDetailActivity;
import com.boop442.weather442.ui.ForecastDetailFragment;
import com.boop442.weather442.ui.LocationsActivity;
import com.boop442.weather442.ui.WeatherActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boop442 on 3/23/2018.
 */

public class LocationListAdapter {

}

//public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>{
//    private ArrayList<Location> mLocations = new ArrayList<>();
//    Context mContext;
//
//
//    public LocationListAdapter(ArrayList<Location> mLocations, Context mContext) {
////        this.mLocations = mLocations;
////        this.mContext = mContext;
//    }
//
//
//    @Override
//    public LocationListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
//        LocationListAdapter.LocationViewHolder viewHolder = new LocationListAdapter.LocationViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(LocationListAdapter.LocationViewHolder holder, int position) {
//        holder.bindLocation(mLocations.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mLocations.size();
//    }
//
//
//    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        @BindView(R.id.locationTextView) TextView mLocationTextView;
//        private Context mContext;
//
//        private int mOrientation;
//
//        public LocationViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            mContext = itemView.getContext();
//
//            itemView.setOnClickListener(this);
//
//            // Determines the current orientation of the device:
//            mOrientation = itemView.getResources().getConfiguration().orientation;
//
//            // Checks if the recorded orientation matches Android's landscape configuration.
//            // if so, we create a new DetailFragment to display in our special landscape layout:
//            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//                createDetailFragment(0);
//            }
//        }
//
//        // Takes position of restaurant in list as parameter:
//        private void createDetailFragment(int position) {
//            // Creates new RestaurantDetailFragment with the given position:
//            ForecastDetailFragment detailFragment = new ForecastDetailFragment();
//            // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
//            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
//            //  Replaces the FrameLayout with the RestaurantDetailFragment:
//            ft.replace(R.id.locationDetailContainer, detailFragment);
//            // Commits these changes:
//            ft.commit();
//        }
//
//        public void bindLocation(Location location) {
//            mLocationTextView.setText(location.getTitle());
//        }
//
//
//        public void onClick(View v) {
//            int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(mContext, ForecastDetailActivity.class);
//            intent.putExtra("position", itemPosition);
//            intent.putExtra("locations", Parcels.wrap(mLocations));
//            mContext.startActivity(intent);
//
//        }
//    }
//}
