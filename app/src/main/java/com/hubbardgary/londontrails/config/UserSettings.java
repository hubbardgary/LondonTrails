package com.hubbardgary.londontrails.config;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.app.interfaces.ILondonTrailsApp;
import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.model.LondonLoop;
import com.hubbardgary.londontrails.model.interfaces.IRoute;

public class UserSettings implements IUserSettings {

    private ILondonTrailsApp app;
    private IRoute currentRoute;

    public UserSettings(ILondonTrailsApp app) {
        this.app = app;
    }

    @Override
    public IRoute getCurrentRoute() {
        if (currentRoute != null)
            return currentRoute;

        int routeId = app.getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring);
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

    @Override
    public void setCurrentRoute(IRoute r) {
        currentRoute = r;
        app.saveIntToSharedPreference(R.string.shared_prefs_current_route_id, r.getRouteId());
    }

    @Override
    public int getMapPreference() {
        return app.getIntFromSharedPreferences(R.string.shared_prefs_map_type, GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void setMapPreference(int mapType) {
        app.saveIntToSharedPreference(R.string.shared_prefs_map_type, mapType);
    }
}
