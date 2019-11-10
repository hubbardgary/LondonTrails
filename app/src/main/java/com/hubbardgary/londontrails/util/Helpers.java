package com.hubbardgary.londontrails.util;

public class Helpers {

    private static final double kmToMilesMultiplier = 0.62137;

    public static double convertKmToMiles(double km) {
        return km * kmToMilesMultiplier;
    }
}
