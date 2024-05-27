package com.hubbardgary.londontrails.model.dto;

import com.hubbardgary.londontrails.model.Coordinates;

import java.util.List;

public class RouteCoordinatesDto {
    private List<Coordinates> coordinates;
    private double minimumLatitude;
    private double maximumLatitude;
    private double minimumLongitude;
    private double maximumLongitude;

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
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
