package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CapitalRingTest {

    private CapitalRing _sut;

    @Before
    public void setUp() {
        _sut = new CapitalRing();
    }

    @Test
    public void getRouteId_shouldReturnCorrectRouteId() {
        int result = _sut.getRouteId();
        assertEquals(R.id.rte_capital_ring, result);
    }

    @Test
    public void getRouteName_shouldReturnCorrectName() {
        String result = _sut.getName();
        assertEquals("Capital Ring", result);
    }

    @Test
    public void getShortName_shouldReturnCorrectName() {
        String result = _sut.getShortName();
        assertEquals("cr", result);
    }

    @Test
    public void getDistanceInKm_shouldReturnCorrectDistance() {
        double result = _sut.getDistanceInKm();
        assertEquals(122, result, 0.0001);
    }

    @Test
    public void isCircular_shouldBeTrue() {
        boolean result = _sut.isCircular();
        assertTrue(result);
    }

    @Test
    public void isLinear_shouldBeTrue() {
        boolean result = _sut.isLinear();
        assertTrue(result);
    }

    @Test
    public void getSections_shouldReturnCorrectSections() {
        Section[] result = _sut.getSections();
        assertNotNull(result);
        assertEquals(15, result.length);
    }

    @Test
    public void getRouteDistanceText_shouldReturnCorrectText() {
        String result = _sut.getRouteDistanceText();
        assertEquals("approx 122.0 km (75.8 miles)", result);
    }
}