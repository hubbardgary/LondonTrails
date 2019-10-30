package com.hubbardgary.londontrails.model.interfaces;

public interface ISection {
    IRoute getRoute();

    String getRouteShortName();

    String getSectionResource(String routeId);

    String getStartLinkResource(String routeId);

    String getEndLinkResource(String routeId);

    String getPoiResource(String routeId);

    void setSectionId(String id);

    String getStartLocationName();

    void setStartLocationName(String startLocationName);

    String getEndLocationName();

    void setEndLocationName(String endLocationName);

    double getDistanceInKm();

    void setDistanceInKm(double distanceInKm);

    double getStartLinkDistanceInKm();

    void setStartLinkDistanceInKm(double startLinkDistanceInKm);

    double getEndLinkDistanceInKm();

    void setEndLinkDistanceInKm(double endLinkDistanceInKm);

    double getExtensionDistanceInKm();

    void setExtensionDistanceInKm(double extensionDistanceInKm);

    String getExtensionDescription();

    void setExtensionDescription(String extensionDescription);
}
