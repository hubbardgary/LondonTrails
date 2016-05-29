package com.hubbardgary.londontrails.config;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.Html;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.Route;

public class GlobalObjects extends Application {

    private Route currentRoute;
    private static final double kmToMilesMultiplier = 0.62137;

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route r) {
        currentRoute = r;
    }

    public static int getSectionsFromRouteId(int routeId) {
        switch (routeId) {
            case R.id.rte_capital_ring:
                return R.array.capital_ring_sections;
            case R.id.rte_london_loop:
                return R.array.london_loop_sections;
        }
        return 0;
    }

    public static CharSequence getButtonText(String title, String subtitle) {
        return Html.fromHtml("<b><big>" + title + "</big></b>" + "<br />" + "<small>" + subtitle + "</small>");
    }

    public void setMapPreference(int mapType) {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE).edit();
        editor.putInt(getString(R.string.shared_prefs_map_type), mapType);
        editor.apply();
    }

    public String getStringFromResource(int resourceId) {
        return getString(resourceId);
    }

    public int getMapPreference() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE);
        // Default to Terrain if no preference is set
        return preferences.getInt(getString(R.string.shared_prefs_map_type), GoogleMap.MAP_TYPE_TERRAIN);
    }

    public static double convertKmToMiles(double km) {
        return km * kmToMilesMultiplier;
    }
}
