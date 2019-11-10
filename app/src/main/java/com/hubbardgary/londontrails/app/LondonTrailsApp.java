package com.hubbardgary.londontrails.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.app.interfaces.ILondonTrailsApp;

public class LondonTrailsApp extends Application implements ILondonTrailsApp {

    @Override
    public String getStringFromResource(int resourceId) {
        return getString(resourceId);
    }

    @Override
    public int getIntFromSharedPreferences(int key, int defaultValue) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getInt(getStringFromResource(key), defaultValue);
    }

    @Override
    public void saveIntToSharedPreference(int key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(getString(key), value);
        editor.apply();
    }

    private SharedPreferences getSharedPreferences() {
        int sharedPreferencesId = R.string.shared_preferences_name;
        return getSharedPreferences(getString(sharedPreferencesId), MODE_PRIVATE);
    }
}
