package com.hubbardgary.londontrails.dataprovider.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.dto.RoutePoiDto;
import com.hubbardgary.londontrails.model.interfaces.IRoute;

public interface IPOIProvider {
    void initialize(IRoute route, AssetManager assetManager);
    RoutePoiDto getPOIsForRoute(int start, int end);
}
