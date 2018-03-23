package com.boop442.weather442.models;

/**
 * Created by boop442 on 3/23/2018.
 */

public class Location {
    private String title;
    private String woeid;

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
}
