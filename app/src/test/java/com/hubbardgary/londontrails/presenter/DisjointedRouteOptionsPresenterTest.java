package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.model.interfaces.ISection;
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

    private IUserSettings mockUserSettings;
    private IRouteOptionsView mockView;
    private IRoute mockRoute;
    private DisjointedRouteOptionsPresenter _sut;

    @Before
    public void setUp() {
        mockView = Mockito.mock(IRouteOptionsView.class);
        mockUserSettings = Mockito.mock(IUserSettings.class);
        mockRoute = Mockito.mock(IRoute.class);
    }

    @Test
    public void getViewModel_ReturnsExpectedViewModel() {
        // Arrange
        when(mockRoute.getName()).thenReturn("Test Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[0]);
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("Test Route", result.name);
        assertFalse(result.isCircular);
    }

    @Test
    public void getViewModel_CurrentRouteIsGreenChain_ShouldSetRouteToGreenChain() {
        // Arrange
        when(mockView.getRouteSectionsFromIntent()).thenReturn(R.array.green_chain_walk_sections);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        RouteViewModel result = _sut.getViewModel();

        // Assert
        assertEquals("Green Chain Walk", result.name);
    }

    @Test
    public void activitySubmit_ShouldInvokeShowMapActivity() {
        // Arrange
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

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
        when(mockView.getStringArrayFromResources(R.array.directions)).thenReturn(new String[] {"0", "1"});
        when(mockView.getStringArrayFromResources(sectionId)).thenReturn(sectionArray);
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        int result = _sut.getSectionId("1");

        // Assert
        assertEquals(sectionId, result);
    }

    @Test
    public void optionsChanged_ShouldUpdateDistanceInViewModel() {
        // Arrange
        ISection mockSection = Mockito.mock(ISection.class);
        when(mockSection.getDistanceInKm()).thenReturn(15.0);
        when(mockSection.getStartLinkDistanceInKm()).thenReturn(1.5);
        when(mockSection.getEndLinkDistanceInKm()).thenReturn(0.5);
        when(mockSection.getExtensionDistanceInKm()).thenReturn(5.0);
        when(mockSection.getExtensionDescription()).thenReturn("Mock Extension Description");

        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[0]);
        when(mockRoute.getSection(1)).thenReturn(mockSection);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

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
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        _sut.menuItemSelected(android.R.id.home);

        // Assert
        verify(mockView).endActivity();
    }

    @Test
    public void menuItemSelected_AnyIdExceptHome_DoesNotEndActivity() {
        // Arrange
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[0]);
        _sut = new DisjointedRouteOptionsPresenter(mockView, mockUserSettings);

        // Act
        _sut.menuItemSelected(1);

        // Assert
        verify(mockView, never()).endActivity();
    }
}