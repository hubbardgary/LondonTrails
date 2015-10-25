package com.hubbardgary.londontrails.viewmodel;

import com.hubbardgary.londontrails.model.Route;

public class ShowMapViewModel {
    public String name;
    public int start;
    public int end;
    public boolean isClockwise;
    public Route route;
    public int mapType;
    public boolean markersVisible;
    public MenuViewModel optionsMenu;
    public MenuViewModel mapTypeSubMenu;

    public ShowMapViewModel(int start, int end, int direction, Route route, int mapType) {
        this.name = route.getName() + ": Section " + String.valueOf(start + 1) + " - " + String.valueOf(end + 1);
        this.start = start;
        this.end = end;
        this.isClockwise = direction == 0 ? true : false;
        this.route = route;
        this.mapType = mapType;
        this.markersVisible = true;
    }
}
