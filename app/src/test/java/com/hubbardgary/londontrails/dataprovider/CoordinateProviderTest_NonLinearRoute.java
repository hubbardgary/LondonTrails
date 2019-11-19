package com.hubbardgary.londontrails.dataprovider;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.Coordinates;
import com.hubbardgary.londontrails.model.dto.RouteCoordinatesDto;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.testhelpers.DataProviderHelpers;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

public class CoordinateProviderTest_NonLinearRoute {
    private CoordinateProvider _sut;

    private List<List<Coordinates>> sectionCoordinates = getSectionCoordinates();
    private List<List<Coordinates>> startLinkCoordinates = getStartLinkCoordinates();
    private List<List<Coordinates>> endLinkCoordinates = getEndLinkCoordinates();

    private static List<List<Coordinates>> getSectionCoordinates() {
        List<Coordinates> section0 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.430962, 0.021436),
                new Coordinates(51.430756, 0.021385),
                new Coordinates(51.430790, 0.019630),
                new Coordinates(51.431526, 0.019548)
        ));
        List<Coordinates> section1 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.433521, 0.011130),
                new Coordinates(51.433224, 0.009731),
                new Coordinates(51.432571, 0.007520),
                new Coordinates(51.430981, 0.008080),
                new Coordinates(51.430538, 0.004745),
                new Coordinates(51.430374, 0.004797)
        ));
        List<Coordinates> section2 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.429192, -0.002370),
                new Coordinates(51.428581, -0.001430),
                new Coordinates(51.426331, -0.000380)
        ));
        List<Coordinates> section3 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.419144, -0.018545),
                new Coordinates(51.419220, -0.018950),
                new Coordinates(51.418781, -0.019510),
                new Coordinates(51.419102, -0.020390),
                new Coordinates(51.419392, -0.020690),
                new Coordinates(51.420532, -0.021653),
                new Coordinates(51.419910, -0.022280),
                new Coordinates(51.418442, -0.023230)
        ));
        List<List<Coordinates>> sectionCoordinates = new ArrayList<>();
        sectionCoordinates.add(section0);
        sectionCoordinates.add(section1);
        sectionCoordinates.add(section2);
        sectionCoordinates.add(section3);
        return sectionCoordinates;
    }

    private static List<List<Coordinates>> getStartLinkCoordinates() {
        List<Coordinates> section0 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.490021, 0.068930),
                new Coordinates(51.490082, 0.068830),
                new Coordinates(51.490452, 0.069440)
        ));
        List<Coordinates> section1 = new ArrayList<>();
        List<Coordinates> section2 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.430950, 0.021400),
                new Coordinates(51.430920, 0.021527)
        ));
        List<Coordinates> section3 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.418846, -0.135785),
                new Coordinates(51.419224, -0.136082),
                new Coordinates(51.419315, -0.136031),
                new Coordinates(51.420238, -0.136935)
        ));
        List<List<Coordinates>> sectionCoordinates = new ArrayList<>();
        sectionCoordinates.add(section0);
        sectionCoordinates.add(section1);
        sectionCoordinates.add(section2);
        sectionCoordinates.add(section3);
        return sectionCoordinates;
    }

    private static List<List<Coordinates>> getEndLinkCoordinates() {
        List<Coordinates> section0 = new ArrayList<>();
        List<Coordinates> section1 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.430920, 0.021527),
                new Coordinates(51.430950, 0.021400)
        ));
        List<Coordinates> section2 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.420238, -0.136935),
                new Coordinates(51.419315, -0.136031),
                new Coordinates(51.419224, -0.136082),
                new Coordinates(51.418846, -0.135785)
        ));
        List<Coordinates> section3 = new ArrayList<>(Arrays.asList(
                new Coordinates(51.490452, 0.069440),
                new Coordinates(51.490082, 0.068830),
                new Coordinates(51.490021, 0.068930)
        ));
        List<List<Coordinates>> sectionCoordinates = new ArrayList<>();
        sectionCoordinates.add(section0);
        sectionCoordinates.add(section1);
        sectionCoordinates.add(section2);
        sectionCoordinates.add(section3);
        return sectionCoordinates;
    }

    @Before
    public void setUp() {
        IUserSettings mockUserSettings;
        IRouteOptionsView mockView;
        AssetManager mockAssetManager = Mockito.mock(AssetManager.class);

        String section1Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates.get(0));
        String section2Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates.get(1));
        String section3Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates.get(2));
        String section4Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates.get(3));

        String startLink1Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(startLinkCoordinates.get(0));
        String startLink3Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(startLinkCoordinates.get(2));
        String startLink4Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(startLinkCoordinates.get(3));

        String endLink2Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(endLinkCoordinates.get(1));
        String endLink3Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(endLinkCoordinates.get(2));
        String endLink4Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(endLinkCoordinates.get(3));

        try {
            when(mockAssetManager.open("t-01-route.kml")).thenReturn(new ByteArrayInputStream(section1Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-02-route.kml")).thenReturn(new ByteArrayInputStream(section2Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-03-route.kml")).thenReturn(new ByteArrayInputStream(section3Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-04-route.kml")).thenReturn(new ByteArrayInputStream(section4Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-01-start_link.kml")).thenReturn(new ByteArrayInputStream(startLink1Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-03-start_link.kml")).thenReturn(new ByteArrayInputStream(startLink3Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-04-start_link.kml")).thenReturn(new ByteArrayInputStream(startLink4Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-02-end_link.kml")).thenReturn(new ByteArrayInputStream(endLink2Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-03-end_link.kml")).thenReturn(new ByteArrayInputStream(endLink3Coordinates.getBytes(StandardCharsets.UTF_8)));
            when(mockAssetManager.open("t-04-end_link.kml")).thenReturn(new ByteArrayInputStream(endLink4Coordinates.getBytes(StandardCharsets.UTF_8)));

            when(mockAssetManager.list("")).thenReturn(new String[] {
                    "t-01-route.kml", "t-02-route.kml", "t-03-route.kml", "t-04-route.kml",
                    "t-01-start_link.kml", "t-03-start_link.kml", "t-04-start_link.kml",
                    "t-02-end_link.kml", "t-03-end_link.kml", "t-04-end_link.kml"
            });
        } catch(IOException e) {
            // Swallow any exception.
        }

        mockView = Mockito.mock(IRouteOptionsView.class);
        mockUserSettings = Mockito.mock(IUserSettings.class);
        IRoute mockRoute = Mockito.mock(IRoute.class);
        when(mockRoute.getShortName()).thenReturn("t");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.isLinear()).thenReturn(false);

        RouteHelpers.setupNonLinearRoute(mockRoute, mockView, mockUserSettings);
        _sut = new CoordinateProvider();
        _sut.initialize(mockRoute, mockAssetManager);
    }

    @Test
    public void routeFromLocation0ToLocation0_ShouldIncludeRouteAndLinksForSection0() {
        // Arrange
        List<Coordinates> expectedResult = new ArrayList<>();
        expectedResult.addAll(startLinkCoordinates.get(0));
        expectedResult.addAll(sectionCoordinates.get(0));
        // Section 0 has no end link

        // Act
        RouteCoordinatesDto result = _sut.getRouteCoordinates(0, 0);

        // Assert
        assertNotNull(result);
        assertEquals(result.getCoordinates(), expectedResult);
    }

    @Test
    public void routeFromLocation1ToLocation1_ShouldIncludeRouteAndLinksForSection1() {
        // Arrange
        List<Coordinates> expectedResult = new ArrayList<>();
        // Section 1 has no start link
        expectedResult.addAll(sectionCoordinates.get(1));
        expectedResult.addAll(endLinkCoordinates.get(1));

        // Act
        RouteCoordinatesDto result = _sut.getRouteCoordinates(1, 1);

        // Assert
        assertNotNull(result);
        assertEquals(result.getCoordinates(), expectedResult);
    }

    @Test
    public void routeFromLocation2ToLocation2_ShouldIncludeRouteAndLinksForSection2() {
        // Arrange
        List<Coordinates> expectedResult = new ArrayList<>();
        expectedResult.addAll(startLinkCoordinates.get(2));
        expectedResult.addAll(sectionCoordinates.get(2));
        expectedResult.addAll(endLinkCoordinates.get(2));

        // Act
        RouteCoordinatesDto result = _sut.getRouteCoordinates(2, 2);

        // Assert
        assertNotNull(result);
        assertEquals(result.getCoordinates(), expectedResult);
    }

    @Test
    public void routeFromLocation3ToLocation3_ShouldIncludeRouteAndLinksForSection3() {
        // Arrange
        List<Coordinates> expectedResult = new ArrayList<>();
        expectedResult.addAll(startLinkCoordinates.get(3));
        expectedResult.addAll(sectionCoordinates.get(3));
        expectedResult.addAll(endLinkCoordinates.get(3));

        // Act
        RouteCoordinatesDto result = _sut.getRouteCoordinates(3, 3);

        // Assert
        assertNotNull(result);
        assertEquals(result.getCoordinates(), expectedResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation0_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getRouteCoordinates(3, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocationMinus1ToLocation0_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getRouteCoordinates(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation0ToLocation1_ShouldThrowIllegalArgumentException() {
        // Act
        _sut.getRouteCoordinates(0, 1);
    }
}
