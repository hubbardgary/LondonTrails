package com.hubbardgary.londontrails.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LondonTrailsPlacemarkTest {

    @Test
    public void londonTrailsPlacemark_alwaysShowIsTrue_reportsValueCorrectly() {
        LondonTrailsPlacemark sut = new LondonTrailsPlacemark(null, true);
        assertTrue(sut.isAlwaysShow());
    }

    @Test
    public void londonTrailsPlacemark_alwaysShowIsFalse_reportsValueCorrectly() {
        LondonTrailsPlacemark sut = new LondonTrailsPlacemark(null, false);
        assertFalse(sut.isAlwaysShow());
    }
}