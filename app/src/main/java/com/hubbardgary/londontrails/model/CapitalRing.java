package com.hubbardgary.londontrails.model;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.interfaces.ISection;
import com.hubbardgary.londontrails.util.Helpers;

import java.util.Locale;

public class CapitalRing extends Route {

    private static final double distanceInKM = 122;
    private static final String name = "Capital Ring";

    public int getRouteId() {
        return R.id.rte_capital_ring;
    }

    public CapitalRing() {
        setName(name);
        setShortName("cr");
        setDistanceInKm(distanceInKM);
        setCircular(true);
        setLinear(true);
        setSections(new ISection[15]);

        for (int i = 0; i < getSections().length; i++) {
            setSection(i, populateSections(i));
        }
    }

    private ISection populateSections(int section) {
        ISection s = new Section(this);

        switch (section) {
            case 0:
                s.setSectionId("01");
                s.setStartSectionName("Woolwich");
                s.setEndSectionName("Falconwood");
                s.setStartLocationName("Woolwich Arsenal Rail Station");
                s.setEndLocationName("Falconwood Rail Station");
                s.setDistanceInKm(9.02);
                s.setStartLinkDistanceInKm(1.21);
                s.setEndLinkDistanceInKm(0.5);
                break;
            case 1:
                s.setSectionId("02");
                s.setStartSectionName("Falconwood");
                s.setEndSectionName("Grove Park");
                s.setStartLocationName("Falconwood Rail Station");
                s.setEndLocationName("Grove Park Rail Station");
                s.setDistanceInKm(6.27);
                s.setStartLinkDistanceInKm(0.5);
                s.setEndLinkDistanceInKm(0.01);
                break;
            case 2:
                s.setSectionId("03");
                s.setStartSectionName("Grove Park");
                s.setEndSectionName("Crystal Palace");
                s.setStartLocationName("Grove Park Rail Station");
                s.setEndLocationName("Crystal Palace Rail Station");
                s.setDistanceInKm(12.4);
                s.setStartLinkDistanceInKm(0.01);
                s.setEndLinkDistanceInKm(0);
                break;
            case 3:
                s.setSectionId("04");
                s.setStartSectionName("Crystal Palace");
                s.setEndSectionName("Streatham");
                s.setStartLocationName("Crystal Palace Rail Station");
                s.setEndLocationName("Streatham Common Rail Station");
                s.setDistanceInKm(6.57);
                s.setStartLinkDistanceInKm(0);
                s.setEndLinkDistanceInKm(0.2);
                break;
            case 4:
                s.setSectionId("05");
                s.setStartSectionName("Streatham");
                s.setEndSectionName("Wimbledon Park");
                s.setStartLocationName("Streatham Common Rail Station");
                s.setEndLocationName("Wimbledon Park Underground Station");
                s.setDistanceInKm(8.84);
                s.setStartLinkDistanceInKm(0.2);
                s.setEndLinkDistanceInKm(0);
                break;
            case 5:
                s.setSectionId("06");
                s.setStartSectionName("Wimbledon Park");
                s.setEndSectionName("Richmond Bridge");
                s.setStartLocationName("Wimbledon Park Underground Station");
                s.setEndLocationName("Richmond Rail Station");
                s.setDistanceInKm(11);
                s.setStartLinkDistanceInKm(0);
                s.setEndLinkDistanceInKm(0.8);
                break;
            case 6:
                s.setSectionId("07");
                s.setStartSectionName("Richmond Bridge");
                s.setEndSectionName("Osterley Lock");
                s.setStartLocationName("Richmond Rail Station");
                s.setEndLocationName("Boston Manor Underground Station");
                s.setDistanceInKm(6.23);
                s.setStartLinkDistanceInKm(0.8);
                s.setEndLinkDistanceInKm(0.83);
                break;
            case 7:
                s.setSectionId("08");
                s.setStartSectionName("Osterley Lock");
                s.setEndSectionName("Greenford");
                s.setStartLocationName("Boston Manor Underground Station");
                s.setEndLocationName("Greenford Station (Rail and Underground)");
                s.setDistanceInKm(7.82);
                s.setStartLinkDistanceInKm(0.83);
                s.setEndLinkDistanceInKm(0.29);
                break;
            case 8:
                s.setSectionId("09");
                s.setStartSectionName("Greenford");
                s.setEndSectionName("South Kenton");
                s.setStartLocationName("Greenford Station (Rail and Underground)");
                s.setEndLocationName("South Kenton Station (Overground and Underground)");
                s.setDistanceInKm(8.74);
                s.setStartLinkDistanceInKm(0.29);
                s.setEndLinkDistanceInKm(0);
                break;
            case 9:
                s.setSectionId("10");
                s.setStartSectionName("South Kenton");
                s.setEndSectionName("Hendon");
                s.setStartLocationName("South Kenton Station (Overground and Underground)");
                s.setEndLocationName("Hendon Central Underground Station");
                s.setDistanceInKm(9.78);
                s.setStartLinkDistanceInKm(0);
                s.setEndLinkDistanceInKm(0.74);
                break;
            case 10:
                s.setSectionId("11");
                s.setStartSectionName("Hendon");
                s.setEndSectionName("Highgate");
                s.setStartLocationName("Hendon Central Underground Station");
                s.setEndLocationName("Highgate Underground Station");
                s.setDistanceInKm(8.21);
                s.setStartLinkDistanceInKm(0.74);
                s.setEndLinkDistanceInKm(0.13);
                break;
            case 11:
                s.setSectionId("12");
                s.setStartSectionName("Highgate");
                s.setEndSectionName("Stoke Newington");
                s.setStartLocationName("Highgate Underground Station");
                s.setEndLocationName("Stoke Newington Rail Station");
                s.setDistanceInKm(8.38);
                s.setStartLinkDistanceInKm(0.13);
                s.setEndLinkDistanceInKm(0.14);
                break;
            case 12:
                s.setSectionId("13");
                s.setStartSectionName("Stoke Newington");
                s.setEndSectionName("Hackney Wick");
                s.setStartLocationName("Stoke Newington Rail Station");
                s.setEndLocationName("Hackney Wick Rail Station");
                s.setDistanceInKm(6.03);
                s.setStartLinkDistanceInKm(0.14);
                s.setEndLinkDistanceInKm(0.43);
                break;
            case 13:
                s.setSectionId("14");
                s.setStartSectionName("Hackney Wick");
                s.setEndSectionName("Beckton District Park");
                s.setStartLocationName("Hackney Wick Rail Station");
                s.setEndLocationName("Royal Albert DLR Station");
                s.setDistanceInKm(7.47);
                s.setStartLinkDistanceInKm(0.43);
                s.setEndLinkDistanceInKm(0.45);
                break;
            case 14:
                s.setSectionId("15");
                s.setStartSectionName("Beckton District Park");
                s.setEndSectionName("Woolwich");
                s.setStartLocationName("Royal Albert DLR Station");
                s.setEndLocationName("Woolwich Arsenal Rail Station");
                s.setDistanceInKm(4.49);
                s.setStartLinkDistanceInKm(0.45);
                s.setEndLinkDistanceInKm(1.21);
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
