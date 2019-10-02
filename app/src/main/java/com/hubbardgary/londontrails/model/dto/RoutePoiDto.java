package com.hubbardgary.londontrails.model.dto;

import com.hubbardgary.londontrails.model.POI;

import java.util.List;

public class RoutePoiDto {
    private List<POI> pois;
    private double minimumLatitude;
    private double maximumLatitude;
    private double minimumLongitude;
    private double maximumLongitude;

    public List<POI> getPOIs() {
        return pois;
    }

    public void setPOIs(List<POI> pois) {
        this.pois = pois;
    }

    public double getMinimumLatitude() {
        return minimumLatitude;
    }

    public void setMinimumLatitude(double minimumLatitude) {
        this.minimumLatitude = minimumLatitude;
    }

    public double getMaximumLatitude() {
        return maximumLatitude;
    }

    public void setMaximumLatitude(double maximumLatitude) {
        this.maximumLatitude = maximumLatitude;
    }

    public double getMinimumLongitude() {
        return minimumLongitude;
    }

    public void setMinimumLongitude(double minimumLongitude) {
        this.minimumLongitude = minimumLongitude;
    }

    public double getMaximumLongitude() {
        return maximumLongitude;
    }

    public void setMaximumLongitude(double maximumLongitude) {
        this.maximumLongitude = maximumLongitude;
    }
}
