package com.hubbardgary.londontrails.view.interfaces;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.hubbardgary.londontrails.model.LondonTrailsPlacemark;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import java.util.List;

public interface IShowMapView {
    void endActivity();
    int getIntFromIntent(String item);
    String getStringFromResources(int resourceId);
    void updateViewModel(ShowMapViewModel vm);
    void setMarkerVisibility(boolean visible);
    void resetCameraPosition();
    void setMapType(int mapType);

    // Methods used in MapContentActivity
    Context getApplicationContext();
    GoogleMap getMap();
    ShowMapViewModel getShowMapVm();
    void setMapRoute(Polyline mapRoute);
    void setPlacemarks(List<LondonTrailsPlacemark> placemarks);
    void setDefaultBounds(LatLngBounds.Builder defaultBounds);
}
