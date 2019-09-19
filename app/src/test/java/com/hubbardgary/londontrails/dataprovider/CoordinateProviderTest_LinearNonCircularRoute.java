package com.hubbardgary.londontrails.dataprovider;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.model.Route;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class CoordinateProviderTest_LinearNonCircularRoute {
    private AssetManager mockAssetManager;
    private Route mockRoute;
    private CoordinateProvider _sut;

    private double[][][] sectionCoordinates = getSectionCoordinates();
    private double[][][] startLinkCoordinates = getStartLinkCoordinates();
    private double[][][] endLinkCoordinates = getEndLinkCoordinates();

    private static double[][][] getSectionCoordinates() {
        double[][] section1 = new double[][] {
                new double[] { 0.021436,51.430962 },
                new double[] { 0.021385,51.430756 },
                new double[] { 0.019630,51.430790 },
                new double[] { 0.019548,51.431526 }
        };
        double[][] section2 = new double[][] {
                new double[] { 0.011130,51.433521 },
                new double[] { 0.009731,51.433224 },
                new double[] { 0.007520,51.432571 },
                new double[] { 0.008080,51.430981 },
                new double[] { 0.004745,51.430538 },
                new double[] { 0.004797,51.430374 }
        };
        double[][] section3 = new double[][] {
                new double[] { -0.002370,51.429192 },
                new double[] { -0.001430,51.428581 },
                new double[] { -0.000380,51.426331 }
        };
        double[][] section4 = new double[][] {
                new double[] { -0.018545,51.419144 },
                new double[] { -0.018950,51.419220 },
                new double[] { -0.019510,51.418781 },
                new double[] { -0.020390,51.419102 },
                new double[] { -0.020690,51.419392 },
                new double[] { -0.021653,51.420532 },
                new double[] { -0.022280,51.419910 },
                new double[] { -0.023230,51.418442 }
        };
        return new double[][][] { section1, section2, section3, section4 };
    }

    private static double [][][] getStartLinkCoordinates() {
        double[][] section1 = new double[][] {
                new double[] { 0.068930,51.490021 },
                new double[] { 0.068830,51.490082 },
                new double[] { 0.069440,51.490452 }
        };
        double[][] section2 = new double[][] {};
        double[][] section3 = new double[][] {
                new double[] { 0.021400,51.430950 },
                new double[] { 0.021527,51.430920 }
        };
        double[][] section4 = new double[][] {
                new double[] { -0.135785,51.418846 },
                new double[] { -0.136082,51.419224 },
                new double[] { -0.136031,51.419315 },
                new double[] { -0.136935,51.420238 }
        };
        return new double[][][] { section1, section2, section3, section4 };
    }

    private static double [][][] getEndLinkCoordinates() {
        double[][] section1 = new double[][] {};
        double[][] section2 = new double[][] {
                new double[] { 0.021527,51.430920 },
                new double[] { 0.021400,51.430950 }
        };
        double[][] section3 = new double[][] {
                new double[] { -0.136935,51.420238 },
                new double[] { -0.136031,51.419315 },
                new double[] { -0.136082,51.419224 },
                new double[] { -0.135785,51.418846 }
        };
        double[][] section4 = new double[][] {
                new double[] { 0.069440,51.490452 },
                new double[] { 0.068830,51.490082 },
                new double[] { 0.068930,51.490021 }
        };
        return new double[][][] { section1, section2, section3, section4 };
    }

    @Before
    public void setUp() {
        GlobalObjects mockGlobals;
        Resources mockResources;
        IRouteOptionsView mockView;

        String section1Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates[0]);
        String section2Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates[1]);
        String section3Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates[2]);
        String section4Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(sectionCoordinates[3]);

        String startLink1Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(startLinkCoordinates[0]);
        String startLink3Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(startLinkCoordinates[2]);
        String startLink4Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(startLinkCoordinates[3]);

        String endLink2Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(endLinkCoordinates[1]);
        String endLink3Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(endLinkCoordinates[2]);
        String endLink4Coordinates = DataProviderHelpers.buildSectionCoordinatesXml(endLinkCoordinates[3]);

        mockAssetManager = Mockito.mock(AssetManager.class);
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
        mockGlobals = Mockito.mock(GlobalObjects.class);
        mockResources = Mockito.mock(Resources.class);
        mockRoute = Mockito.mock(Route.class);
        when(mockRoute.getShortName()).thenReturn("t");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.isLinear()).thenReturn(true);

        RouteHelpers.setupLinearNonCircularRoute(mockRoute, mockView, mockResources, mockGlobals);
    }

    @Test
    public void routeFromLocation0ToLocation1_ShouldIncludeCoordinatesAndLinksForSection0() {
        // Arrange
        List<double[]> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(startLinkCoordinates[0]));
        expectedResult.addAll(Arrays.asList(sectionCoordinates[0]));
        // Section 0 has no end link
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 0, 1);

        // Act
        double[][] result = _sut.getPathWayPoints();

        // Assert
        assertTrue(Arrays.deepEquals(result, expectedResult.toArray()));
    }

    @Test
    public void routeFromLocation1ToLocation2_ShouldIncludeCoordinatesAndLinksForSection1() {
        // Arrange
        List<double[]> expectedResult = new ArrayList<>();
        // Section 1 has no start link
        expectedResult.addAll(Arrays.asList(sectionCoordinates[1]));
        expectedResult.addAll(Arrays.asList(endLinkCoordinates[1]));
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 1, 2);

        // Act
        double[][] result = _sut.getPathWayPoints();

        // Assert
        assertTrue(Arrays.deepEquals(result, expectedResult.toArray()));
    }

    @Test
    public void routeFromLocation2ToLocation3_ShouldIncludeCoordinatesAndLinksForSection2() {
        // Arrange
        List<double[]> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(startLinkCoordinates[2]));
        expectedResult.addAll(Arrays.asList(sectionCoordinates[2]));
        expectedResult.addAll(Arrays.asList(endLinkCoordinates[2]));
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 2, 3);

        // Act
        double[][] result = _sut.getPathWayPoints();

        // Assert
        assertTrue(Arrays.deepEquals(result, expectedResult.toArray()));
    }

    @Test
    public void routeFromLocation0ToLocation3_ShouldIncludeCoordinatesAndLinksForSection0To2() {
        // Arrange
        List<double[]> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(startLinkCoordinates[0]));
        expectedResult.addAll(Arrays.asList(sectionCoordinates[0]));
        expectedResult.addAll(Arrays.asList(sectionCoordinates[1]));
        expectedResult.addAll(Arrays.asList(sectionCoordinates[2]));
        expectedResult.addAll(Arrays.asList(endLinkCoordinates[2]));
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 0, 3);

        // Act
        double[][] result = _sut.getPathWayPoints();

        // Assert
        assertTrue(Arrays.deepEquals(result, expectedResult.toArray()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation0_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 3, 0);

        // Act
        _sut.getPathWayPoints();
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation0ToLocation0_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 0, 0);

        // Act
        _sut.getPathWayPoints();
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation1ToLocation1_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 1, 1);

        // Act
        _sut.getPathWayPoints();
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation2ToLocation2_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 2, 2);

        // Act
        _sut.getPathWayPoints();
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation3_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 3, 3);

        // Act
        _sut.getPathWayPoints();
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocation3ToLocation4_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, 3, 4);

        // Act
        _sut.getPathWayPoints();
    }

    @Test(expected = IllegalArgumentException.class)
    public void routeFromLocationMinus1ToLocation0_ShouldThrowIllegalArgumentException() {
        // Arrange
        _sut = new CoordinateProvider(mockRoute, mockAssetManager, -1, 0);

        // Act
        _sut.getPathWayPoints();
    }
}