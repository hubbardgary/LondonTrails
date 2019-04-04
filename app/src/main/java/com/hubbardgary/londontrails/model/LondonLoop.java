package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;

public class LondonLoop extends Route {

    private static double distanceInKM = 240;
    private static String name = "London Loop";

    public int getRouteId() {
        return R.id.rte_london_loop;
    }

    public LondonLoop() {
        setName(name);
        setShortName("ll");
        setDistanceInKm(distanceInKM);
        setCircular(false);
        setLinear(true);
        setSections(new Section[15]);

        for (int i = 0; i < getSections().length; i++) {
            setSection(i, populateSections(i));
        }
    }

    private Section populateSections(int section) {
        Section s = new Section(this);

        switch (section) {
            case 0:
                s.setSectionId("01");
                s.setStartLocationName("Erith Rail Station");
                s.setEndLocationName("Bexley Rail Station");
                s.setDistanceInKm(13);
                s.setStartLinkDistanceInKm(0.33);
                s.setEndLinkDistanceInKm(0.14);
                break;
            case 1:
                s.setSectionId("02");
                s.setStartLocationName("Bexley Rail Station");
                s.setEndLocationName("Petts Wood Rail Station");
                s.setDistanceInKm(11.3);
                s.setStartLinkDistanceInKm(0.14);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 2:
                s.setSectionId("03");
                s.setStartLocationName("Petts Wood Rail Station");
                s.setEndLocationName("Hayes Rail Station");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(0.96);
                break;
            case 3:
                s.setSectionId("04");
                s.setStartLocationName("Hayes Rail Station");
                s.setEndLocationName("Hamsey Green Bus Stop");
                s.setDistanceInKm(13.5);
                s.setStartLinkDistanceInKm(0.96);
                s.setEndLinkDistanceInKm(0.06);
                break;
            case 4:
                s.setSectionId("05");
                s.setStartLocationName("Hamsey Green Bus Stop");
                s.setEndLocationName("Banstead Rail Station");
                s.setDistanceInKm(17.3);
                s.setStartLinkDistanceInKm(0.06);
                s.setEndLinkDistanceInKm(0.46);
                break;
            case 5:
                s.setSectionId("06");
                s.setStartLocationName("Banstead Rail Station");
                s.setEndLocationName("Kingston Rail Station");
                s.setDistanceInKm(17.4);
                s.setStartLinkDistanceInKm(0.46);
                s.setEndLinkDistanceInKm(0.61);
                break;
            case 6:
                s.setSectionId("07");
                s.setStartLocationName("Kingston Rail Station");
                s.setEndLocationName("Hatton Cross Underground Station");
                s.setDistanceInKm(15.3);
                s.setStartLinkDistanceInKm(0.61);
                s.setEndLinkDistanceInKm(0.16);
                break;
            case 7:
                s.setSectionId("08");
                s.setStartLocationName("Hatton Cross Underground Station");
                s.setEndLocationName("Uxbridge Underground Station");
                s.setDistanceInKm(17.5);
                s.setStartLinkDistanceInKm(0.16);
                s.setEndLinkDistanceInKm(0.76);
                break;
            case 8:
                s.setSectionId("09");
                s.setStartLocationName("Uxbridge Underground Station");
                s.setEndLocationName("Moor Park Underground Station");
                s.setDistanceInKm(15.3);
                s.setStartLinkDistanceInKm(0.76);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 9:
                s.setSectionId("10");
                s.setStartLocationName("Moor Park Underground Station");
                s.setEndLocationName("Elstree & Borehamwood Rail Station");
                s.setDistanceInKm(19.5);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(0.2);
                break;
            case 10:
                s.setSectionId("11");
                s.setStartLocationName("Elstree & Borehamwood Rail Station");
                s.setEndLocationName("Cockfosters Underground Station");
                s.setDistanceInKm(17.6);
                s.setStartLinkDistanceInKm(0.2);
                s.setEndLinkDistanceInKm(0.03);
                break;
            case 11:
                s.setSectionId("12");
                s.setStartLocationName("Cockfosters Underground Station");
                s.setEndLocationName("Enfield Lock Rail Station");
                s.setDistanceInKm(13.7);
                s.setStartLinkDistanceInKm(0.03);
                s.setEndLinkDistanceInKm(0.39);
                break;
            case 12:
                s.setSectionId("13");
                s.setStartLocationName("Enfield Lock Rail Station");
                s.setEndLocationName("Chigwell Underground Station");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(0.39);
                s.setEndLinkDistanceInKm(0.37);
                break;
            case 13:
                s.setSectionId("14");
                s.setStartLocationName("Chigwell Underground Station");
                s.setEndLocationName("Harold Wood Rail Station");
                s.setDistanceInKm(18.2);
                s.setStartLinkDistanceInKm(0.37);
                s.setEndLinkDistanceInKm(0.07);
                break;
            case 14:
                s.setSectionId("15");
                s.setStartLocationName("Harold Wood Rail Station");
                s.setEndLocationName("Purfleet Rail Station");
                s.setDistanceInKm(22.3);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0);
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
