package com.hubbardgary.londontrails.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteViewModel {
    // Properties of the route
    public final String name;
    public final String[] sectionsArray;
    public final List<String> startOptions;
    public final List<String> directionOptions;
    public final boolean isCircular;

    // Properties specific to options chosen by the user
    public List<String> endOptions;
    public double distanceKm;
    public double distanceMiles;
    public double extensionDistanceKm;
    public double extensionDistanceMiles;
    public String extensionDescription;
    public int direction;
    public int startSection;
    public int endSection;
    public int startSelectedIndex;
    public int endSelectedIndex;

    public RouteViewModel(String name, String[] sectionsArray, boolean isCircular, List<String> directions) {
        this.name = name;
        this.sectionsArray = sectionsArray;
        this.startOptions = new ArrayList<>(Arrays.asList(this.sectionsArray));
        this.endOptions = new ArrayList<>(Arrays.asList(this.sectionsArray));
        this.directionOptions = directions;
        this.isCircular = isCircular;
        this.startSection = 0;
        this.startSelectedIndex = 0;
        this.endSection = 1;
        if(isCircular)
            this.endSelectedIndex = 1;
        else
            this.endSelectedIndex = 0;
    }
}
