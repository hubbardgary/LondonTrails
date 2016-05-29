package com.hubbardgary.londontrails.view.interfaces;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.viewmodel.PathViewModel;

public interface IMapContentView {
    void getPath(PathViewModel path);
    void initializeDefaultBounds(double minLatitude, double minLongitude, double maxLatitude, double maxLongitude);
    AssetManager getAssetManager();
}