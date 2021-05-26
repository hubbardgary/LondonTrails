package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.interfaces.ISection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LondonLoopTest {

    private LondonLoop _sut;

    @Before
    public void setUp() {
        _sut = new LondonLoop();
    }

    @Test
    public void getRouteId_shouldReturnCorrectRouteId() {
        int result = _sut.getRouteId();
        assertEquals(R.id.rte_london_loop, result);
    }

    @Test
    public void getRouteName_shouldReturnCorrectName() {
        String result = _sut.getName();
        assertEquals("London Loop", result);
    }

    @Test
    public void getShortName_shouldReturnCorrectName() {
        String result = _sut.getShortName();
        assertEquals("ll", result);
    }

    @Test
    public void getDistanceInKm_shouldReturnCorrectDistance() {
        double result = _sut.getDistanceInKm();
        assertEquals(240, result, 0.0001);
    }

    @Test
    public void isCircular_shouldBeFalse() {
        boolean result = _sut.isCircular();
        assertFalse(result);
    }

    @Test
    public void isLinear_shouldBeTrue() {
        boolean result = _sut.isLinear();
        assertTrue(result);
    }

    @Test
    public void getSections_shouldReturnCorrectSections() {
        ISection[] result = _sut.getSections();
        assertNotNull(result);
        assertEquals(24, result.length);
    }

    @Test
    public void getRouteDistanceText_shouldReturnCorrectText() {
        String result = LondonLoop.getRouteDistanceText();
        assertEquals("approx 240.0 km (149.1 miles)", result);
    }
}