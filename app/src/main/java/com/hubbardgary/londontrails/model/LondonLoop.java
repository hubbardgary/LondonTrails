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
        setDistanceInKm(distanceInKM);
        setCircular(false);
        setLinear(true);
        setSections(new Section[15]);

        for (int i = 0; i < getSections().length; i++) {
            setSection(i, populateSections(i));
        }
    }

    private Section populateSections(int section) {
        Section s = new Section();

        switch (section) {
            case 0:
                s.setStartLocationName("Erith Rail Station");
                s.setEndLocationName("Bexley Rail Station");
                s.setStartLinkResource("ll-01-start_link.kml");
                s.setSectionResource("ll-01-erith-bexley.kml");
                s.setEndLinkResource("ll-01-end_link.kml");
                s.setPoiResource("ll-01-placemarks.kml");
                s.setDistanceInKm(13);
                s.setStartLinkDistanceInKm(0.33);
                s.setEndLinkDistanceInKm(0.14);
                break;
            case 1:
                s.setStartLocationName("Bexley Rail Station");
                s.setEndLocationName("Petts Wood Rail Station");
                s.setStartLinkResource("ll-02-start_link.kml");
                s.setSectionResource("ll-02-bexley-jubilee_park.kml");
                s.setEndLinkResource("ll-02-end_link.kml");
                s.setPoiResource("ll-02-placemarks.kml");
                s.setDistanceInKm(11.3);
                s.setStartLinkDistanceInKm(0.14);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 2:
                s.setStartLocationName("Petts Wood Rail Station");
                s.setEndLocationName("Hayes Rail Station");
                s.setStartLinkResource("ll-03-start_link.kml");
                s.setSectionResource("ll-03-jubilee_park-west_wickham.kml");
                s.setEndLinkResource("ll-03-end_link.kml");
                s.setPoiResource("ll-03-placemarks.kml");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(0.96);
                break;
            case 3:
                s.setStartLocationName("Hayes Rail Station");
                s.setEndLocationName("Hamsey Green Bus Stop");
                s.setStartLinkResource("ll-04-start_link.kml");
                s.setSectionResource("ll-04-west_wickham-hamsey_green.kml");
                s.setEndLinkResource("ll-04-end_link.kml");
                s.setPoiResource("ll-04-placemarks.kml");
                s.setDistanceInKm(13.5);
                s.setStartLinkDistanceInKm(0.96);
                s.setEndLinkDistanceInKm(0.06);
                break;
            case 4:
                s.setStartLocationName("Hamsey Green Bus Stop");
                s.setEndLocationName("Banstead Rail Station");
                s.setStartLinkResource("ll-05-start_link.kml");
                s.setSectionResource("ll-05-hamsey_green-banstead_downs.kml");
                s.setEndLinkResource("ll-05-end_link.kml");
                s.setPoiResource("ll-05-placemarks.kml");
                s.setDistanceInKm(17.3);
                s.setStartLinkDistanceInKm(0.06);
                s.setEndLinkDistanceInKm(0.46);
                break;
            case 5:
                s.setStartLocationName("Banstead Rail Station");
                s.setEndLocationName("Kingston Rail Station");
                s.setStartLinkResource("ll-06-start_link.kml");
                s.setSectionResource("ll-06-banstead_downs-kingston_bridge.kml");
                s.setEndLinkResource("ll-06-end_link.kml");
                s.setPoiResource("ll-06-placemarks.kml");
                s.setDistanceInKm(17.4);
                s.setStartLinkDistanceInKm(0.46);
                s.setEndLinkDistanceInKm(0.61);
                break;
            case 6:
                s.setStartLocationName("Kingston Rail Station");
                s.setEndLocationName("Hatton Cross Underground Station");
                s.setStartLinkResource("ll-07-start_link.kml");
                s.setSectionResource("ll-07-kingston_bridge-donkey_wood.kml");
                s.setEndLinkResource("ll-07-end_link.kml");
                s.setPoiResource("ll-07-placemarks.kml");
                s.setDistanceInKm(15.3);
                s.setStartLinkDistanceInKm(0.61);
                s.setEndLinkDistanceInKm(0.16);
                break;
            case 7:
                s.setStartLocationName("Hatton Cross Underground Station");
                s.setEndLocationName("Uxbridge Underground Station");
                s.setStartLinkResource("ll-08-start_link.kml");
                s.setSectionResource("ll-08-donkey_wood-uxbridge_lock.kml");
                s.setEndLinkResource("ll-08-end_link.kml");
                s.setPoiResource("ll-08-placemarks.kml");
                s.setDistanceInKm(17.5);
                s.setStartLinkDistanceInKm(0.16);
                s.setEndLinkDistanceInKm(0.76);
                break;
            case 8:
                s.setStartLocationName("Uxbridge Underground Station");
                s.setEndLocationName("Moor Park Underground Station");
                s.setStartLinkResource("ll-09-start_link.kml");
                s.setSectionResource("ll-09-uxbridge_lock-moor_park.kml");
                s.setEndLinkResource("ll-09-end_link.kml");
                s.setPoiResource("ll-09-placemarks.kml");
                s.setDistanceInKm(15.3);
                s.setStartLinkDistanceInKm(0.76);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 9:
                s.setStartLocationName("Moor Park Underground Station");
                s.setEndLocationName("Elstree & Borehamwood Rail Station");
                s.setStartLinkResource("ll-10-start_link.kml");
                s.setSectionResource("ll-10-moor_park-elstree.kml");
                s.setEndLinkResource("ll-10-end_link.kml");
                s.setPoiResource("ll-10-placemarks.kml");
                s.setDistanceInKm(19.5);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(0.2);
                break;
            case 10:
                s.setStartLocationName("Elstree & Borehamwood Rail Station");
                s.setEndLocationName("Cockfosters Underground Station");
                s.setStartLinkResource("ll-11-start_link.kml");
                s.setSectionResource("ll-11-elstree-cockfosters.kml");
                s.setEndLinkResource("ll-11-end_link.kml");
                s.setPoiResource("ll-11-placemarks.kml");
                s.setDistanceInKm(17.6);
                s.setStartLinkDistanceInKm(0.2);
                s.setEndLinkDistanceInKm(0.03);
                break;
            case 11:
                s.setStartLocationName("Cockfosters Underground Station");
                s.setEndLocationName("Enfield Lock Rail Station");
                s.setStartLinkResource("ll-12-start_link.kml");
                s.setSectionResource("ll-12-cockfosters-enfield_lock.kml");
                s.setEndLinkResource("ll-12-end_link.kml");
                s.setPoiResource("ll-12-placemarks.kml");
                s.setDistanceInKm(13.7);
                s.setStartLinkDistanceInKm(0.03);
                s.setEndLinkDistanceInKm(0.39);
                break;
            case 12:
                s.setStartLocationName("Enfield Lock Rail Station");
                s.setEndLocationName("Chigwell Underground Station");
                s.setStartLinkResource("ll-13-start_link.kml");
                s.setSectionResource("ll-13-enfield_lock-chigwell.kml");
                s.setEndLinkResource("ll-13-end_link.kml");
                s.setPoiResource("ll-13-placemarks.kml");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(0.39);
                s.setEndLinkDistanceInKm(0.37);
                break;
            case 13:
                s.setStartLocationName("Chigwell Underground Station");
                s.setEndLocationName("Harold Wood Rail Station");
                s.setStartLinkResource("ll-14-start_link.kml");
                s.setSectionResource("ll-14-chigwell-harold_wood.kml");
                s.setEndLinkResource("ll-14-end_link.kml");
                s.setPoiResource("ll-14-placemarks.kml");
                s.setDistanceInKm(18.2);
                s.setStartLinkDistanceInKm(0.37);
                s.setEndLinkDistanceInKm(0.07);
                break;
            case 14:
                s.setStartLocationName("Harold Wood Rail Station");
                s.setEndLocationName("Purfleet Rail Station");
                s.setStartLinkResource("ll-15-start_link.kml");
                s.setSectionResource("ll-15-harold_wood-purfleet.kml");
                s.setEndLinkResource("");
                s.setPoiResource("ll-15-placemarks.kml");
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
