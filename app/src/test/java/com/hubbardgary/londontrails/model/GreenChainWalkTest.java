package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.interfaces.ISection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreenChainWalkTest {

    private GreenChainWalk _sut;

    @Before
    public void setUp() {
        _sut = new GreenChainWalk();
    }

    @Test
    public void getRouteId_shouldReturnCorrectRouteId() {
        int result = _sut.getRouteId();
        assertEquals(R.id.rte_green_chain_walk, result);
    }

    @Test
    public void getRouteName_shouldReturnCorrectName() {
        String result = _sut.getName();
        assertEquals("Green Chain Walk", result);
    }

    @Test
    public void getShortName_shouldReturnCorrectName() {
        String result = _sut.getShortName();
        assertEquals("gc", result);
    }

    @Test
    public void getDistanceInKm_shouldReturnCorrectDistance() {
        double result = _sut.getDistanceInKm();
        assertEquals(85.3, result, 0.0001);
    }

    @Test
    public void isCircular_shouldBeFalse() {
        boolean result = _sut.isCircular();
        assertFalse(result);
    }

    @Test
    public void isLinear_shouldBeFalse() {
        boolean result = _sut.isLinear();
        assertFalse(result);
    }

    @Test
    public void getSections_shouldReturnCorrectAmountOfInstantiatedSections() {
        ISection[] result = _sut.getSections();
        assertNotNull(result);
        assertEquals(13, result.length);
        for (ISection iSection : result) {
            assertNotNull(iSection);
        }
    }

    @Test
    public void getRouteDistanceText_shouldReturnCorrectText() {
        String result = GreenChainWalk.getRouteDistanceText();
        assertEquals("approx 85.3 km (53.0 miles)", result);
    }

}