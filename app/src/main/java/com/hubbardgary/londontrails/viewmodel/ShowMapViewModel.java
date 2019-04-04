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
        this.name = getRouteName(start, end, route);
        this.start = start;
        this.end = end;
        this.isClockwise = direction == 0 ? true : false;
        this.route = route;
        this.mapType = mapType;
        this.markersVisible = true;
    }

    private String getRouteName(int start, int end, Route route) {
        if (route.isLinear()) {
            return String.format("%s: Section %s - %s", route.getName(), String.valueOf(start + 1), String.valueOf(end + 1));
        }
        return String.format("%s: Section %s", route.getName(), String.valueOf(start + 1));
    }
}
