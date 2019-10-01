package com.hubbardgary.londontrails.presenter;

import android.content.res.Resources;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IGlobalObjects;
import com.hubbardgary.londontrails.testhelpers.RouteHelpers;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.view.ShowMapActivity;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteOptionsPresenterTest {

    private IGlobalObjects mockGlobals;
    private Resources mockResources;
    private IRouteOptionsView mockView;
    private Route mockRoute;
    private RouteOptionsPresenter _sut;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IRouteOptionsView.class);
        mockGlobals = Mockito.mock(IGlobalObjects.class);
        mockResources = Mockito.mock(Resources.class);
        mockRoute = Mockito.mock(Route.class);

        when(mockResources.getInteger(R.integer.Clockwise)).thenReturn(0);
        when(mockResources.getInteger(R.integer.AntiClockwise)).thenReturn(1);

        when(mockRoute.getName()).thenReturn("Test Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);
    }

    @Test
    public void getViewModel_ReturnsExpectedViewModel() {
        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("Test Route", result.name);
        assertEquals(false, result.isCircular);
    }

    @Test
    public void getViewModel_CurrentRouteIsCapitalRing_RouteShouldBeCapitalRing() {
        // Arrange
        when(mockView.getRouteSectionsFromIntent()).thenReturn(R.array.capital_ring_sections);
        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("Capital Ring", result.name);
    }

    @Test
    public void getViewModel_CurrentRouteIsLondonLoop_RouteShouldBeLondonLoop() {
        // Arrange
        when(mockView.getRouteSectionsFromIntent()).thenReturn(R.array.london_loop_sections);
        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("London Loop", result.name);
    }

    @Test
    public void activitySubmit_RouteIsCircular_ShowMapActivityInvokedWithCorrectDirection() {
        // Arrange
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], true, new ArrayList<String>());
        vm.startSection = 1;
        vm.endSection = 2;
        vm.direction = 1;

        // Act
        _sut.activitySubmit(vm);

        // Assert
        ArgumentCaptor<Map> argument = ArgumentCaptor.forClass(Map.class);
        verify(mockView).invokeActivity(argument.capture(), eq(ShowMapActivity.class));
        Map capturedIntent = argument.getValue();
        assertEquals(1, (int) capturedIntent.get("startSection"));
        assertEquals(2, (int) capturedIntent.get("endSection"));
        assertEquals(1, (int) capturedIntent.get("direction"));
    }

    @Test
    public void activitySubmit_RouteIsNotCircularAndStartSectionIsBeforeEndSection_ShowMapActivityInvokedWithClockwiseDirection() {
        // Arrange
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = 1;
        vm.endSection = 2;
        vm.direction = 0;

        // Act
        _sut.activitySubmit(vm);

        // Assert
        ArgumentCaptor<Map> argument = ArgumentCaptor.forClass(Map.class);
        verify(mockView).invokeActivity(argument.capture(), eq(ShowMapActivity.class));
        Map capturedIntent = argument.getValue();
        assertEquals(1, (int) capturedIntent.get("startSection"));
        assertEquals(2, (int) capturedIntent.get("endSection"));
        assertEquals(0, (int) capturedIntent.get("direction"));
    }

    @Test
    public void activitySubmit_RouteIsNotCircularAndStartSectionIsAfterEndSection_ShowMapActivityInvokedWithClockwiseDirection() {
        // Arrange
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = 3;
        vm.endSection = 2;

        // Act
        _sut.activitySubmit(vm);

        // Assert
        ArgumentCaptor<Map> argument = ArgumentCaptor.forClass(Map.class);
        verify(mockView).invokeActivity(argument.capture(), eq(ShowMapActivity.class));
        Map capturedIntent = argument.getValue();
        assertEquals(3, (int) capturedIntent.get("startSection"));
        assertEquals(2, (int) capturedIntent.get("endSection"));
        assertEquals(1, (int) capturedIntent.get("direction"));
    }

    @Test
    public void getSectionId_ReturnsCorrectSectionId() {
        // Arrange
        final int sectionId = 1;
        final String[] sectionArray = new String[]{"0", "1", "2"};
        when(mockView.getRouteSectionsFromIntent()).thenReturn(sectionId);
        when(mockResources.getStringArray(sectionId)).thenReturn(sectionArray);
        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        int result = _sut.getSectionId("1");

        // Assert
        assertEquals(sectionId, result);
    }

    @Test
    public void startSectionChanged_RouteIsCircular_NoChangesMadeToViewModel() {
        // Arrange
        when(mockRoute.isCircular()).thenReturn(true);

        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], true, new ArrayList<String>());
        vm.startSection = 1;
        vm.startSelectedIndex = 2;
        vm.endSection = 3;
        vm.endSelectedIndex = 4;
        vm.endOptions = new ArrayList<>();
        vm.endOptions.add("Section 1");
        vm.endOptions.add("Section 2");

        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.startSectionChanged(vm);

        // Assert
        assertEquals(1, result.startSection);
        assertEquals(2, result.startSelectedIndex);
        assertEquals(3, result.endSection);
        assertEquals(4, result.endSelectedIndex);
        assertArrayEquals(new String[]{"Section 1", "Section 2"}, result.endOptions.toArray());
    }

    @Test
    public void startSectionChanged_RouteIsNotCircularAndStartSectionIsDifferentToEndSection_StartSectionRemovedFromEndOptions() {
        // Arrange
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[]{"Section 0", "Section 1", "Section 2", "Section 3"});

        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], true, new ArrayList<String>());
        vm.startSection = 1;
        vm.startSelectedIndex = 1;
        vm.endSection = 3;
        vm.endSelectedIndex = 3;
        vm.endOptions = new ArrayList<>(Arrays.asList(mockResources.getStringArray(1)));

        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.startSectionChanged(vm);

        // Assert
        assertEquals(1, result.startSection);
        assertEquals(1, result.startSelectedIndex);
        assertEquals(3, result.endSection);
        assertEquals(2, result.endSelectedIndex); // This is now 2 because the start section has been removed from the list since it can't be selected.
        assertArrayEquals(new String[]{"Section 0", "Section 2", "Section 3"}, vm.endOptions.toArray());
    }

    @Test
    public void startSectionChanged_RouteIsNotCircularAndStartSectionIsSameAsEndSection_EndSectionIsResetToFirstSection() {
        // Arrange
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[]{"Section 0", "Section 1", "Section 2", "Section 3"});

        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], true, new ArrayList<String>());
        vm.startSection = 1;
        vm.startSelectedIndex = 1;
        vm.endSection = 1;
        vm.endSelectedIndex = 1;
        vm.endOptions = new ArrayList<>(Arrays.asList(mockResources.getStringArray(1)));

        _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.startSectionChanged(vm);

        // Assert
        assertEquals(1, result.startSection);
        assertEquals(1, result.startSelectedIndex);
        assertEquals(0, result.endSection);
        assertEquals(0, result.endSelectedIndex);
        assertArrayEquals(new String[]{"Section 0", "Section 2", "Section 3"}, vm.endOptions.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionsChanged_LinearNonCircularRoute_StartSectionEqualsEndSection_ShouldThrowIllegalArgumentException() {
        // Arrange
        RouteHelpers.setupLinearNonCircularRoute(mockRoute, mockView, mockResources, mockGlobals);
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = 0;
        vm.endSection = 0;
        RouteOptionsPresenter _sut = new RouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        _sut.optionsChanged(vm);
    }

    @Test
    public void optionsChanged_RefreshDistanceIsInvoked() {
        // Arrange
        RouteHelpers.setupLinearCircularRoute(mockRoute, mockView, mockResources, mockGlobals);
        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], true, new ArrayList<String>());
        vm.startSection = 1;
        vm.endSection = 2;

        // Act
        _sut.optionsChanged(vm);

        // Assert
        verify(mockView).refreshDistance(any(RouteViewModel.class));
    }

    @Test
    public void menuItemSelected_Home_ShouldEndActivity() {
        // Act
        _sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }
}