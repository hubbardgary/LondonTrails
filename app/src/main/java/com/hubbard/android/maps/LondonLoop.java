package com.hubbard.android.maps;

import java.util.ArrayList;

public class LondonLoop extends Route {

    public LondonLoop() {
        name = "London Loop";
        circular = false;
        sections = new Section[15];
        endPoints = populateEndPoints();

        for(int i = 0; i < sections.length; i++) {
            sections[i] = populateSections(i);
        }
    }

    private String[] populateEndPoints() {
        return new String[] {
            "Erith Rail Station",
            "Bexley Rail Station",
            "Petts Wood Rail Station",
            "Hayes Rail Station",
            "Hamsey Green Bus Stop",
            "Banstead Rail Station",
            "Kingston Rail Station",
            "Hatton Cross Underground Station",
            "Uxbridge Underground Station",
            "Moor Park Underground Station",
            "Elstree & Borehamwood Rail Station",
            "Cockfosters Underground Station",
            "Enfield Lock Rail Station",
            "Chigwell Underground Station",
            "Harold Wood Rail Station",
            "Purfleet Rail Station"
        };
    }

    private Section populateSections(int section) {
        Section s = new Section();

        // Initialise WayPoint arrays but don't populate until we know we'll use them
        s.sectionWayPoints = new ArrayList<WayPoint>();

        switch (section) {
            case 0:
                s.startLinkResource = "ll-01-start_link.kml";
                s.sectionResource = "ll-01-erith-bexley.kml";
                s.endLinkResource = "ll-01-end_link.kml";
                s.poiResource = "ll-01-placemarks.kml";
                s.distanceInKm = 13;
                s.startLinkDistanceInKm = 0.33;
                s.endLinkDistanceInKm = 0.14;
                break;
            case 1:
                s.startLinkResource = "ll-02-start_link.kml";
                s.sectionResource = "ll-02-bexley-jubilee_park.kml";
                s.endLinkResource = "ll-02-end_link.kml";
                s.poiResource = "ll-02-placemarks.kml";
                s.distanceInKm = 11.3;
                s.startLinkDistanceInKm = 0.14;
                s.endLinkDistanceInKm = 0.74;
                break;
            case 2:
                s.startLinkResource = "ll-03-start_link.kml";
                s.sectionResource = "ll-03-jubilee_park-west_wickham.kml";
                s.endLinkResource = "ll-03-end_link.kml";
                s.poiResource = "ll-03-placemarks.kml";
                s.distanceInKm = 13.6;
                s.startLinkDistanceInKm = 0.74;
                s.endLinkDistanceInKm = 0.96;
                break;
            case 3:
                s.startLinkResource = "ll-04-start_link.kml";
                s.sectionResource = "ll-04-west_wickham-hamsey_green.kml";
                s.endLinkResource = "ll-04-end_link.kml";
                s.poiResource = "ll-04-placemarks.kml";
                s.distanceInKm = 13.5;
                s.startLinkDistanceInKm = 0.96;
                s.endLinkDistanceInKm = 0.06;
                break;
            case 4:
                s.startLinkResource = "ll-05-start_link.kml";
                s.sectionResource = "ll-05-hamsey_green-banstead_downs.kml";
                s.endLinkResource = "ll-05-end_link.kml";
                s.poiResource = "ll-05-placemarks.kml";
                s.distanceInKm = 17.3;
                s.startLinkDistanceInKm = 0.06;
                s.endLinkDistanceInKm = 0.46;
                break;
            case 5:
                s.startLinkResource = "ll-06-start_link.kml";
                s.sectionResource = "ll-06-banstead_downs-kingston_bridge.kml";
                s.endLinkResource = "ll-06-end_link.kml";
                s.poiResource = "ll-06-placemarks.kml";
                s.distanceInKm = 17.4;
                s.startLinkDistanceInKm = 0.46;
                s.endLinkDistanceInKm = 0.61;
                break;
            case 6:
                s.startLinkResource = "ll-07-start_link.kml";
                s.sectionResource = "ll-07-kingston_bridge-donkey_wood.kml";
                s.endLinkResource = "ll-07-end_link.kml";
                s.poiResource = "ll-07-placemarks.kml";
                s.distanceInKm = 15.3;
                s.startLinkDistanceInKm = 0.61;
                s.endLinkDistanceInKm = 0.16;
                break;
            case 7:
                s.startLinkResource = "ll-08-start_link.kml";
                s.sectionResource = "ll-08-donkey_wood-uxbridge_lock.kml";
                s.endLinkResource = "ll-08-end_link.kml";
                s.poiResource = "ll-08-placemarks.kml";
                s.distanceInKm = 17.5;
                s.startLinkDistanceInKm = 0.16;
                s.endLinkDistanceInKm = 0.76;
                break;
            case 8:
                s.startLinkResource = "ll-09-start_link.kml";
                s.sectionResource = "ll-09-uxbridge_lock-moor_park.kml";
                s.endLinkResource = "ll-09-end_link.kml";
                s.poiResource = "ll-09-placemarks.kml";
                s.distanceInKm = 15.3;
                s.startLinkDistanceInKm = 0.76;
                s.endLinkDistanceInKm = 0.74;
                break;
            case 9:
                s.startLinkResource = "ll-10-start_link.kml";
                s.sectionResource = "ll-10-moor_park-elstree.kml";
                s.endLinkResource = "ll-10-end_link.kml";
                s.poiResource = "ll-10-placemarks.kml";
                s.distanceInKm = 19.5;
                s.startLinkDistanceInKm = 0.74;
                s.endLinkDistanceInKm = 0.2;
                break;
            case 10:
                s.startLinkResource = "ll-11-start_link.kml";
                s.sectionResource = "ll-11-elstree-cockfosters.kml";
                s.endLinkResource = "ll-11-end_link.kml";
                s.poiResource = "ll-11-placemarks.kml";
                s.distanceInKm = 17.6;
                s.startLinkDistanceInKm = 0.2;
                s.endLinkDistanceInKm = 0.03;
                break;
            case 11:
                s.startLinkResource = "ll-12-start_link.kml";
                s.sectionResource = "ll-12-cockfosters-enfield_lock.kml";
                s.endLinkResource = "ll-12-end_link.kml";
                s.poiResource = "ll-12-placemarks.kml";
                s.distanceInKm = 13.7;
                s.startLinkDistanceInKm = 0.03;
                s.endLinkDistanceInKm = 0.39;
                break;
            case 12:
                s.startLinkResource = "ll-13-start_link.kml";
                s.sectionResource = "ll-13-enfield_lock-chigwell.kml";
                s.endLinkResource = "ll-13-end_link.kml";
                s.poiResource = "ll-13-placemarks.kml";
                s.distanceInKm = 13.6;
                s.startLinkDistanceInKm = 0.39;
                s.endLinkDistanceInKm = 0.37;
                break;
            case 13:
                s.startLinkResource = "ll-14-start_link.kml";
                s.sectionResource = "ll-14-chigwell-harold_wood.kml";
                s.endLinkResource = "ll-14-end_link.kml";
                s.poiResource = "ll-14-placemarks.kml";
                s.distanceInKm = 18.2;
                s.startLinkDistanceInKm = 0.37;
                s.endLinkDistanceInKm = 0.07;
                break;
            case 14:
                s.startLinkResource = "ll-15-start_link.kml";
                s.sectionResource = "ll-15-harold_wood-purfleet.kml";
                s.endLinkResource = "";
                s.poiResource = "ll-15-placemarks.kml";
                s.distanceInKm = 22.3;
                s.startLinkDistanceInKm = 0.07;
                s.endLinkDistanceInKm = 0;
                break;
        }
        return s;
    }

}
