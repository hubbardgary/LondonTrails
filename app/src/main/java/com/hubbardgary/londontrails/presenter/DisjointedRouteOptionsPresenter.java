package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.interfaces.IUserSettings;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.model.interfaces.ISection;
import com.hubbardgary.londontrails.util.Helpers;
import com.hubbardgary.londontrails.view.ShowMapActivity;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import java.util.Arrays;
import java.util.HashMap;

public class DisjointedRouteOptionsPresenter {

    private final IRouteOptionsView view;
    private final IUserSettings settings;
    private final int sectionResource;
    private final String[] sectionArray;
    private IRoute route;
    private final RouteViewModel routeVm;

    public DisjointedRouteOptionsPresenter(IRouteOptionsView view, IUserSettings settings) {

        this.view = view;
        this.settings = settings;
        sectionResource = view.getRouteSectionsFromIntent();
        sectionArray = view.getStringArrayFromResources(sectionResource);
        initializePresenter();
        routeVm = new RouteViewModel(route.getName(), sectionArray, route.isCircular(), Arrays.asList(getDirections()));
    }

    private void initializePresenter() {
        route = settings.getCurrentRoute();

        if (sectionResource == R.array.green_chain_walk_sections) {
            route = new GreenChainWalk();
        }

        settings.setCurrentRoute(route);
    }

    public RouteViewModel getViewModel() {
        return routeVm;
    }

    public void activitySubmit(RouteViewModel vm) {
        HashMap<String, Integer> intents = new HashMap<>();
        intents.put("startSection", vm.startSection);
        // TODO: Remove endSection from DisjointedRouteViewModel. Rename all things to LinearRoute and NonLinearRoute instead of Route and DisjointedRoute.
        intents.put("endSection", vm.startSection);
        view.invokeActivity(intents, ShowMapActivity.class);
    }

    private String[] getDirections() {
        return view.getStringArrayFromResources(R.array.directions);
    }

    private double calculateDistanceInKm(int startSection) {
        ISection s = route.getSection(startSection);
        return s.getDistanceInKm() +
                s.getStartLinkDistanceInKm() +
                s.getEndLinkDistanceInKm();
    }

    public int getSectionId(String section) {
        for(int i = 0; i < sectionArray.length; i++) {
            if(sectionArray[i].equals((section)))
                return i;
        }
        return -1;
    }

    private void updateDistance(RouteViewModel vm) {
        vm.distanceKm = calculateDistanceInKm(vm.startSection);
        vm.distanceMiles = Helpers.convertKmToMiles(vm.distanceKm);
        vm.extensionDistanceKm = route.getSection(vm.startSection).getExtensionDistanceInKm();
        vm.extensionDistanceMiles = Helpers.convertKmToMiles(vm.extensionDistanceKm);
        vm.extensionDescription = route.getSection(vm.startSection).getExtensionDescription();
    }

    public RouteViewModel optionsChanged(RouteViewModel vm) {
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
