package com.hubbardgary.londontrails.dataprovider.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.dto.RouteCoordinatesDto;

public interface ICoordinateProvider {
    void initialize(Route route, AssetManager assetManager);
    RouteCoordinatesDto getRouteCoordinates(int start, int end);
}
