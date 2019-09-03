package com.hubbardgary.londontrails.presenter;

import android.content.res.Resources;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.Section;
import com.hubbardgary.londontrails.view.ShowMapActivity;
import com.hubbardgary.londontrails.view.interfaces.IRouteOptionsView;
import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import java.util.Arrays;
import java.util.HashMap;

public class DisjointedRouteOptionsPresenter {

    private IRouteOptionsView view;
    private GlobalObjects globals;
    private final int sectionResource;
    private final String[] sectionArray;
    private Resources res;
    private Route route;
    private RouteViewModel routeVm;

    public DisjointedRouteOptionsPresenter(IRouteOptionsView view, GlobalObjects globals, Resources res) {

        this.view = view;
        this.globals = globals;
        this.res = res;
        this.sectionResource = view.getRouteSectionsFromIntent();
        this.sectionArray = res.getStringArray(sectionResource);
        initializePresenter();
        routeVm = new RouteViewModel(route.getName(), res.getStringArray(sectionResource), route.isCircular(), Arrays.asList(getDirections()));
    }

    private void initializePresenter() {
        route = globals.getCurrentRoute();

        switch (sectionResource) {
            case R.array.green_chain_walk_sections:
                route = new GreenChainWalk();
                break;
        }
        globals.setCurrentRoute(route);
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
        return res.getStringArray(R.array.directions);
    }

    private double calculateDistanceInKm(int startSection) {
        Section s = route.getSection(startSection);
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

    private RouteViewModel updateDistance(RouteViewModel vm) {
        vm.distanceKm = calculateDistanceInKm(vm.startSection);
        vm.distanceMiles = GlobalObjects.convertKmToMiles(vm.distanceKm);
        vm.extensionDistanceKm = route.getSection(vm.startSection).getExtensionDistanceInKm();
        vm.extensionDistanceMiles = GlobalObjects.convertKmToMiles(vm.extensionDistanceKm);
        vm.extensionDescription = route.getSection(vm.startSection).getExtensionDescription();
        return vm;
    }

    public RouteViewModel optionsChanged(RouteViewModel vm) {
        vm = updateDistance(vm);
        view.refreshDistance(vm);
        return vm;
    }

    public void menuItemSelected(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                view.endActivity();
                return;
        }
    }
}
