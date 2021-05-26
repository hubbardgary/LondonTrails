package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.interfaces.ISection;
import com.hubbardgary.londontrails.util.Helpers;

import java.util.Locale;

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
        setSections(new ISection[24]);

        for (int i = 0; i < getSections().length; i++) {
            setSection(i, populateSections(i));
        }
    }

    private ISection populateSections(int section) {
        ISection s = new Section(this);

        switch (section) {
            case 0:
                s.setSectionId("01");
                s.setStartLocationName("Erith Rail Station");
                s.setEndLocationName("Bexley Rail Station");
                s.setDistanceInKm(13.1);
                s.setStartLinkDistanceInKm(0.33);
                s.setEndLinkDistanceInKm(0.14);
                break;
            case 1:
                s.setSectionId("02");
                s.setStartLocationName("Bexley Rail Station");
                s.setEndLocationName("Petts Wood Rail Station");
                s.setDistanceInKm(11.4);
                s.setStartLinkDistanceInKm(0.14);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 2:
                s.setSectionId("03");
                s.setStartLocationName("Petts Wood Rail Station");
                s.setEndLocationName("Hayes Rail Station");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(0.95);
                break;
            case 3:
                s.setSectionId("04");
                s.setStartLocationName("Hayes Rail Station");
                s.setEndLocationName("Hamsey Green Bus Stop");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(0.95);
                s.setEndLinkDistanceInKm(0.06);
                break;
            case 4:
                s.setSectionId("05");
                s.setStartLocationName("Hamsey Green Bus Stop");
                s.setEndLocationName("Coulsdon South Rail Station");
                s.setDistanceInKm(10.0);
                s.setStartLinkDistanceInKm(0.06);
                s.setEndLinkDistanceInKm(0.0);
                break;
            case 5:
                s.setSectionId("06");
                s.setStartLocationName("Coulsdon South Rail Station");
                s.setEndLocationName("Banstead Rail Station");
                s.setDistanceInKm(7.4);
                s.setStartLinkDistanceInKm(0.0);
                s.setEndLinkDistanceInKm(0.46);
                break;
            case 6:
                s.setSectionId("07");
                s.setStartLocationName("Banstead Rail Station");
                s.setEndLocationName("Ewell West Rail Station");
                s.setDistanceInKm(5.6);
                s.setStartLinkDistanceInKm(0.46);
                s.setEndLinkDistanceInKm(0.44);
                break;
            case 7:
                s.setSectionId("08");
                s.setStartLocationName("Ewell West Rail Station");
                s.setEndLocationName("Kingston Rail Station");
                s.setDistanceInKm(12.0);
                s.setStartLinkDistanceInKm(0.44);
                s.setEndLinkDistanceInKm(0.6);
                break;
            case 8:
                s.setSectionId("09");
                s.setStartLocationName("Kingston Rail Station");
                s.setEndLocationName("Hatton Cross Underground Station");
                s.setDistanceInKm(15.4);
                s.setStartLinkDistanceInKm(0.6);
                s.setEndLinkDistanceInKm(0.16);
                break;
            case 9:
                s.setSectionId("10");
                s.setStartLocationName("Hatton Cross Underground Station");
                s.setEndLocationName("Hayes & Harlington Rail Station");
                s.setDistanceInKm(6.2);
                s.setStartLinkDistanceInKm(0.16);
                s.setEndLinkDistanceInKm(0.23);
                break;
            case 10:
                s.setSectionId("11");
                s.setStartLocationName("Hayes & Harlington Rail Station");
                s.setEndLocationName("Uxbridge Underground Station");
                s.setDistanceInKm(11.4);
                s.setStartLinkDistanceInKm(0.23);
                s.setEndLinkDistanceInKm(0.76);
                break;
            case 11:
                s.setSectionId("12");
                s.setStartLocationName("Uxbridge Underground Station");
                s.setEndLocationName("Harefield West Bus Stop");
                s.setDistanceInKm(7.8);
                s.setStartLinkDistanceInKm(0.76);
                s.setEndLinkDistanceInKm(0.29);
                break;
            case 12:
                s.setSectionId("13");
                s.setStartLocationName("Harefield West Bus Stop");
                s.setEndLocationName("Moor Park Underground Station");
                s.setTravelWarning("Buses to and from Harefield West run Monday to Saturday only. Check transport options before you travel.");
                s.setDistanceInKm(7.5);
                s.setStartLinkDistanceInKm(0.29);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 13:
                s.setSectionId("14");
                s.setStartLocationName("Moor Park Underground Station");
                s.setEndLocationName("Hatch End Rail Station");
                s.setDistanceInKm(6.0);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(1.0);
                break;
            case 14:
                s.setSectionId("15");
                s.setStartLocationName("Hatch End Rail Station");
                s.setEndLocationName("Elstree & Borehamwood Rail Station");
                s.setDistanceInKm(13.6);
                s.setStartLinkDistanceInKm(1.0);
                s.setEndLinkDistanceInKm(0.2);
                break;
            case 15:
                s.setSectionId("16");
                s.setStartLocationName("Elstree & Borehamwood Rail Station");
                s.setEndLocationName("Cockfosters Underground Station");
                s.setDistanceInKm(17.6);
                s.setStartLinkDistanceInKm(0.2);
                s.setEndLinkDistanceInKm(0.03);
                break;
            case 16:
                s.setSectionId("17");
                s.setStartLocationName("Cockfosters Underground Station");
                s.setEndLocationName("Enfield Lock Rail Station");
                s.setDistanceInKm(13.8);
                s.setStartLinkDistanceInKm(0.03);
                s.setEndLinkDistanceInKm(0.39);
                break;
            case 17:
                s.setSectionId("18");
                s.setStartLocationName("Enfield Lock Rail Station");
                s.setEndLocationName("Chingford Rail Station");
                s.setDistanceInKm(7.3);
                s.setStartLinkDistanceInKm(0.39);
                s.setEndLinkDistanceInKm(0.29);
                break;
            case 18:
                s.setSectionId("19");
                s.setStartLocationName("Chingford Rail Station");
                s.setEndLocationName("Chigwell Underground Station");
                s.setDistanceInKm(6.3);
                s.setStartLinkDistanceInKm(0.29);
                s.setEndLinkDistanceInKm(0.37);
                break;
            case 19:
                s.setSectionId("20");
                s.setStartLocationName("Chigwell Underground Station");
                s.setEndLocationName("Havering-atte-Bower Bus Stop");
                s.setDistanceInKm(10.1);
                s.setStartLinkDistanceInKm(0.37);
                s.setEndLinkDistanceInKm(0.04);
                break;
            case 20:
                s.setSectionId("21");
                s.setStartLocationName("Havering-atte-Bower Bus Stop");
                s.setEndLocationName("Harold Wood Rail Station");
                s.setTravelWarning("Buses to and from Havering-atte-Bower run infrequently, and do not run on Sundays. Check transport options before you travel.");
                s.setDistanceInKm(8.2);
                s.setStartLinkDistanceInKm(0.04);
                s.setEndLinkDistanceInKm(0.07);
                break;
            case 21:
                s.setSectionId("22");
                s.setStartLocationName("Harold Wood Rail Station");
                s.setEndLocationName("Upminster Bridge Underground Station");
                s.setDistanceInKm(7.0);
                s.setStartLinkDistanceInKm(0.07);
                s.setEndLinkDistanceInKm(0.02);
                break;
            case 22:
                s.setSectionId("23");
                s.setStartLocationName("Upminster Bridge Underground Station");
                s.setEndLocationName("Rainham Rail Station");
                s.setDistanceInKm(7.4);
                s.setStartLinkDistanceInKm(0.02);
                s.setEndLinkDistanceInKm(0.04);
                break;
            case 23:
                s.setSectionId("24");
                s.setStartLocationName("Rainham Rail Station");
                s.setEndLocationName("Purfleet Rail Station");
                s.setDistanceInKm(8.1);
                s.setStartLinkDistanceInKm(0.04);
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
        return String.format(Locale.UK, "approx %.1f km (%.1f miles)", distanceInKM, Helpers.convertKmToMiles(distanceInKM));
    }
}
