package com.hubbardgary.londontrails.viewmodel;

import com.hubbardgary.londontrails.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class PathViewModel {
    private String name;
    private String description;
    private int color;
    private int width;
    private List<Coordinates> coordinates = new ArrayList<>();

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public double getCoordinateLat(int i) {
        return coordinates.get(i).getLatitude();
    }

    public double getCoordinateLng(int i) {
        return coordinates.get(i).getLongitude();
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

}
