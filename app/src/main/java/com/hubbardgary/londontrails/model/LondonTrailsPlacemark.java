package com.hubbardgary.londontrails.model;

import com.google.android.gms.maps.model.Marker;

public class LondonTrailsPlacemark {
    private Marker googleMapsMarker;
    private boolean alwaysShow;

    public LondonTrailsPlacemark(Marker googleMapsMarker, boolean alwaysShow) {
        this.googleMapsMarker = googleMapsMarker;
        this.alwaysShow = alwaysShow;
    }

    public Marker getGoogleMapsMarker() {
        return googleMapsMarker;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }
}
