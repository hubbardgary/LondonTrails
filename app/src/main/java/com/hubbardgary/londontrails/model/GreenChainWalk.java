package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;

public class GreenChainWalk extends Route {

    private static double distanceInKM = 85.3; // Includes all main sections and extensions (out-and-back) but not transport links.
    private static String name = "Green Chain Walk";

    public int getRouteId() {
        return R.id.rte_green_chain_walk;
    }

    public GreenChainWalk() {
        setName(name);
        setShortName("gc");
        setDistanceInKm(distanceInKM);
        setCircular(false);
        setLinear(false);
        setSections(new Section[13]);

        for (int i = 0; i < getSections().length; i++) {
            setSection(i, populateSections(i));
        }
    }

    private Section populateSections(int section) {
        Section s = new Section(this);

        switch (section) {
            case 0:
                s.setSectionId("01");
                s.setStartLocationName("Nickelby Close, Crossway Bus Stop");
                s.setEndLocationName("Abbey Wood Rail Station");
                s.setDistanceInKm(3.94);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.95);
                break;
            case 1:
                s.setSectionId("02");
                s.setStartLocationName("Erith Rail Station");
                s.setEndLocationName("Bostall Hill / Longleigh Lane Bus Stop");
                s.setDistanceInKm(5.32);
                s.setStartLinkDistanceInKm(1.17);
                s.setEndLinkDistanceInKm(0.11);
                break;
            case 2:
                s.setSectionId("03");
                s.setStartLocationName("Bostall Hill / Longleigh Lane Bus Stop");
                s.setEndLocationName("Falconwood Rail Station");
                s.setDistanceInKm(4.48);
                s.setStartLinkDistanceInKm(0.11);
                s.setEndLinkDistanceInKm(1.42);
                break;
            case 3:
                s.setSectionId("04");
                s.setStartLocationName("Charlton Rail Station");
                s.setEndLocationName("Bostall Hill / Longleigh Lane Bus Stop");
                s.setDistanceInKm(5.8);
                s.setStartLinkDistanceInKm(1.5);
                s.setEndLinkDistanceInKm(0.11);
                s.setExtensionDistanceInKm(2.8);
                s.setExtensionDescription("each way for Oxleas Meadows link");
                break;
            case 4:
                s.setSectionId("05");
                s.setStartLocationName("Charlton Rail Station");
                s.setEndLocationName("Falconwood Rail Station");
                s.setDistanceInKm(6.15);
                s.setStartLinkDistanceInKm(1.56);
                s.setEndLinkDistanceInKm(1.42);
                break;
            case 5:
                s.setSectionId("06");
                s.setStartLocationName("Falconwood Rail Station");
                s.setEndLocationName("Mottingham Rail Station");
                s.setDistanceInKm(6.27);
                s.setStartLinkDistanceInKm(0.04);
                s.setEndLinkDistanceInKm(1.12);
                break;
            case 6:
                s.setSectionId("07");
                s.setStartLocationName("Falconwood Rail Station");
                s.setEndLocationName("Mottingham Rail Station");
                s.setDistanceInKm(6.32);
                s.setStartLinkDistanceInKm(0.04);
                s.setEndLinkDistanceInKm(0.9);
                break;
            case 7:
                s.setSectionId("08");
                s.setStartLocationName("Mottingham Rail Station");
                s.setEndLocationName("Beckenham Hill Rail Station");
                s.setDistanceInKm(7.2);
                s.setStartLinkDistanceInKm(1.1);
                s.setEndLinkDistanceInKm(0.67);
                s.setExtensionDistanceInKm(1.4);
                s.setExtensionDescription("each way for Chinbrook link");
                break;
            case 8:
                s.setSectionId("09");
                s.setStartLocationName("Mottingham Rail Station");
                s.setEndLocationName("Ravensbourne Rail Station");
                s.setDistanceInKm(7.1);
                s.setStartLinkDistanceInKm(0.9);
                s.setEndLinkDistanceInKm(0.64);
                s.setExtensionDistanceInKm(2.8);
                s.setExtensionDescription("each way for Chislehurst link");
                break;
            case 9:
                s.setSectionId("10");
                s.setStartLocationName("Beckenham Hill Rail Station");
                s.setEndLocationName("Crystal Palace Rail Station");
                s.setDistanceInKm(6.15);
                s.setStartLinkDistanceInKm(0.67);
                s.setEndLinkDistanceInKm(0.04);
                break;
            case 10:
                s.setSectionId("11");
                s.setStartLocationName("Crystal Palace Rail Station");
                s.setEndLocationName("Nunhead Rail Station");
                s.setDistanceInKm(8.6);
                s.setStartLinkDistanceInKm(0.04);
                s.setEndLinkDistanceInKm(0.4);
                s.setExtensionDistanceInKm(2.0);
                s.setExtensionDescription("each way for Dulwich link");
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
