package com.hubbardgary.londontrails.model;

import com.google.android.gms.maps.model.LatLng;

public class POI {

    //private LatLng coords;
    private double latitude;
    private double longitude;
    private String title;
    public String snippet;

//    public LatLng getCoords() {
//        return coords;
//    }
//
//    public void setCoords(LatLng coords) {
//        this.coords = coords;
//    }



    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
