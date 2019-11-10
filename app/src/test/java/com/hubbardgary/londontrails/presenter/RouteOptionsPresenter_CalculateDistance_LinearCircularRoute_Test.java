package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class RouteOptionsPresenter_CalculateDistance_LinearCircularRoute_Test {
    private IUserSettings mockUserSettings;
    private IRouteOptionsView mockView;
    private IRoute mockRoute;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IRouteOptionsView.class);
        mockUserSettings = Mockito.mock(IUserSettings.class);
        mockRoute = Mockito.mock(IRoute.class);

        when(mockView.getIntegerFromResources(R.integer.Clockwise)).thenReturn(0);
        when(mockView.getIntegerFromResources(R.integer.AntiClockwise)).thenReturn(1);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][] {
                // Clockwise routes
                // startSection, endSection, direction, expectedDistanceKm, expectedDistanceMiles
                {  0,            0,          0,         37.6,               23.363512 },
                {  1,            1,          0,         35.8,               22.245046 },
                {  2,            2,          0,         35.2,               21.872224 },
                {  3,            3,          0,         36.2,               22.493594 },
                {  0,            1,          0,         8.7,                5.405919 },
                {  1,            2,          0,         13.1,               8.139947 },
                {  2,            3,          0,         7.4,                4.598138 },
                {  3,            0,          0,         10.0,               6.2137 },
                {  0,            2,          0,         21.2,               13.173044 },
                {  0,            3,          0,         28.6,               17.771182 },
                {  1,            3,          0,         20.5,               12.738085 },
                {  1,            0,          0,         29.5,               18.330415 },
                {  2,            0,          0,         16.4,               10.190468 },
                {  2,            1,          0,         22.7,               14.105099 },
                {  3,            1,          0,         16.3,               10.128331 },
                {  3,            2,          0,         28.8,               17.895456 },

                // Anti-clockwise routes
                // startSection, endSection, direction, expectedDistanceKm, expectedDistanceMiles
                {  0,            0,          1,         37.6,               23.363512 },
                {  1,            1,          1,         35.8,               22.245046 },
                {  2,            2,          1,         35.2,               21.872224 },
                {  3,            3,          1,         36.2,               22.493594 },
                {  0,            1,          1,         29.5,               18.330415 },
                {  1,            2,          1,         22.7,               14.105099 },
                {  2,            3,          1,         28.8,               17.895456 },
                {  3,            0,          1,         28.6,               17.771182 },
                {  0,            2,          1,         16.4,               10.190468 },
                {  0,            3,          1,         10.0,               6.2137 },
                {  1,            3,          1,         16.3,               10.128331 },
                {  1,            0,          1,         8.7,                5.405919 },
                {  2,            0,          1,         21.2,               13.173044 },
                {  2,            1,          1,         13.1,               8.139947 },
                {  3,            1,          1,         20.5,               12.738085 },
                {  3,            2,          1,         7.4,                4.598138 }
        });
    }

    @Parameterized.Parameter
    public int startSection;

    @Parameterized.Parameter(1)
    public int endSection;

    @Parameterized.Parameter(2)
    public int direction;

    @Parameterized.Parameter(3)
    public double expectedDistanceKm;

    @Parameterized.Parameter(4)
    public double expectedDistanceMiles;


    @Test
    public void optionsChanged_LinearCircularRoute_ShouldCalculateCorrectDistance() {
        // Arrange
        RouteHelpers.setupLinearCircularRoute(mockRoute, mockView, mockUserSettings);
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], true, new ArrayList<String>());
        vm.startSection = startSection;
        vm.endSection = endSection;
        vm.direction = direction;
        RouteOptionsPresenter _sut = new RouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        vm = _sut.optionsChanged(vm);

        // Assert
        assertEquals(expectedDistanceKm, vm.distanceKm, 0.01);
        assertEquals(expectedDistanceMiles, vm.distanceMiles, 0.01);
    }
}
