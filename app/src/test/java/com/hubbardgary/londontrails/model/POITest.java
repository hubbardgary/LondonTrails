package com.hubbardgary.londontrails.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class POITest {

    private POI _sut;

    @Before
    public void setUp() throws Exception {
        _sut = new POI();
    }

    @Test
    public void setLongitude_correctlySetsLongitude() {
        final double longitude = 51.12345;
        _sut.setLongitude(longitude);
        double result = _sut.getLongitude();
        assertEquals(longitude, result, 0.00001);
    }

    @Test
    public void setLatitude_correctlySetsLatitude() {
        final double latitude = 23.98765;
        _sut.setLatitude(latitude);
        double result = _sut.getLatitude();
        assertEquals(latitude, result, 0.00001);
    }

    @Test
    public void setTitle_correctlySetsTitle() {
        final String title = "Test Title";
        _sut.setTitle(title);
        String result = _sut.getTitle();
        assertEquals(title, result);
    }

    @Test
    public void setSnippet_correctlySetsSnippet() {
        final String snippet = "Test snippet";
        _sut.setSnippet(snippet);
        String result = _sut.getSnippet();
        assertEquals(snippet, result);
    }

    @Test
    public void setAlternativeEndPointToTrue_correctlySetsAlternativeEndPoint() {
        _sut.setIsAlternativeEndPoint(true);
        boolean result = _sut.getIsAlternativeEndPoint();
        assertEquals(true, result);
    }

    @Test
    public void setAlternativeEndPointToFalse_correctlySetsAlternativeEndPoint() {
        _sut.setIsAlternativeEndPoint(false);
        boolean result = _sut.getIsAlternativeEndPoint();
        assertEquals(false, result);
    }

}