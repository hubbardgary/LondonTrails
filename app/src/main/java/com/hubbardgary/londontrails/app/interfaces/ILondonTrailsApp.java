package com.hubbardgary.londontrails.app.interfaces;

public interface ILondonTrailsApp {
    String getStringFromResource(int resourceId);
    int getIntFromSharedPreferences(int key, int defaultValue);
    void saveIntToSharedPreference(int key, int value);
}
