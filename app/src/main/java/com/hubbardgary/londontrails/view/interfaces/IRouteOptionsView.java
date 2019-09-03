package com.hubbardgary.londontrails.view.interfaces;

import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import java.util.Map;

public interface IRouteOptionsView {
    void invokeActivity(Map<String, Integer> intents, Class<?> activity);
    void endActivity();
    int getRouteSectionsFromIntent();
    void refreshDestinationSpinner(RouteViewModel vm);
    void refreshDistance(RouteViewModel vm);
}
