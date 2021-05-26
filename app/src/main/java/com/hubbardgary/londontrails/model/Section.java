package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.model.interfaces.IRoute;

public class Section implements com.hubbardgary.londontrails.model.interfaces.ISection {

    private IRoute route;
    private String sectionId;
    private String startLocationName;
    private String endLocationName;
    private double distanceInKm;
    private double startLinkDistanceInKm;
    private double endLinkDistanceInKm;
    private double extensionDistanceInKm = 0;
    private String extensionDescription;
    private String travelWarning;

    public Section(IRoute route) {
        this.route = route;
    }

    private String getResource(String routeId, String sectionId, String resourceName) {
        return String.format("%s-%s-%s.kml", routeId, sectionId, resourceName);
    }

    @Override
    public IRoute getRoute() {
        return this.route;
    }

    @Override
    public String getRouteShortName() {
        return this.route.getShortName();
    }

    @Override
    public String getSectionResource(String routeId) {
        return getResource(routeId, sectionId, "route");
    }

    @Override
    public String getStartLinkResource(String routeId) {
        return getResource(routeId, sectionId, "start_link");
    }

    @Override
    public String getEndLinkResource(String routeId) {
        return getResource(routeId, sectionId, "end_link");
    }

    @Override
    public String getPoiResource(String routeId) {
        return getResource(routeId, sectionId, "placemarks");
    }

    @Override
    public void setSectionId(String id) {
        this.sectionId = id;
    }

    @Override
    public String getStartLocationName() {
        return startLocationName;
    }

    @Override
    public void setStartLocationName(String startLocationName) {
        this.startLocationName = startLocationName;
    }

    @Override
    public String getEndLocationName() {
        return endLocationName;
    }

    @Override
    public void setEndLocationName(String endLocationName) {
        this.endLocationName = endLocationName;
    }

    @Override
    public double getDistanceInKm() {
        return distanceInKm;
    }

    @Override
    public void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    @Override
    public double getStartLinkDistanceInKm() {
        return startLinkDistanceInKm;
    }

    @Override
    public void setStartLinkDistanceInKm(double startLinkDistanceInKm) {
        this.startLinkDistanceInKm = startLinkDistanceInKm;
    }

    @Override
    public double getEndLinkDistanceInKm() {
        return endLinkDistanceInKm;
    }

    @Override
    public void setEndLinkDistanceInKm(double endLinkDistanceInKm) {
        this.endLinkDistanceInKm = endLinkDistanceInKm;
    }

    @Override
    public double getExtensionDistanceInKm() {
        return extensionDistanceInKm;
    }

    @Override
    public void setExtensionDistanceInKm(double extensionDistanceInKm) {
        this.extensionDistanceInKm = extensionDistanceInKm;
    }

    @Override
    public String getExtensionDescription() {
        return extensionDescription;
    }

    @Override
    public void setExtensionDescription(String extensionDescription) {
        this.extensionDescription = extensionDescription;
    }

    @Override
    public String getTravelWarning() {
        if (travelWarning == null) {
            return "";
        }
        return travelWarning;
    }

    @Override
    public void setTravelWarning(String travelWarning) {
        this.travelWarning = travelWarning;
    }
}
