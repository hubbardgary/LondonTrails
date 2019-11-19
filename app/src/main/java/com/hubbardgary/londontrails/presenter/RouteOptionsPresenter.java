package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.model.LondonLoop;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.util.Helpers;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.view.ShowMapActivity;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RouteOptionsPresenter {

    private IRouteOptionsView view;
    private IUserSettings settings;
    private final int sectionResource;
    private final String[] sectionArray;
    private IRoute route;
    private RouteViewModel routeVm;

    public RouteOptionsPresenter(IRouteOptionsView view, IUserSettings settings) {

        this.view = view;
        this.settings = settings;
        sectionResource = view.getRouteSectionsFromIntent();
        sectionArray = view.getStringArrayFromResources(sectionResource);
        initializePresenter();
        routeVm = new RouteViewModel(route.getName(), sectionArray, route.isCircular(), Arrays.asList(getDirections()));
    }

    private void initializePresenter() {
        route = settings.getCurrentRoute();

        switch (sectionResource) {
            case R.array.capital_ring_sections:
                route = new CapitalRing();
                break;
            case R.array.london_loop_sections:
                route = new LondonLoop();
                break;
        }
        settings.setCurrentRoute(route);
    }

    public RouteViewModel getViewModel() {
        return routeVm;
    }

    public void activitySubmit(RouteViewModel vm) {
        HashMap<String, Integer> intents = new HashMap<>();
        intents.put("startSection", vm.startSection);
        intents.put("endSection", vm.endSection);
        if (vm.isCircular) {
            intents.put("direction", vm.direction);
        } else {
            if (vm.startSection < vm.endSection)
                // Route will be walked forwards, so clockwise
                intents.put("direction", view.getIntegerFromResources(R.integer.Clockwise));
            else
                // Route will be walked backwards, so anticlockwise
                intents.put("direction", view.getIntegerFromResources(R.integer.AntiClockwise));
        }
        view.invokeActivity(intents, ShowMapActivity.class);
    }

    private String[] getDirections() {
        return view.getStringArrayFromResources(R.array.directions);
    }

    private double calculateDistanceInKm(int startSection, int endSection, int direction) {
        if( (route.isCircular() && direction == view.getIntegerFromResources(R.integer.AntiClockwise))
            || (!route.isCircular() && startSection > endSection) ) {
            // anti-clockwise, so swap Start and End and calculate as clockwise.
            int temp = endSection;
            endSection = startSection;
            startSection = temp;
        }

        double totalDistance = 0;
        int i = startSection;
        do {
            totalDistance += route.getSection(i).getDistanceInKm();
            i++;

            if (route.isCircular() && i > route.getSections().length - 1)
                i = 0;  // allow wraparound for circular routes

        } while (i != endSection);

        // Add the distance of the start and end links
        int previousSection = endSection - 1;
        if (route.isCircular() && previousSection < 0)
            previousSection = route.getSections().length - 1;

        totalDistance += route.getSection(startSection).getStartLinkDistanceInKm();
        totalDistance += route.getSection(previousSection).getEndLinkDistanceInKm();
        return totalDistance;
    }

    public int getSectionId(String section) {
        for(int i = 0; i < sectionArray.length; i++) {
            if(sectionArray[i].equals((section)))
                return i;
        }
        return -1;
    }

    public RouteViewModel startSectionChanged(RouteViewModel vm) {
        if (!routeVm.isCircular) {
            // If it's not circular, start and end locations cannot be the same
            String dest = sectionArray[vm.endSection];
            List<String> destContents = new ArrayList<>(Arrays.asList(routeVm.sectionsArray));
            destContents.remove(sectionArray[vm.startSection]);
            if (destContents.indexOf(dest) == -1) {
                vm.endSection = getSectionId(destContents.get(0));
                vm.endSelectedIndex = 0;
            } else {
                vm.endSelectedIndex = destContents.indexOf(dest);
            }
            vm.endOptions = destContents;
            view.refreshDestinationSpinner(vm);
        }
        return vm;
    }

    private void updateDistance(RouteViewModel vm) {
        vm.distanceKm = calculateDistanceInKm(vm.startSection, vm.endSection, vm.direction);
        vm.distanceMiles = Helpers.convertKmToMiles(vm.distanceKm);
    }

    public RouteViewModel optionsChanged(RouteViewModel vm) {
        if (!vm.isCircular && vm.startSection == vm.endSection) {
            throw new IllegalArgumentException("Start section and end section must be different for non-circular routes.");
        }
        updateDistance(vm);
        view.refreshDistance(vm);
        return vm;
    }

    public void menuItemSelected(int itemId) {
        if (itemId == android.R.id.home) {
            view.endActivity();
        }
    }
}
