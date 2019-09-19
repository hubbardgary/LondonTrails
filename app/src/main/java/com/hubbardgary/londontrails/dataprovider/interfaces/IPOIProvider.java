package com.hubbardgary.londontrails.dataprovider.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.model.Route;

import java.util.List;

public interface IPOIProvider {
    void initialize(Route route, AssetManager assetManager);
    List<POI> getPOIsForRoute(int start, int end);
}
