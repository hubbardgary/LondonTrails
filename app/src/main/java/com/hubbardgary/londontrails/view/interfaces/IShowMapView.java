package com.hubbardgary.londontrails.view.interfaces;

import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

public interface IShowMapView {
    void endActivity();
    int getIntFromIntent(String item);
    String getStringFromResources(int resourceId);
    void updateViewModel(ShowMapViewModel vm);
    void setMarkerVisibility(boolean visible);
    void resetCameraPosition();
    void setMapType(int mapType);
}
