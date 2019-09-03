package com.hubbardgary.londontrails.presenter;

import android.content.res.Resources;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.Section;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class TestHelpers {

    public static Section setupLinearSection(Route route,
                                             String id,
                                             String startLocation,
                                             String endLocation,
                                             double distanceInKm,
                                             double startLinkDistanceInKm,
                                             double endLinkDistanceInKm) {
        Section section = new Section(route);
        section.setSectionId(id);
        section.setStartLocationName(startLocation);
        section.setEndLocationName(endLocation);
        section.setDistanceInKm(distanceInKm);
        section.setStartLinkDistanceInKm(startLinkDistanceInKm);
        section.setEndLinkDistanceInKm(endLinkDistanceInKm);
        return section;
    }

    public static Section setupNonLinearSection(Route route,
                                                String id,
                                                String startLocation,
                                                String endLocation,
                                                double distanceInKm,
                                                double startLinkDistanceInKm,
                                                double endLinkDistanceInKm,
                                                double extensionDistanceInKm,
                                                String extensionDescription) {
        Section section = setupLinearSection(route, id, startLocation, endLocation, distanceInKm, startLinkDistanceInKm, endLinkDistanceInKm);
        section.setExtensionDistanceInKm(extensionDistanceInKm);
        section.setExtensionDescription(extensionDescription);
        return section;
    }

    public static void setupLinearCircularRoute(Route mockRoute, IRouteOptionsView mockView, Resources mockResources, GlobalObjects mockGlobals) {
        // Linear Circular
        // Start links         (1.2)       (0.3)         (0.0)        (0.5)
        // Section distance  a (7.2)---> b (12.8) ---> c (6.9) ---> a (8.3)
        // End links           (0.3)       (0.0)         (0.5)        (1.2)
        Section section0 = setupLinearSection(mockRoute, "a", "a", "b", 7.2, 1.2, 0.3);
        Section section1 = setupLinearSection(mockRoute, "b", "b", "c", 12.8, 0.3, 0.0);
        Section section2 = setupLinearSection(mockRoute, "c", "c", "d", 6.9, 0.0, 0.5);
        Section section3 = setupLinearSection(mockRoute, "d", "d", "a", 8.3, 0.5, 1.2);

        when(mockRoute.getSection(0)).thenReturn(section0);
        when(mockRoute.getSection(1)).thenReturn(section1);
        when(mockRoute.getSection(2)).thenReturn(section2);
        when(mockRoute.getSection(3)).thenReturn(section3);

        when(mockRoute.getName()).thenReturn("Fake Linear Circular Route");
        when(mockRoute.isCircular()).thenReturn(true);
        when(mockRoute.getSections()).thenReturn(new Section[]{section0, section1, section2, section3});
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[]{"a", "b", "c", "d"});
        when(mockResources.getInteger(R.integer.Clockwise)).thenReturn(0);
        when(mockResources.getInteger(R.integer.AntiClockwise)).thenReturn(1);
    }

    public static void setupLinearNonCircularRoute(Route mockRoute, IRouteOptionsView mockView, Resources mockResources, GlobalObjects mockGlobals) {
        // Linear Non-Circular
        // Start links         (1.2)       (0.3)         (0.0)        (0.5)
        // Section distance  a (7.2)---> b (12.8) ---> c (6.9) ---> d (14.9)
        // End links           (0.3)       (0.0)         (0.5)        (1.3)
        Section section0 = setupLinearSection(mockRoute, "a", "a", "b", 7.2, 1.2, 0.3);
        Section section1 = setupLinearSection(mockRoute, "b", "b", "c", 12.8, 0.3, 0.0);
        Section section2 = setupLinearSection(mockRoute, "c", "c", "d", 6.9, 0.0, 0.5);
        Section section3 = setupLinearSection(mockRoute, "d", "d", "e", 14.9, 0.5, 1.3);

        when(mockRoute.getSection(0)).thenReturn(section0);
        when(mockRoute.getSection(1)).thenReturn(section1);
        when(mockRoute.getSection(2)).thenReturn(section2);
        when(mockRoute.getSection(3)).thenReturn(section3);

        when(mockRoute.getName()).thenReturn("Fake Linear Non-Circular Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.getSections()).thenReturn(new Section[]{section0, section1, section2, section3});
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[]{"a", "b", "c", "d"});
        when(mockResources.getInteger(R.integer.Clockwise)).thenReturn(0);
        when(mockResources.getInteger(R.integer.AntiClockwise)).thenReturn(1);
    }

    public static void setupNonLinearRoute(Route mockRoute, IRouteOptionsView mockView, Resources mockResources, GlobalObjects mockGlobals) {
        Section section0 = setupNonLinearSection(mockRoute, "a", "a start", "a end", 5.5, 0.1, 0.6, 0.0, "");
        Section section1 = setupNonLinearSection(mockRoute, "b", "b start", "b end", 7.2, 0.6, 0.2, 0.8, "b extension");
        Section section2 = setupNonLinearSection(mockRoute, "c", "c start", "c end", 10.3, 0.0, 0.9, 1.5, "c extension");
        Section section3 = setupNonLinearSection(mockRoute, "d", "d start", "d end", 6.4, 1.2, 0.1, 0.0, "");

        when(mockRoute.getSection(0)).thenReturn(section0);
        when(mockRoute.getSection(1)).thenReturn(section1);
        when(mockRoute.getSection(2)).thenReturn(section2);
        when(mockRoute.getSection(3)).thenReturn(section3);

        when(mockRoute.getName()).thenReturn("Fake Non-Linear Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.getSections()).thenReturn(new Section[]{section0, section1, section2, section3});
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockGlobals.getCurrentRoute()).thenReturn(mockRoute);
        when(mockResources.getStringArray(anyInt())).thenReturn(new String[]{"a", "b", "c", "d"});
    }
}
