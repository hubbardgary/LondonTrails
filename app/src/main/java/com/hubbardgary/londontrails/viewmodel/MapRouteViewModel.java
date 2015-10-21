package com.hubbardgary.londontrails.viewmodel;

public class MapRouteViewModel {
    public final int startSection;
    public final int endSection;
    public final int direction;
    public final String mapTitle;

    public MapRouteViewModel(int startSection, int endSection, int direction, String mapTitle) {
        this.startSection = startSection;
        this.endSection = endSection;
        this.direction = direction;
        this.mapTitle = mapTitle;
    }
}
