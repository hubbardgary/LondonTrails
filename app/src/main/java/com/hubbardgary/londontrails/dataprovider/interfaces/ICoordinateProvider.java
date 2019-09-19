package com.hubbardgary.londontrails.dataprovider.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.Route;

public interface ICoordinateProvider {
    void initialize(Route route, AssetManager assetManager);
    double[][] getPathWayPoints(int start, int end);
}
