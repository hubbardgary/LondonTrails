package com.hubbardgary.londontrails.config.interfaces;

import android.content.ComponentCallbacks2;

import com.hubbardgary.londontrails.model.Route;

public interface IGlobalObjects extends ComponentCallbacks2 {
    Route getCurrentRoute();

    void setCurrentRoute(Route r);

    CharSequence getButtonText(String title, String subtitle);

    void setMapPreference(int mapType);

    String getStringFromResource(int resourceId);

    int getMapPreference();
}
