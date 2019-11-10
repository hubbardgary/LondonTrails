package com.hubbardgary.londontrails.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.LondonLoop;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.view.interfaces.IShowMapView;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowMapPresenterTest {

    private ShowMapPresenter sut;
    private IShowMapView mockView;
    private IUserSettings mockUserSettings;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IShowMapView.class);
        mockUserSettings = Mockito.mock(IUserSettings.class);
    }

    private void setUpWalk(int start, int end, int direction, IRoute route, int mapPreference) {
        when(mockView.getIntFromIntent("startSection")).thenReturn(start);
        when(mockView.getIntFromIntent("endSection")).thenReturn(end);
        when(mockView.getIntFromIntent("direction")).thenReturn(direction);
        when(mockUserSettings.getCurrentRoute()).thenReturn(route);
        when(mockUserSettings.getMapPreference()).thenReturn(mapPreference);
    }

    @Test
    public void initializePresenter_ClockwiseWalk_ViewModelIsSetUpCorrectly() {
        // Arrange
        setUpWalk(1, 2, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);

        // Act
        sut = new ShowMapPresenter(mockView, mockUserSettings);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertEquals(result.start, 1);
        assertEquals(result.end, 2);
        assertEquals(result.isClockwise, true);
        assertEquals(result.name, "Capital Ring: Section 2 - 3");
    }

    @Test
    public void initializePresenter_AntiClockwiseWalk_ViewModelIsSetUpCorrectly() {
        // Arrange
        setUpWalk(5, 1, 1, new LondonLoop(), GoogleMap.MAP_TYPE_NORMAL);

        // Act
        sut = new ShowMapPresenter(mockView, mockUserSettings);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertEquals(result.start, 5);
        assertEquals(result.end, 1);
        assertEquals(result.isClockwise, false);
        assertEquals(result.name, "London Loop: Section 6 - 2");
    }

    @Test
    public void initializePresenter_OptionsMenuIsSetupCorrectly() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);

        // Act
        sut = new ShowMapPresenter(mockView, mockUserSettings);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertNotNull(result.optionsMenu);
        assertEquals(2, result.optionsMenu.menuItems.size());
        assertTrue(result.optionsMenu.menuItems.containsKey(R.id.view_option_hide_markers));
        assertTrue(result.optionsMenu.menuItems.containsKey(R.id.view_option_reset_focus));
        assertFalse(result.optionsMenu.menuItems.containsKey(R.id.view_option_show_markers));
    }

    @Test
    public void initializePresenter_MapPreferenceNormal_MapTypeMenuIsSetupCorrectly() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);

        // Act
        sut = new ShowMapPresenter(mockView, mockUserSettings);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertNotNull(result.mapTypeSubMenu);
        assertEquals(2, result.mapTypeSubMenu.menuItems.size());
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_satellite));
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_terrain));
        assertFalse(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_streetmap));
    }

    @Test
    public void initializePresenter_MapPreferenceHybrid_MapTypeMenuIsSetupCorrectly() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_HYBRID);

        // Act
        sut = new ShowMapPresenter(mockView, mockUserSettings);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertNotNull(result.mapTypeSubMenu);
        assertEquals(2, result.mapTypeSubMenu.menuItems.size());
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_streetmap));
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_terrain));
        assertFalse(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_satellite));
    }

    @Test
    public void initializePresenter_MapPreferenceTerrain_MapTypeMenuIsSetupCorrectly() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_TERRAIN);

        // Act
        sut = new ShowMapPresenter(mockView, mockUserSettings);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertNotNull(result.mapTypeSubMenu);
        assertEquals(2, result.mapTypeSubMenu.menuItems.size());
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_streetmap));
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_satellite));
        assertFalse(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_terrain));
    }

    @Test
    public void menuItemSelected_HideMarkers_HidesMarkersAndUpdatesMenu() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(R.id.view_option_hide_markers);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertFalse(result.markersVisible);
        verify(mockView).setMarkerVisibility(false);
        assertTrue(result.optionsMenu.menuItems.containsKey(R.id.view_option_show_markers));
        assertFalse(result.optionsMenu.menuItems.containsKey(R.id.view_option_hide_markers));
    }

    @Test
    public void menuItemSelected_ResetFocus_InvokesCorrectMethodOnView() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(R.id.view_option_reset_focus);

        // Assert
        verify(mockView).resetCameraPosition();
    }

    @Test
    public void menuItemSelected_HideThenShowMarkers_ShowsMarkersAndUpdatesMenu() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(R.id.view_option_hide_markers);
        sut.menuItemSelected(R.id.view_option_show_markers);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertTrue(result.markersVisible);
        verify(mockView).setMarkerVisibility(false);
        verify(mockView).setMarkerVisibility(true);
        assertTrue(result.optionsMenu.menuItems.containsKey(R.id.view_option_hide_markers));
        assertFalse(result.optionsMenu.menuItems.containsKey(R.id.view_option_show_markers));
    }

    @Test
    public void menuItemSelected_StreetMap_SetsMapTypeAndUpdatesMenu() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_TERRAIN);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(R.id.view_option_streetmap);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertEquals(GoogleMap.MAP_TYPE_NORMAL, result.mapType);
        verify(mockView).setMapType(GoogleMap.MAP_TYPE_NORMAL);
        verify(mockUserSettings).setMapPreference(GoogleMap.MAP_TYPE_NORMAL);
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_satellite));
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_terrain));
        assertFalse(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_streetmap));
    }

    @Test
    public void menuItemSelected_Terrain_SetsMapTypeAndUpdatesMenu() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(R.id.view_option_terrain);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertEquals(GoogleMap.MAP_TYPE_TERRAIN, result.mapType);
        verify(mockView).setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        verify(mockUserSettings).setMapPreference(GoogleMap.MAP_TYPE_TERRAIN);
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_streetmap));
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_satellite));
        assertFalse(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_terrain));
    }

    @Test
    public void menuItemSelected_Satellite_SetsMapTypeAndUpdatesMenu() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(R.id.view_option_satellite);
        ShowMapViewModel result = sut.getViewModel();

        // Assert
        assertEquals(GoogleMap.MAP_TYPE_HYBRID, result.mapType);
        verify(mockView).setMapType(GoogleMap.MAP_TYPE_HYBRID);
        verify(mockUserSettings).setMapPreference(GoogleMap.MAP_TYPE_HYBRID);
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_streetmap));
        assertTrue(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_terrain));
        assertFalse(result.mapTypeSubMenu.menuItems.containsKey(R.id.view_option_satellite));
    }

    @Test
    public void menuItemSelected_Home_EndsActivity() {
        // Arrange
        setUpWalk(2, 3, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        sut = new ShowMapPresenter(mockView, mockUserSettings);

        // Act
        sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }
}
