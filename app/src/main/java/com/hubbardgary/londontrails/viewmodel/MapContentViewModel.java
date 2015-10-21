package com.hubbardgary.londontrails.viewmodel;

import com.hubbardgary.londontrails.model.POI;

import java.util.List;

public class MapContentViewModel {

    public double minimumLatitude;
    public double maximumLatitude;
    public double minimumLongitude;
    public double maximumLongitude;

    public double startLatitude;
    public double startLongitude;
    public double endLatitude;
    public double endLongitude;

    public PathViewModel path;
    public List<POI> poi;
}
