package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;

public class GreenChainWalk extends Route {

    private static double distanceInKM = 80.5;
    private static String name = "Green Chain Walk";

    public int getRouteId() {
        return R.id.rte_green_chain_walk;
    }

    public GreenChainWalk() {
        setName(name);
        setDistanceInKm(distanceInKM);
        setCircular(false);
        setLinear(false);
        setSections(new Section[13]);

        for (int i = 0; i < getSections().length; i++) {
            setSection(i, populateSections(i));
        }
    }

    private Section populateSections(int section) {
        Section s = new Section();

        switch (section) {
            case 0:
                s.setStartLocationName("Nickelby Close, Crossway Bus Stop");
                s.setEndLocationName("Abbey Wood Rail Station");
                s.setStartLinkResource("gc-01-start_link.kml");
                s.setSectionResource("gc-01-route.kml");
                s.setEndLinkResource("gc-01-end_link.kml");
                s.setPoiResource("gc-01-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 1:
                s.setStartLocationName("Erith Rail Station");
                s.setEndLocationName("Bostall Hill / Longleigh Lane Bus Stop");
                s.setStartLinkResource("gc-02-start_link.kml");
                s.setSectionResource("gc-02-route.kml");
                s.setEndLinkResource("gc-02-end_link.kml");
                s.setPoiResource("gc-02-placemarks.kml");
                s.setDistanceInKm(5.3);
                s.setStartLinkDistanceInKm(1.2);
                s.setEndLinkDistanceInKm(0.1);
                break;
            case 2:
                s.setStartLocationName("Bostall Hill / Longleigh Lane Bus Stop");
                s.setEndLocationName("Falconwood Rail Station");
                s.setStartLinkResource("gc-03-start_link.kml");
                s.setSectionResource("gc-03-route.kml");
                s.setEndLinkResource("gc-03-end_link.kml");
                s.setPoiResource("gc-03-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 3:
                s.setStartLocationName("Charlton Rail Station");
                s.setEndLocationName("Bostall Hill / Longleigh Lane Bus Stop");
                s.setStartLinkResource("gc-04a-start_link.kml");
                s.setSectionResource("gc-04a-route.kml");
                s.setEndLinkResource("gc-04a-end_link.kml");
                s.setPoiResource("gc-04a-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 4:
                s.setStartLocationName("Plumstead Rail Station");
                s.setEndLocationName("Falconwood Rail Station");
                s.setStartLinkResource("gc-04b-start_link.kml");
                s.setSectionResource("gc-04b-route.kml");
                s.setEndLinkResource("gc-04b-end_link.kml");
                s.setPoiResource("gc-04b-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 5:
                s.setStartLocationName("Charlton Rail Station");
                s.setEndLocationName("Falconwood Rail Station");
                s.setStartLinkResource("gc-05-start_link.kml");
                s.setSectionResource("gc-05-route.kml");
                s.setEndLinkResource("gc-05-end_link.kml");
                s.setPoiResource("gc-05-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 6:
                s.setStartLocationName("Falconwood Rail Station");
                s.setEndLocationName("Mottingham Rail Station");
                s.setStartLinkResource("gc-06-start_link.kml");
                s.setSectionResource("gc-06-route.kml");
                s.setEndLinkResource("gc-06-end_link.kml");
                s.setPoiResource("gc-06-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 7:
                s.setStartLocationName("Falconwood Rail Station");
                s.setEndLocationName("Mottingham Rail Station");
                s.setStartLinkResource("gc-07-start_link.kml");
                s.setSectionResource("gc-07-route.kml");
                s.setEndLinkResource("gc-07-end_link.kml");
                s.setPoiResource("gc-07-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 8:
                s.setStartLocationName("Mottingham Rail Station");
                s.setEndLocationName("Beckenham Hill Rail Station");
                s.setStartLinkResource("gc-08-start_link.kml");
                s.setSectionResource("gc-08-route.kml");
                s.setEndLinkResource("gc-08-end_link.kml");
                s.setPoiResource("gc-08-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 9:
                s.setStartLocationName("Mottingham Rail Station");
                s.setEndLocationName("Ravensbourne Rail Station");
                s.setStartLinkResource("gc-09a-start_link.kml");
                s.setSectionResource("gc-09a-route.kml");
                s.setEndLinkResource("gc-09a-end_link.kml");
                s.setPoiResource("gc-09a-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 10:
                s.setStartLocationName("Chislehurst Ashfield Road / Loop Road Bust Stop");
                s.setEndLocationName("Elmstead Woods Rail Station");
                s.setStartLinkResource("gc-09b-start_link.kml");
                s.setSectionResource("gc-09b-route.kml");
                s.setEndLinkResource("gc-09b-end_link.kml");
                s.setPoiResource("gc-09b-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 11:
                s.setStartLocationName("Beckenham Hill Rail Station");
                s.setEndLocationName("Crystal Palace Rail Station");
                s.setStartLinkResource("gc-10-start_link.kml");
                s.setSectionResource("gc-10-route.kml");
                s.setEndLinkResource("gc-10-end_link.kml");
                s.setPoiResource("gc-10-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
            case 12:
                s.setStartLocationName("Crystal Palace Rail Station");
                s.setEndLocationName("Nunhead Rail Station");
                s.setStartLinkResource("gc-11-start_link.kml");
                s.setSectionResource("gc-11-route.kml");
                s.setEndLinkResource("gc-11-end_link.kml");
                s.setPoiResource("gc-11-placemarks.kml");
                s.setDistanceInKm(3.6);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.65);
                break;
        }
        return s;
    }

    // Static methods used for displaying details about the route without
    // needing to instantiate a Route object.
    public static String getRouteName() {
        return name;
    }

    public static String getRouteDistanceText() {
        return String.format("approx %.1f km (%.1f miles)", distanceInKM, GlobalObjects.convertKmToMiles(distanceInKM));
    }
}
