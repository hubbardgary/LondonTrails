package com.hubbardgary.londontrails.config.interfaces;

import com.hubbardgary.londontrails.model.interfaces.IRoute;

public interface IUserSettings {
    IRoute getCurrentRoute();
    void setCurrentRoute(IRoute r);
    int getMapPreference();
    void setMapPreference(int mapType);
}
