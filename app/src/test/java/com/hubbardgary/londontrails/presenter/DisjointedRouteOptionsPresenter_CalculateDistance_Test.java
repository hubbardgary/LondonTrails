package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.testhelpers.RouteHelpers;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DisjointedRouteOptionsPresenter_CalculateDistance_Test {

    private IUserSettings mockUserSettings;
    private IRouteOptionsView mockView;
    private IRoute mockRoute;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IRouteOptionsView.class);
        mockUserSettings = Mockito.mock(IUserSettings.class);
        mockRoute = Mockito.mock(IRoute.class);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][] {
                // section, expectedDistanceKm, expectedDistanceMiles, extensionDistanceKm, extensionDistanceMiles
                {  0,       6.2,                3.85,                  0.0,                 0.0 },
                {  1,       8.0,                4.97,                  0.8,                 0.5  },
                {  2,       11.2,               6.96,                  1.5,                 0.93 },
                {  3,       7.7,                4.78,                  0.0,                 0.0 }
        });
    }

    @Parameterized.Parameter
    public int section;

    @Parameterized.Parameter(1)
    public double expectedDistanceKm;

    @Parameterized.Parameter(2)
    public double expectedDistanceMiles;

    @Parameterized.Parameter(3)
    public double expectedExtensionDistanceKm;

    @Parameterized.Parameter(4)
    public double expectedExtensionDistanceMiles;

    @Test
    public void optionsChanged_LinearNonCircularRoute_ShouldCalculateCorrectDistance() {
        // Arrange
        RouteHelpers.setupNonLinearRoute(mockRoute, mockView, mockUserSettings);
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = section;
        vm.endSection = section;
        DisjointedRouteOptionsPresenter _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        vm = _sut.optionsChanged(vm);

        // Assert
        assertEquals(expectedDistanceKm, vm.distanceKm, 0.01);
        assertEquals(expectedDistanceMiles, vm.distanceMiles, 0.01);
        assertEquals(expectedExtensionDistanceKm, vm.extensionDistanceKm, 0.01);
        assertEquals(expectedExtensionDistanceMiles, vm.extensionDistanceMiles, 0.01);
    }
}
