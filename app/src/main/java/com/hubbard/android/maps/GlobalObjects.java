package com.hubbard.android.maps;

import java.util.HashMap;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.Html;

public class GlobalObjects extends Application {

    private Route currentRoute;
    private HashMap<String, Integer> hshSectionMap;
    private static final double kmToMilesMultiplier = 0.62137;

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route r) {
        currentRoute = r;
    }

    public HashMap<String, Integer> getSectionMap() {
        return hshSectionMap;
    }

    public void setSectionMap(HashMap<String, Integer> h) {
        hshSectionMap = h;
    }

    public static CharSequence getButtonText(String title, String subtitle) {
        return Html.fromHtml("<b><big>" + title + "</big></b>" + "<br />" + "<small>" + subtitle + "</small>");
    }
    public static CharSequence getButtonText(String title) {
        return Html.fromHtml("<b><big>" + title + "</big></b>");
    }

    public void setMapPreference(int mapType) {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE).edit();
        editor.putInt(getString(R.string.shared_prefs_map_type), mapType);
        editor.apply();
    }

    public static double convertKmToMiles(double km) {
        return km * kmToMilesMultiplier;
    }
}
