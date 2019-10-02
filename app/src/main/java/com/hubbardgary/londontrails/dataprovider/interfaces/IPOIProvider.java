package com.hubbardgary.londontrails.dataprovider.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.dto.RoutePoiDto;

import java.util.List;

public interface IPOIProvider {
    void initialize(Route route, AssetManager assetManager);
    RoutePoiDto getPOIsForRoute(int start, int end);
}
