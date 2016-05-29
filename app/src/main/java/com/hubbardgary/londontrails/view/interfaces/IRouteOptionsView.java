package com.hubbardgary.londontrails.view.interfaces;

import com.hubbardgary.londontrails.viewmodel.RouteViewModel;

import java.util.HashMap;

public interface IRouteOptionsView {
    void invokeActivity(HashMap<String, Integer> intents, Class<?> activity);
    int getRouteSectionsFromIntent();
    void refreshDestinationSpinner(RouteViewModel vm);
    void refreshDistance(RouteViewModel vm);
}
