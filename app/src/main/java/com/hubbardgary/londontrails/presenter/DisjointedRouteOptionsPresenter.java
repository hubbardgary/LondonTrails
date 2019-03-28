package com.hubbardgary.londontrails.presenter;

import android.content.res.Resources;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.model.Route;
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
        HashMap<String, Integer> intents = new HashMap<String, Integer>();
        intents.put("startSection", vm.startSection);
        intents.put("endSection", vm.startSection);
        view.invokeActivity(intents, ShowMapActivity.class);
    }

    public String[] getDirections() {
        return res.getStringArray(R.array.directions);
    }

    public double calculateDistanceInKm(int startSection) {
        return route.getSection(startSection).getDistanceInKm();
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
        return vm;
    }

    public RouteViewModel optionsChanged(RouteViewModel vm) {
        vm = updateDistance(vm);
        view.refreshDistance(vm);
        return vm;
    }
}
