package com.hubbardgary.londontrails.config;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.app.interfaces.ILondonTrailsApp;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.model.LondonLoop;
import com.hubbardgary.londontrails.model.interfaces.IRoute;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserSettingsTest {

    private UserSettings _sut;
    private ILondonTrailsApp mockLondonTrailsApp;

    @Before
    public void setUp() {
        mockLondonTrailsApp = Mockito.mock(ILondonTrailsApp.class);
        _sut = new UserSettings(mockLondonTrailsApp);
    }

    @Test
    public void getCurrentRoute_CapitalRing_ReturnsCapitalRing() {
        // Arrange
        when(mockLondonTrailsApp.getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring)).thenReturn(R.id.rte_capital_ring);

        // Act
        IRoute result = _sut.getCurrentRoute();

        // Assert
        assertTrue(result instanceof CapitalRing);
        verify(mockLondonTrailsApp).getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring);
    }

    @Test
    public void getCurrentRoute_LondonLoop_ReturnsLondonLoop() {
        // Arrange
        when(mockLondonTrailsApp.getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring)).thenReturn(R.id.rte_london_loop);

        // Act
        IRoute result = _sut.getCurrentRoute();

        // Assert
        assertTrue(result instanceof LondonLoop);
        verify(mockLondonTrailsApp).getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring);
    }

    @Test
    public void getCurrentRoute_GreenChainWalk_ReturnsGreenChainWalk() {
        // Arrange
        when(mockLondonTrailsApp.getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring)).thenReturn(R.id.rte_green_chain_walk);

        // Act
        IRoute result = _sut.getCurrentRoute();

        // Assert
        assertTrue(result instanceof GreenChainWalk);
        verify(mockLondonTrailsApp).getIntFromSharedPreferences(R.string.shared_prefs_current_route_id, R.id.rte_capital_ring);
    }

    @Test
    public void getCurrentRoute_RouteHasBeenPreviouslySet_ReturnsPreviouslySetRoute() {
        // Act
        _sut.setCurrentRoute(new GreenChainWalk());
        IRoute result = _sut.getCurrentRoute();

        // Assert
        assertTrue(result instanceof GreenChainWalk);
        verify(mockLondonTrailsApp, never()).getIntFromSharedPreferences(anyInt(), anyInt());
    }

    @Test
    public void setCurrentRoute_SavesIntToSharedPreferences() {
        // Act
        IRoute route = new LondonLoop();
        _sut.setCurrentRoute(route);

        // Assert
        verify(mockLondonTrailsApp).saveIntToSharedPreference(R.string.shared_prefs_current_route_id, route.getRouteId());
    }

    @Test
    public void getMapPreference_RetrievesMapPreference() {
        // Arrange
        int preferredMapType = GoogleMap.MAP_TYPE_TERRAIN;
        when(mockLondonTrailsApp.getIntFromSharedPreferences(R.string.shared_prefs_map_type, GoogleMap.MAP_TYPE_NORMAL)).thenReturn(preferredMapType);

        // Act
        int result = _sut.getMapPreference();

        // Assert
        assertEquals(preferredMapType, result);
        verify(mockLondonTrailsApp).getIntFromSharedPreferences(R.string.shared_prefs_map_type, GoogleMap.MAP_TYPE_NORMAL);
    }

    @Test
    public void setMapPreference_SavesIntToSharedPreferences() {
        // Arrange
        int mapType = GoogleMap.MAP_TYPE_SATELLITE;

        // Act
        _sut.setMapPreference(mapType);

        // Assert
        verify(mockLondonTrailsApp).saveIntToSharedPreference(R.string.shared_prefs_map_type, mapType);
    }
}
