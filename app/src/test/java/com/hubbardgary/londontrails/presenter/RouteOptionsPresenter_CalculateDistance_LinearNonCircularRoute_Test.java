package com.hubbardgary.londontrails.presenter;

import android.content.res.Resources;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.testhelpers.RouteHelpers;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.model.Route;
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
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class RouteOptionsPresenter_CalculateDistance_LinearNonCircularRoute_Test {
    private GlobalObjects mockGlobals;
    private Resources mockResources;
    private IRouteOptionsView mockView;
    private Route mockRoute;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IRouteOptionsView.class);
        mockGlobals = Mockito.mock(GlobalObjects.class);
        mockResources = Mockito.mock(Resources.class);
        mockRoute = Mockito.mock(Route.class);

        when(mockResources.getInteger(R.integer.Clockwise)).thenReturn(0);
        when(mockResources.getInteger(R.integer.AntiClockwise)).thenReturn(1);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][] {
                // startSection, endSection, expectedDistanceKm, expectedDistanceMiles
                {  0,            1,          8.7,                5.405919 },
                {  1,            2,          13.1,               8.139947 },
                {  2,            3,          7.4,                4.598138 },
                {  3,            0,          28.6,               17.771182 },
                {  0,            2,          21.2,               13.173044 },
                {  0,            3,          28.6,               17.771182 },
                {  1,            3,          20.5,               12.738085 },
                {  1,            0,          8.7,                5.405919 },
                {  2,            0,          21.2,               13.173044 },
                {  2,            1,          13.1,               8.139947 },
                {  3,            1,          20.5,               12.738085 },
                {  3,            2,          7.4,                4.598138 }
        });
    }

    @Parameterized.Parameter
    public int startSection;

    @Parameterized.Parameter(1)
    public int endSection;

    @Parameterized.Parameter(2)
    public double expectedDistanceKm;

    @Parameterized.Parameter(3)
    public double expectedDistanceMiles;


    @Test
    public void optionsChanged_LinearNonCircularRoute_ShouldCalculateCorrectDistance() {
        // Arrange
        RouteHelpers.setupLinearNonCircularRoute(mockRoute, mockView, mockResources, mockGlobals);
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = startSection;
        vm.endSection = endSection;
        RouteOptionsPresenter _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        vm = _sut.optionsChanged(vm);

        // Assert
        assertEquals(expectedDistanceKm, vm.distanceKm, 0.01);
        assertEquals(expectedDistanceMiles, vm.distanceMiles, 0.01);
    }
}
