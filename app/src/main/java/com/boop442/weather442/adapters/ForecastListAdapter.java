package com.boop442.weather442.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.boop442.weather442.R;
import com.boop442.weather442.models.Forecast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boop442 on 3/22/2018.
 */

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder> {
    private ArrayList<Forecast> mForecasts = new ArrayList<>();
    Context mContext;

    public ForecastListAdapter(ArrayList<Forecast> mForecasts, Context mContext) {
        this.mForecasts = mForecasts;
        this.mContext = mContext;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bindForecast(mForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dateTextView) TextView mDateTextView;
        @BindView(R.id.tempTextView) TextView mTempTextView;
        @BindView(R.id.stateTextView) TextView mStateTextView;

        private Context mContext;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindForecast(Forecast forecast) {
            mDateTextView.setText(forecast.getDate());
            mTempTextView.setText(String.valueOf(forecast.getT()));
            mStateTextView.setText(forecast.getState());
        }
    }
}
