package com.hubbardgary.londontrails.model;

public abstract class Route {

    private Section[] sections;
    private boolean circular;
    private boolean linear;  // Indicates whether route is contiguous (i.e. multiple sections can be chained into a single walk)
    private String name;
    private String shortName;
    private double distanceInKm;

    public Route() {
    }

    public abstract int getRouteId();

    public Section[] getSections() {
        return sections;
    }

    public Section getSection(int i) {
        return sections[i];
    }

    public void setSections(Section[] sections) {
        this.sections = sections;
    }

    public void setSection(int i, Section section) {
        this.sections[i] = section;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public boolean isLinear() {
        return linear;
    }

    public void setLinear(boolean linear) {
        this.linear = linear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }
}
