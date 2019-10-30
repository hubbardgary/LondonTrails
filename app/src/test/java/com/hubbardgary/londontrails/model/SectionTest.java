package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.model.interfaces.ISection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SectionTest {

    private ISection _sut;
    private String _routeId = "tr";
    private String _sectionId = "03";
    IRoute routeMock;

    @Before
    public void setUp() {
        routeMock = Mockito.mock(IRoute.class);
        _sut = new Section(routeMock);
        _sut.setSectionId(_sectionId);
    }

    @Test
    public void getRoute_returnsCorrectRoute() {
        IRoute result = _sut.getRoute();
        assertSame(routeMock, result);
    }

    @Test
    public void getRouteShortName_returnsCorrectShortName() {
        String shortName = "Route Short Name";
        when(routeMock.getShortName()).thenReturn(shortName);
        String result = _sut.getRouteShortName();
        assertEquals(shortName, result);
    }

    @Test
    public void getSectionResource_returnsCorrectFilename() {
        String result = _sut.getSectionResource(_routeId);
        assertEquals("tr-03-route.kml", result);
    }

    @Test
    public void getStartLinkResource_returnsCorrectFilename() {
        String result = _sut.getStartLinkResource(_routeId);
        assertEquals("tr-03-start_link.kml", result);
    }

    @Test
    public void getEndLinkResource_returnsCorrectFilename() {
        String result = _sut.getEndLinkResource(_routeId);
        assertEquals("tr-03-end_link.kml", result);
    }

    @Test
    public void getPoiResource_returnsCorrectFilename() {
        String result = _sut.getPoiResource(_routeId);
        assertEquals("tr-03-placemarks.kml", result);
    }

    @Test
    public void setStartLocationName_correctlySetsStartLocationName() {
        String startLocationName = "Start Location";
        _sut.setStartLocationName(startLocationName);
        String result = _sut.getStartLocationName();
        assertEquals(startLocationName, result);
    }

    @Test
    public void setEndLocationName_correctlySetsEndLocationName() {
        String endLocationName = "End Location";
        _sut.setEndLocationName(endLocationName);
        String result = _sut.getEndLocationName();
        assertEquals(endLocationName, result);
    }

    @Test
    public void setSectionDistance_correctlySetsSectionDistance() {
        double sectionDistance = 30;
        _sut.setDistanceInKm(sectionDistance);
        double result = _sut.getDistanceInKm();
        assertEquals(sectionDistance, result, 0.0001);
    }

    @Test
    public void setStartLinkDistance_correctlySetsStartLinkDistance() {
        double startLinkDistance = 5;
        _sut.setStartLinkDistanceInKm(startLinkDistance);
        double result = _sut.getStartLinkDistanceInKm();
        assertEquals(startLinkDistance, result, 0.0001);
    }

    @Test
    public void setEndLinkDistance_correctlySetsEndLinkDistance() {
        double endLinkDistance = 3;
        _sut.setEndLinkDistanceInKm(endLinkDistance);
        double result = _sut.getEndLinkDistanceInKm();
        assertEquals(endLinkDistance, result, 0.0001);
    }

    @Test
    public void setExtensionLinkDistance_correctlySetsExtensionLinkDistance() {
        double extensionLinkDistance = 10;
        _sut.setExtensionDistanceInKm(extensionLinkDistance);
        double result = _sut.getExtensionDistanceInKm();
        assertEquals(extensionLinkDistance, result, 0.0001);
    }

    @Test
    public void setExtensionDescription_correctlySetsExtensionDescription() {
        String extensionDescription = "Extension Description";
        _sut.setExtensionDescription(extensionDescription);
        String result = _sut.getExtensionDescription();
        assertEquals(extensionDescription, result);
    }

}