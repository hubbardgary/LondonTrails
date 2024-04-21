package com.hubbardgary.londontrails.testhelpers;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.Section;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.model.interfaces.ISection;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class RouteHelpers {

    private static ISection setupLinearSection(IRoute route,
                                             String id,
                                             String startLocation,
                                             String endLocation,
                                             double distanceInKm,
                                             double startLinkDistanceInKm,
                                             double endLinkDistanceInKm) {
        ISection section = new Section(route);
        section.setSectionId(id);
        section.setStartLocationName(startLocation);
        section.setEndLocationName(endLocation);
        section.setDistanceInKm(distanceInKm);
        section.setStartLinkDistanceInKm(startLinkDistanceInKm);
        section.setEndLinkDistanceInKm(endLinkDistanceInKm);
        return section;
    }

    private static ISection setupNonLinearSection(IRoute route,
                                                String id,
                                                String startLocation,
                                                String endLocation,
                                                double distanceInKm,
                                                double startLinkDistanceInKm,
                                                double endLinkDistanceInKm,
                                                double extensionDistanceInKm,
                                                String extensionDescription) {
        ISection section = setupLinearSection(route, id, startLocation, endLocation, distanceInKm, startLinkDistanceInKm, endLinkDistanceInKm);
        section.setExtensionDistanceInKm(extensionDistanceInKm);
        section.setExtensionDescription(extensionDescription);
        return section;
    }

    public static void setupLinearCircularRoute(IRoute mockRoute, IRouteOptionsView mockView, IUserSettings mockUserSettings) {
        // Linear Circular
        // Start links         (1.2)       (0.3)         (0.0)        (0.5)
        // Section distance  a (7.2)---> b (12.8) ---> c (6.9) ---> a (8.3)
        // End links           (0.3)       (0.0)         (0.5)        (1.2)
        ISection section0 = setupLinearSection(mockRoute, "01", "a", "b", 7.2, 1.2, 0.3);
        ISection section1 = setupLinearSection(mockRoute, "02", "b", "c", 12.8, 0.3, 0.0);
        ISection section2 = setupLinearSection(mockRoute, "03", "c", "d", 6.9, 0.0, 0.5);
        ISection section3 = setupLinearSection(mockRoute, "04", "d", "a", 8.3, 0.5, 1.2);

        when(mockRoute.getSection(0)).thenReturn(section0);
        when(mockRoute.getSection(1)).thenReturn(section1);
        when(mockRoute.getSection(2)).thenReturn(section2);
        when(mockRoute.getSection(3)).thenReturn(section3);

        when(mockRoute.getName()).thenReturn("Fake Linear Circular Route");
        when(mockRoute.isCircular()).thenReturn(true);
        when(mockRoute.getSections()).thenReturn(new ISection[]{section0, section1, section2, section3});
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[]{"a", "b", "c", "d"});
        when(mockView.getIntegerFromResources(R.integer.Clockwise)).thenReturn(0);
        when(mockView.getIntegerFromResources(R.integer.AntiClockwise)).thenReturn(1);
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
    }

    public static void setupLinearNonCircularRoute(IRoute mockRoute, IRouteOptionsView mockView, IUserSettings mockUserSettings) {
        // Linear Non-Circular
        // Start links         (1.2)       (0.3)         (0.0)        (0.5)
        // Section distance  a (7.2)---> b (12.8) ---> c (6.9) ---> d (14.9)
        // End links           (0.3)       (0.0)         (0.5)        (1.3)
        ISection section0 = setupLinearSection(mockRoute, "01", "a", "b", 7.2, 1.2, 0.3);
        ISection section1 = setupLinearSection(mockRoute, "02", "b", "c", 12.8, 0.3, 0.0);
        ISection section2 = setupLinearSection(mockRoute, "03", "c", "d", 6.9, 0.0, 0.5);
        ISection section3 = setupLinearSection(mockRoute, "04", "d", "e", 14.9, 0.5, 1.3);

        when(mockRoute.getSection(0)).thenReturn(section0);
        when(mockRoute.getSection(1)).thenReturn(section1);
        when(mockRoute.getSection(2)).thenReturn(section2);
        when(mockRoute.getSection(3)).thenReturn(section3);

        when(mockRoute.getName()).thenReturn("Fake Linear Non-Circular Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.getSections()).thenReturn(new ISection[]{section0, section1, section2, section3});
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[]{"a", "b", "c", "d"});
        when(mockView.getIntegerFromResources(R.integer.Clockwise)).thenReturn(0);
        when(mockView.getIntegerFromResources(R.integer.AntiClockwise)).thenReturn(1);
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
    }

    public static void setupNonLinearRoute(IRoute mockRoute, IRouteOptionsView mockView, IUserSettings mockUserSettings) {
        ISection section0 = setupNonLinearSection(mockRoute, "01", "a start", "a end", 5.5, 0.1, 0.6, 0.0, "");
        ISection section1 = setupNonLinearSection(mockRoute, "02", "b start", "b end", 7.2, 0.6, 0.2, 0.8, "b extension");
        ISection section2 = setupNonLinearSection(mockRoute, "03", "c start", "c end", 10.3, 0.0, 0.9, 1.5, "c extension");
        ISection section3 = setupNonLinearSection(mockRoute, "04", "d start", "d end", 6.4, 1.2, 0.1, 0.0, "");

        when(mockRoute.getSection(0)).thenReturn(section0);
        when(mockRoute.getSection(1)).thenReturn(section1);
        when(mockRoute.getSection(2)).thenReturn(section2);
        when(mockRoute.getSection(3)).thenReturn(section3);

        when(mockRoute.getName()).thenReturn("Fake Non-Linear Route");
        when(mockRoute.isCircular()).thenReturn(false);
        when(mockRoute.getSections()).thenReturn(new ISection[]{section0, section1, section2, section3});
        when(mockView.getRouteSectionsFromIntent()).thenReturn(1234);
        when(mockView.getStringArrayFromResources(anyInt())).thenReturn(new String[]{"a", "b", "c", "d"});
        when(mockUserSettings.getCurrentRoute()).thenReturn(mockRoute);
    }
}
