package com.hubbardgary.londontrails.config.interfaces;

import android.content.ComponentCallbacks2;

import com.hubbardgary.londontrails.model.interfaces.IRoute;

public interface IGlobalObjects extends ComponentCallbacks2 {
    IRoute getCurrentRoute();

    void setCurrentRoute(IRoute r);

    CharSequence getButtonText(String title, String subtitle);

    void setMapPreference(int mapType);

    String getStringFromResource(int resourceId);

    int getMapPreference();
}
