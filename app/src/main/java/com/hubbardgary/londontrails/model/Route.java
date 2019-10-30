package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.model.interfaces.ISection;

public abstract class Route implements com.hubbardgary.londontrails.model.interfaces.IRoute {

    private ISection[] sections;
    private boolean circular;
    private boolean linear;  // Indicates whether route is contiguous (i.e. multiple sections can be chained into a single walk)
    private String name;
    private String shortName;
    private double distanceInKm;

    public Route() {
    }

    @Override
    public ISection[] getSections() {
        return sections;
    }

    @Override
    public ISection getSection(int i) {
        return sections[i];
    }

    void setSections(ISection[] sections) {
        this.sections = sections;
    }

    void setSection(int i, ISection section) {
        this.sections[i] = section;
    }

    @Override
    public boolean isCircular() {
        return circular;
    }

    void setCircular(boolean circular) {
        this.circular = circular;
    }

    @Override
    public boolean isLinear() {
        return linear;
    }

    void setLinear(boolean linear) {
        this.linear = linear;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public double getDistanceInKm() {
        return distanceInKm;
    }

    void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }
}
