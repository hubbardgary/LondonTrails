package com.hubbardgary.londontrails.model;

public class Section {

    private Route route;
    private String sectionId;
    private String startLocationName;
    private String endLocationName;
    private double distanceInKm;
    private double startLinkDistanceInKm;
    private double endLinkDistanceInKm;
    private double extensionDistanceInKm = 0;
    private String extensionDescription;

    public Section(Route route) {
        this.route = route;
    }

    private String getResource(String routeId, String sectionId, String resourceName) {
        return String.format("%s-%s-%s.kml", routeId, sectionId, resourceName);
    }

    public Route getRoute() {
        return this.route;
    }

    public String getRouteShortName() {
        return this.route.getShortName();
    }

    public String getSectionResource(String routeId) {
        return getResource(routeId, sectionId, "route");
    }

    public String getStartLinkResource(String routeId) {
        return getResource(routeId, sectionId, "start_link");
    }

    public String getEndLinkResource(String routeId) {
        return getResource(routeId, sectionId, "end_link");
    }

    public String getPoiResource(String routeId) {
        return getResource(routeId, sectionId, "placemarks");
    }

    void setSectionId(String id) {
        this.sectionId = id;
    }

    public String getStartLocationName() {
        return startLocationName;
    }

    void setStartLocationName(String startLocationName) {
        this.startLocationName = startLocationName;
    }

    public String getEndLocationName() {
        return endLocationName;
    }

    void setEndLocationName(String endLocationName) {
        this.endLocationName = endLocationName;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public double getStartLinkDistanceInKm() {
        return startLinkDistanceInKm;
    }

    void setStartLinkDistanceInKm(double startLinkDistanceInKm) {
        this.startLinkDistanceInKm = startLinkDistanceInKm;
    }

    public double getEndLinkDistanceInKm() {
        return endLinkDistanceInKm;
    }

    void setEndLinkDistanceInKm(double endLinkDistanceInKm) {
        this.endLinkDistanceInKm = endLinkDistanceInKm;
    }

    public double getExtensionDistanceInKm() {
        return extensionDistanceInKm;
    }

    void setExtensionDistanceInKm(double extensionDistanceInKm) {
        this.extensionDistanceInKm = extensionDistanceInKm;
    }

    public String getExtensionDescription() {
        return extensionDescription;
    }

    void setExtensionDescription(String extensionDescription) {
        this.extensionDescription = extensionDescription;
    }
}
