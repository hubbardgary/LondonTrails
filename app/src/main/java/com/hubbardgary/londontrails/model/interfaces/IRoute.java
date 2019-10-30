package com.hubbardgary.londontrails.model.interfaces;

public interface IRoute {
    int getRouteId();

    ISection[] getSections();

    ISection getSection(int i);

    boolean isCircular();

    boolean isLinear();

    String getName();

    void setName(String name);

    String getShortName();

    double getDistanceInKm();
}
