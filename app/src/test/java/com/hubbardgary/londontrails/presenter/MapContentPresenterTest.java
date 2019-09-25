package com.hubbardgary.londontrails.presenter;

import android.content.res.AssetManager;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.dataprovider.interfaces.ICoordinateProvider;
import com.hubbardgary.londontrails.dataprovider.interfaces.IPOIProvider;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.view.interfaces.IMapContentView;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class MapContentPresenterTest {
    IMapContentView mockView;
    ICoordinateProvider mockCoordinateProvider;
    IPOIProvider mockPoiProvider;

    private List<POI> poiListWithinWaypointBoundary() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI("title", "snippet", 51.376433, -0.013423, false));
        poiList.add(new POI("title", "snippet", 51.376433, -0.013423, false));
        return poiList;
    }

    private List<POI> poiListOutsideWaypointBoundary() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI("title", "snippet", 54.376433, -3.013423, false));
        poiList.add(new POI("title", "snippet", 50.376433, 4.013423, false));
        return poiList;
    }

    private List<POI> poiListPartlyWithinWaypointBoundary() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI("title", "snippet", 54.376433, -3.013423, false));
        poiList.add(new POI("title", "snippet", 51.376433, -0.013423, false));
        return poiList;
    }

    @Before
    public void setUp() {
        mockView = Mockito.mock(IMapContentView.class);
        mockCoordinateProvider = Mockito.mock(ICoordinateProvider.class);
        mockPoiProvider = Mockito.mock(IPOIProvider.class);

        AssetManager mockAssetManager = Mockito.mock(AssetManager.class);
        when(mockView.getAssetManager()).thenReturn(mockAssetManager);

        double[][] testWaypoints = new double[][] {
                new double[] { 0.063600, 51.387386 },
                new double[] { -0.488226, 51.544458 },
                new double[] { -0.237016, 51.635921 },
                new double[] { 0.076773, 51.220917 }
        };

        when(mockCoordinateProvider.getPathWayPoints(anyInt(), anyInt())).thenReturn(testWaypoints);
    }

    @Test
    public void getMapContentViewModel_Clockwise_StartAndEndLatLonsAreCorrect() {
        // Arrange
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(51.387386, result.startLatitude, 0.01);
        assertEquals(0.063600, result.startLongitude, 0.01);
        assertEquals(51.220917, result.endLatitude, 0.01);
        assertEquals(0.076773, result.endLongitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_AntiClockwise_StartAndEndLatLonsAreCorrect() {
        // Arrange
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 1, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(51.220917, result.startLatitude, 0.01);
        assertEquals(0.076773, result.startLongitude, 0.01);
        assertEquals(51.387386, result.endLatitude, 0.01);
        assertEquals(0.063600, result.endLongitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_Clockwise_WaypointAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(0, 1)).thenReturn(poiListWithinWaypointBoundary());
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(-0.488226, result.minimumLongitude, 0.01);
        assertEquals(0.076773, result.maximumLongitude, 0.01);
        assertEquals(51.220917, result.minimumLatitude, 0.01);
        assertEquals(51.635921, result.maximumLatitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_Clockwise_PoiAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(0, 1)).thenReturn(poiListOutsideWaypointBoundary());
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(-3.013423, result.minimumLongitude, 0.01);
        assertEquals(4.013423, result.maximumLongitude, 0.01);
        assertEquals(50.376433, result.minimumLatitude, 0.01);
        assertEquals(54.376433, result.maximumLatitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_Clockwise_PoiPartlyAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(0, 1)).thenReturn(poiListPartlyWithinWaypointBoundary());
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 0, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(-3.013423, result.minimumLongitude, 0.01);
        assertEquals(0.076773, result.maximumLongitude, 0.01);
        assertEquals(51.220917, result.minimumLatitude, 0.01);
        assertEquals(54.376433, result.maximumLatitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_AntiClockwise_WaypointAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(1, 0)).thenReturn(poiListWithinWaypointBoundary());
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 1, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(-0.488226, result.minimumLongitude, 0.01);
        assertEquals(0.076773, result.maximumLongitude, 0.01);
        assertEquals(51.220917, result.minimumLatitude, 0.01);
        assertEquals(51.635921, result.maximumLatitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_AntiClockwise_PoiAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(1, 0)).thenReturn(poiListOutsideWaypointBoundary());
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 1, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(-3.013423, result.minimumLongitude, 0.01);
        assertEquals(4.013423, result.maximumLongitude, 0.01);
        assertEquals(50.376433, result.minimumLatitude, 0.01);
        assertEquals(54.376433, result.maximumLatitude, 0.01);
    }

    @Test
    public void getMapContentViewModel_AntiClockwise_PoiPartlyAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(1, 0)).thenReturn(poiListPartlyWithinWaypointBoundary());
        ShowMapViewModel vm = new ShowMapViewModel(0, 1, 1, new CapitalRing(), GoogleMap.MAP_TYPE_NORMAL);
        MapContentPresenter sut = new MapContentPresenter(mockView, vm, mockCoordinateProvider, mockPoiProvider);

        // Act
        MapContentViewModel result = sut.getMapContentViewModel();

        // Assert
        assertEquals(-3.013423, result.minimumLongitude, 0.01);
        assertEquals(0.076773, result.maximumLongitude, 0.01);
        assertEquals(51.220917, result.minimumLatitude, 0.01);
        assertEquals(54.376433, result.maximumLatitude, 0.01);
    }
}
