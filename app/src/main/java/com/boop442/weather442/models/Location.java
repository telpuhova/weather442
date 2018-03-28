package com.boop442.weather442.models;

import org.parceler.Parcel;

/**
 * Created by boop442 on 3/23/2018.
 */

@Parcel
public class Location {
    String title;
    String woeid;

    public Location() {
    }

    public Location(String title, String woeid) {
        this.title = title;
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }
}
