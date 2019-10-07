package com.hubbardgary.londontrails.presenter;

import android.content.res.AssetManager;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.dataprovider.interfaces.ICoordinateProvider;
import com.hubbardgary.londontrails.dataprovider.interfaces.IPOIProvider;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.Coordinates;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.model.dto.RoutePoiDto;
import com.hubbardgary.londontrails.model.dto.RouteCoordinatesDto;
import com.hubbardgary.londontrails.view.interfaces.IMapContentView;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class MapContentPresenterTest {
    private IMapContentView mockView;
    private ICoordinateProvider mockCoordinateProvider;
    private IPOIProvider mockPoiProvider;

    private RoutePoiDto poiListWithinRouteBoundary() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI("title", "snippet", 51.376433, -0.013423, false));
        poiList.add(new POI("title", "snippet", 51.376433, -0.013423, false));

        RoutePoiDto poiDto = new RoutePoiDto();
        poiDto.setPOIs(poiList);
        poiDto.setMaximumLatitude(51.376433);
        poiDto.setMinimumLatitude(51.376433);
        poiDto.setMaximumLongitude(-0.013423);
        poiDto.setMinimumLongitude(-0.013423);
        return poiDto;
    }

    private RoutePoiDto poiListOutsideRouteBoundary() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI("title", "snippet", 54.376433, -3.013423, false));
        poiList.add(new POI("title", "snippet", 50.376433, 4.013423, false));

        RoutePoiDto poiDto = new RoutePoiDto();
        poiDto.setPOIs(poiList);
        poiDto.setMaximumLatitude(54.376433);
        poiDto.setMinimumLatitude(50.376433);
        poiDto.setMaximumLongitude(4.013423);
        poiDto.setMinimumLongitude(-3.013423);
        return poiDto;
    }

    private RoutePoiDto poiListPartlyWithinRouteBoundary() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI("title", "snippet", 54.376433, -3.013423, false));
        poiList.add(new POI("title", "snippet", 51.376433, -0.013423, false));

        RoutePoiDto poiDto = new RoutePoiDto();
        poiDto.setPOIs(poiList);
        poiDto.setMaximumLatitude(54.376433);
        poiDto.setMinimumLatitude(51.376433);
        poiDto.setMaximumLongitude(-0.013423);
        poiDto.setMinimumLongitude(-3.013423);
        return poiDto;
    }

    @Before
    public void setUp() {
        mockView = Mockito.mock(IMapContentView.class);
        mockCoordinateProvider = Mockito.mock(ICoordinateProvider.class);
        mockPoiProvider = Mockito.mock(IPOIProvider.class);

        AssetManager mockAssetManager = Mockito.mock(AssetManager.class);
        when(mockView.getAssetManager()).thenReturn(mockAssetManager);

        RouteCoordinatesDto coordinatesDto = new RouteCoordinatesDto();
        coordinatesDto.setCoordinates(new ArrayList<Coordinates>(Arrays.asList(
                new Coordinates(51.387386, 0.063600),
                new Coordinates(51.544458, -0.488226),
                new Coordinates(51.635921, -0.237016),
                new Coordinates(51.220917, 0.076773)
        )));

        coordinatesDto.setMaximumLatitude(51.635921);
        coordinatesDto.setMinimumLatitude(51.220917);
        coordinatesDto.setMaximumLongitude(0.076773);
        coordinatesDto.setMinimumLongitude(-0.488226);

        when(mockCoordinateProvider.getRouteCoordinates(anyInt(), anyInt())).thenReturn(coordinatesDto);
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
    public void getMapContentViewModel_Clockwise_RouteAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(0, 1)).thenReturn(poiListWithinRouteBoundary());
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
        when(mockPoiProvider.getPOIsForRoute(0, 1)).thenReturn(poiListOutsideRouteBoundary());
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
        when(mockPoiProvider.getPOIsForRoute(0, 1)).thenReturn(poiListPartlyWithinRouteBoundary());
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
    public void getMapContentViewModel_AntiClockwise_RouteAtOuterBoundary_MaxMinLatLonsAreCorrect() {
        // Arrange
        when(mockPoiProvider.getPOIsForRoute(1, 0)).thenReturn(poiListWithinRouteBoundary());
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
        when(mockPoiProvider.getPOIsForRoute(1, 0)).thenReturn(poiListOutsideRouteBoundary());
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
        when(mockPoiProvider.getPOIsForRoute(1, 0)).thenReturn(poiListPartlyWithinRouteBoundary());
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
