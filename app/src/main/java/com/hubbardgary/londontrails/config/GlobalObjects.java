package com.hubbardgary.londontrails.config;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.Html;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.model.LondonLoop;
import com.hubbardgary.londontrails.model.Route;

public class GlobalObjects extends Application implements com.hubbardgary.londontrails.config.interfaces.IGlobalObjects {

    private Route currentRoute;
    private static final double kmToMilesMultiplier = 0.62137;

    public Route getCurrentRoute() {
        if (currentRoute != null)
            return currentRoute;

        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE);

        int routeId = preferences.getInt(getString(R.string.shared_prefs_current_route_id), R.id.rte_capital_ring);
        switch (routeId) {
            case R.id.rte_london_loop:
                currentRoute = new LondonLoop();
                break;
            case R.id.rte_capital_ring:
                currentRoute = new CapitalRing();
                break;
            case R.id.rte_green_chain_walk:
                currentRoute = new GreenChainWalk();
                break;
        }
        return currentRoute;
    }

    public void setCurrentRoute(Route r) {
        currentRoute = r;
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE).edit();
        editor.putInt(getString(R.string.shared_prefs_current_route_id), r.getRouteId());
        editor.apply();
    }

    public static int getSectionsFromRouteId(int routeId) {
        switch (routeId) {
            case R.id.rte_capital_ring:
                return R.array.capital_ring_sections;
            case R.id.rte_london_loop:
                return R.array.london_loop_sections;
            case R.id.rte_green_chain_walk:
                return R.array.green_chain_walk_sections;
        }
        return 0;
    }

    public CharSequence getButtonText(String title, String subtitle) {
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
        // Default to Normal if no preference is set
        return preferences.getInt(getString(R.string.shared_prefs_map_type), GoogleMap.MAP_TYPE_NORMAL);
    }

    public static double convertKmToMiles(double km) {
        return km * kmToMilesMultiplier;
    }
}
