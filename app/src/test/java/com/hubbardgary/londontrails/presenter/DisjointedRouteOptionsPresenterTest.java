package com.hubbardgary.londontrails.presenter;

import android.content.res.Resources;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IGlobalObjects;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.Section;
import com.hubbardgary.londontrails.view.ShowMapActivity;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DisjointedRouteOptionsPresenterTest {

    private IGlobalObjects mockGlobals;
    private Resources mockResources;
    private IRouteOptionsView mockView;
    private Route mockRoute;
    private DisjointedRouteOptionsPresenter _sut;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IRouteOptionsView.class);
        mockGlobals = Mockito.mock(IGlobalObjects.class);
        mockResources = Mockito.mock(Resources.class);
        mockRoute = Mockito.mock(Route.class);
    }

    @Test
    public void getViewModel_ReturnsExpectedViewModel() {
        // Arrange
        when(mockRoute.getName()).thenReturn("Test Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("Test Route", result.name);
        assertEquals(false, result.isCircular);
    }

    @Test
    public void getViewModel_CurrentRouteIsGreenChain_ShouldSetRouteToGreenChain() {
        // Arrange
        when(mockView.getRouteSectionsFromIntent()).thenReturn(R.array.green_chain_walk_sections);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("Green Chain Walk", result.name);
    }

    @Test
    public void activitySubmit_ShouldInvokeShowMapActivity() {
        // Arrange
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = 1;
        vm.endSection = 3;

        // Act
        _sut.activitySubmit(vm);

        // Assert
        ArgumentCaptor<Map> argument = ArgumentCaptor.forClass(Map.class);
        verify(mockView).invokeActivity(argument.capture(), eq(ShowMapActivity.class));
        Map capturedIntent = argument.getValue();
        assertEquals(1, (int)capturedIntent.get("startSection"));
        assertEquals(1, (int)capturedIntent.get("endSection"));
    }

    @Test
    public void getSectionId_ReturnsCorrectSectionId() {
        // Arrange
        final int sectionId = 1;
        final String[] sectionArray = new String[] { "0", "1", "2" };
        when(mockView.getRouteSectionsFromIntent()).thenReturn(sectionId);
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(R.array.directions)).thenReturn(new String[] {"0", "1"});
        when(mockResources.getStringArray(sectionId)).thenReturn(sectionArray);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        int result = _sut.getSectionId("1");

        // Assert
        assertEquals(sectionId, result);
    }

    @Test
    public void optionsChanged_ShouldUpdateDistanceInViewModel() {
        // Arrange
        Section mockSection = Mockito.mock(Section.class);
        when(mockSection.getDistanceInKm()).thenReturn(15.0);
        when(mockSection.getStartLinkDistanceInKm()).thenReturn(1.5);
        when(mockSection.getEndLinkDistanceInKm()).thenReturn(0.5);
        when(mockSection.getExtensionDistanceInKm()).thenReturn(5.0);
        when(mockSection.getExtensionDescription()).thenReturn("Mock Extension Description");

        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        when(mockRoute.getSection(1)).thenReturn(mockSection);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        RouteViewModel vm = new RouteViewModel("Test vm", new String[0], false, new ArrayList<String>());
        vm.startSection = 1;

        // Act
        _sut.optionsChanged(vm);

        // Assert
        assertEquals(17.0, vm.distanceKm, 0.001);
        assertEquals(5.0, vm.extensionDistanceKm, 0.001);
        assertEquals("Mock Extension Description", vm.extensionDescription);
        verify(mockView).refreshDistance(eq(vm));
    }

    @Test
    public void menuItemSelected_HomeButtonPressed_EndsActivity() {
        // Arrange
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        _sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }

    @Test
    public void menuItemSelected_AnyIdExceptHome_DoesNotEndActivity() {
        // Arrange
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockGlobals, mockResources);

        // Act
        _sut.menuItemSelected(1);

        // Assert
        verify(mockView, never()).endActivity();
    }
}