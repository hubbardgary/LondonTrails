package com.hubbardgary.londontrails.dataprovider;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.config.interfaces.IGlobalObjects;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.testhelpers.RouteHelpers;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hubbardgary.londontrails.testhelpers.DataProviderHelpers.buildPoiSectionXml;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class POIProviderTest_LinearNonCircularRoute {
    private AssetManager mockAssetManager;
    private Route mockRoute;
    private POIProvider _sut;

    private POI[][] placemarks;

    private static POI[][] getPOIs() {
        POI section1poi1 = new POI("Section 1 POI 1", "Section 1 POI 1 description", 0.12345, 0.12345, false);
        POI section1poi2 = new POI("Section 1 POI 2", "Section 1 POI 2 description", 0.12345, 0.12345, false);
        POI section1poi3 = new POI("Section 1 POI 3", "Section 1 POI 3 description", 0.12345, 0.12345, false);
        POI[] section1poi = { section1poi1, section1poi2, section1poi3 };

        POI section2poi1 = new POI("Section 2 POI 1", "Section 2 POI 1 description", 0.12345, 0.12345, false);
        POI section2poi2 = new POI("Section 2 POI 2", "Section 2 POI 2 description", 0.12345, 0.12345, false);
        POI[] section2poi = { section2poi1, section2poi2 };

        POI section3poi1 = new POI("Section 3 POI 1", "Section 3 POI 1 description", 0.12345, 0.12345, false);
        POI section3poi2 = new POI("Section 3 POI 2", "Section 3 POI 2 description", 0.12345, 0.12345, false);
        POI section3poi3 = new POI("Section 3 POI 3", "Section 3 POI 3 description", 0.12345, 0.12345, false);
        POI section3poi4 = new POI("Section 3 POI 4", "Section 3 POI 4 description", 0.12345, 0.12345, false);
        POI[] section3poi = { section3poi1, section3poi2, section3poi3, section3poi4 };

        POI section4poi1 = new POI("Section 4 POI 1", "Section 4 POI 1 description", 0.12345, 0.12345, false);
        POI section4poi2 = new POI("Section 4 POI 2", "Section 4 POI 2 description", 0.12345, 0.12345, false);
        POI[] section4poi = { section4poi1, section4poi2 };

        return new POI[][] { section1poi, section2poi, section3poi, section4poi};
    }

    @Before
    public void setUp() {
        IGlobalObjects mockGlobals;
        Resources mockResources;
        IRouteOptionsView mockView;

        placemarks = getPOIs();
        String section1poi = buildPoiSectionXml(placemarks[0]);
        String section2poi = buildPoiSectionXml(placemarks[1]);
        String section3poi = buildPoiSectionXml(placemarks[2]);
        String section4poi = buildPoiSectionXml(placemarks[3]);

        mockAssetManager = Mockito.mock(AssetManager.class);
        try {
            when(mockAssetManager.open("t-01-placemarks.kml")).thenReturn(new ByteArrayInputStream(section1poi.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-02-placemarks.kml")).thenReturn(new ByteArrayInputStream(section2poi.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-03-placemarks.kml")).thenReturn(new ByteArrayInputStream(section3poi.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-04-placemarks.kml")).thenReturn(new ByteArrayInputStream(section4poi.getBytes(StandardCharsets.UTF_8)));
        } catch(IOException e) {
            // Swallow any exception.
        }

        mockView = Mockito.mock(IRouteOptionsView.class);
        mockGlobals = Mockito.mock(IGlobalObjects.class);
        mockResources = Mockito.mock(Resources.class);
        mockRoute = Mockito.mock(Route.class);
        when(mockRoute.getShortName()).thenReturn("t");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.isLinear()).thenReturn(true);

        RouteHelpers.setupLinearNonCircularRoute(mockRoute, mockView, mockResources, mockGlobals);
        _sut = new POIProvider();
        _sut.initialize(mockRoute, mockAssetManager);
    }

    @Test
    public void routeFromLocation0ToLocation1_ShouldIncludePlacemarksForSection0() {
        // Arrange
        List<POI> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(placemarks[0]));

        // Act
        List<POI> result = _sut.getPOIsForRoute(0, 1);

        // Assert
        assertEquals(result, expectedResult);
    }

    @Test
    public void routeFromLocation1ToLocation2_ShouldIncludePlacemarksForSection1() {
        // Arrange
        List<POI> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(placemarks[1]));

        // Act
        List<POI> result = _sut.getPOIsForRoute(1, 2);

        // Assert
        assertEquals(result, expectedResult);
    }

    @Test
    public void routeFromLocation2ToLocation3_ShouldIncludePlacemarksForSection2() {
        // Arrange
        List<POI> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(placemarks[2]));

        // Act
        List<POI> result = _sut.getPOIsForRoute(2, 3);

        // Assert
        assertEquals(result, expectedResult);
    }

    @Test
    public void routeFromLocation0ToLocation3_ShouldIncludePlacemarksForSection0To2() {
        // Arrange
        List<POI> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(placemarks[0]));
        expectedResult.addAll(Arrays.asList(placemarks[1]));
        expectedResult.addAll(Arrays.asList(placemarks[2]));

        // Act
        List<POI> result = _sut.getPOIsForRoute(0, 3);

        // Assert
        assertEquals(result, expectedResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation0_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(3, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation0ToLocation0_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation1ToLocation1_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation2ToLocation2_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(2, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation3_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(3, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation4_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(3, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocationMinus1ToLocation0_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getPOIsForRoute(-1, 0);
    }
}
