package com.hubbardgary.londontrails.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpersTest {
    @Test
    public void convertKmToMiles_ReturnsDistanceInMiles() {
        assertConvertKmToMiles(10, 6.2137);
        assertConvertKmToMiles(15.3, 9.506961);
        assertConvertKmToMiles(16.09, 9.9978433);
        assertConvertKmToMiles(0.2, 0.124274);
        assertConvertKmToMiles(0, 0);
    }

    private void assertConvertKmToMiles(double distanceInKm, double expectedDistanceInMiles) {
        // Act
        double result = Helpers.convertKmToMiles(distanceInKm);

        // Assert
        assertEquals(expectedDistanceInMiles, result, 0.01);
    }
}
