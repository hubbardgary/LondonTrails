package com.hubbard.android.maps;

import java.util.ArrayList;

public class LondonLoop extends Route {


    public LondonLoop() {
        circular = false;
        sections = new Section[15];
        for(int i = 0; i < sections.length; i++) {
            sections[i] = populateSection(i);
        }
    }

    private Section populateSection(int section) {
        Section s = new Section();

        // Initialise WayPoint arrays but don't populate until we know we'll use them
        s.startLinkWayPoints = new ArrayList<WayPoint>();
        s.sectionWayPoints = new ArrayList<WayPoint>();
        s.endLinkWayPoints = new ArrayList<WayPoint>();

        switch (section) {
            case 0:
                s.startLinkResource = "ll-01-start_link.kml";
                s.sectionResource = "ll-01-erith-bexley.kml";
                s.endLinkResource = "ll-01-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-01-placemarks.kml";
                break;
            case 1:
                s.startLinkResource = "ll-02-start_link.kml";
                s.sectionResource = "ll-02-bexley-jubilee_park.kml";
                s.endLinkResource = "ll-02-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-02-placemarks.kml";
                break;
            case 2:
                s.startLinkResource = "ll-03-start_link.kml";
                s.sectionResource = "ll-03-jubilee_park-west_wickham.kml";
                s.endLinkResource = "ll-03-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-03-placemarks.kml";
                break;
            case 3:
                s.startLinkResource = "ll-04-start_link.kml";
                s.sectionResource = "ll-04-west_wickham-hamsey_green.kml";
                s.endLinkResource = "ll-04-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-04-placemarks.kml";
                break;
            case 4:
                s.startLinkResource = "ll-05-start_link.kml";
                s.sectionResource = "ll-05-hamsey_green-banstead_downs.kml";
                s.endLinkResource = "ll-05-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-05-placemarks.kml";
                break;
            case 5:
                s.startLinkResource = "ll-06-start_link.kml";
                s.sectionResource = "ll-06-banstead_downs-kingston_bridge.kml";
                s.endLinkResource = "ll-06-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-06-placemarks.kml";
                break;
            case 6:
                s.startLinkResource = "ll-07-start_link.kml";
                s.sectionResource = "ll-07-kingston_bridge-donkey_wood.kml";
                s.endLinkResource = "ll-07-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-07-placemarks.kml";
                break;
            case 7:
                s.startLinkResource = "ll-08-start_link.kml";
                s.sectionResource = "ll-08-donkey_wood-uxbridge_lock.kml";
                s.endLinkResource = "ll-08-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-08-placemarks.kml";
                break;
            case 8:
                s.startLinkResource = "ll-09-start_link.kml";
                s.sectionResource = "ll-09-uxbridge_lock-moor_park.kml";
                s.endLinkResource = "ll-09-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-09-placemarks.kml";
                break;
            case 9:
                s.startLinkResource = "ll-10-start_link.kml";
                s.sectionResource = "ll-10-moor_park-elstree.kml";
                s.endLinkResource = "ll-10-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-10-placemarks.kml";
                break;
            case 10:
                s.startLinkResource = "ll-11-start_link.kml";
                s.sectionResource = "ll-11-elstree-cockfosters.kml";
                s.endLinkResource = "ll-11-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-11-placemarks.kml";
                break;
            case 11:
                s.startLinkResource = "ll-12-start_link.kml";
                s.sectionResource = "ll-12-cockfosters-enfield_lock.kml";
                s.endLinkResource = "ll-12-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-12-placemarks.kml";
                break;
            case 12:
                s.startLinkResource = "ll-13-start_link.kml";
                s.sectionResource = "ll-13-enfield_lock-chigwell.kml";
                s.endLinkResource = "ll-13-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-13-placemarks.kml";
                break;
            case 13:
                s.startLinkResource = "ll-14-start_link.kml";
                s.sectionResource = "ll-14-chigwell-harold_wood.kml";
                s.endLinkResource = "ll-14-end_link.kml";
                s.poiResource = "";
                //s.poiResource = "ll-14-placemarks.kml";
                break;
            case 14:
                s.startLinkResource = "ll-15-start_link.kml";
                s.sectionResource = "ll-15-harold_wood-purfleet.kml";
                s.endLinkResource = "";
                s.poiResource = "";
                //s.poiResource = "ll-15-placemarks.kml";
                break;
        }
        return s;
    }

}
