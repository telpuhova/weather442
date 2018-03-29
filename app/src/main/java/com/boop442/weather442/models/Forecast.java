package com.boop442.weather442.models;

import org.parceler.Parcel;

/**
 * Created by boop442 on 3/22/2018.
 */

@Parcel
public class Forecast {
    String date;
    String state;
    double t;
    double min_t;
    double max_t;
    String wind_dir;
    double wind_speed;
    int humidity;
    double air_pressure;

    public Forecast() {
    }

    public Forecast(String date, String state, double t, double min_t, double max_t, String wind_dir, double wind_speed, int humidity, double air_pressure) {
        this.date = date;
        this.state = state;
        this.t = t;
        this.min_t = min_t;
        this.max_t = max_t;
        this.wind_dir = wind_dir;
        this.wind_speed = wind_speed;
        this.humidity = humidity;
        this.air_pressure = air_pressure;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public double getT() {
        return t;
    }

    public double getMin_t() {
        return min_t;
    }

    public double getMax_t() {
        return max_t;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getAir_pressure() {
        return air_pressure;
    }
}
