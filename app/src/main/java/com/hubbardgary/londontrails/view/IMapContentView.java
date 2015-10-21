package com.hubbardgary.londontrails.view;

import android.content.res.AssetManager;

import com.google.android.gms.maps.model.LatLng;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.viewmodel.PathViewModel;

public interface IMapContentView {
    void drawPath(PathViewModel path);
    void initializeDefaultBounds(LatLng minLatLng, LatLng maxLatLng);
    Route getRoute();
    AssetManager getAssetManager();
    int getStart();
    int getEnd();
    boolean isClockwise();
}
