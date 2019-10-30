package com.hubbardgary.londontrails.dataprovider.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.dto.RouteCoordinatesDto;
import com.hubbardgary.londontrails.model.interfaces.IRoute;

public interface ICoordinateProvider {
    void initialize(IRoute route, AssetManager assetManager);
    RouteCoordinatesDto getRouteCoordinates(int start, int end);
}
