package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.GreenChainWalk;
import com.hubbardgary.londontrails.view.AboutActivity;
import com.hubbardgary.londontrails.view.DisjointedRouteOptionsActivity;
import com.hubbardgary.londontrails.view.interfaces.IMainView;
import com.hubbardgary.londontrails.viewmodel.ButtonViewModel;
import com.hubbardgary.londontrails.model.CapitalRing;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.model.LondonLoop;
import com.hubbardgary.londontrails.view.RouteOptionsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPresenter {

    private IMainView view;
    private List<ButtonViewModel> buttons;

    public MainPresenter(IMainView view) {
        this.view = view;
    }

    public void initializeView() {
        view.displayButtons(getButtons());
        view.checkGooglePlayAvailability();
    }

    private List<ButtonViewModel> getButtons() {
        if(buttons == null) {
            buttons = new ArrayList<>();
            buttons.add(new ButtonViewModel(R.id.rte_capital_ring, GlobalObjects.getButtonText(CapitalRing.getRouteName(), CapitalRing.getRouteDistanceText())));
            buttons.add(new ButtonViewModel(R.id.rte_london_loop, GlobalObjects.getButtonText(LondonLoop.getRouteName(), LondonLoop.getRouteDistanceText())));
            buttons.add(new ButtonViewModel(R.id.rte_green_chain_walk, GlobalObjects.getButtonText(GreenChainWalk.getRouteName(), GreenChainWalk.getRouteDistanceText())));
        }
        return buttons;
    }

    public void routeButtonClicked(int routeId) {
        HashMap<String, Integer> intents = new HashMap<>();
        intents.put("routeSections", GlobalObjects.getSectionsFromRouteId(routeId));
        // TODO: Try to get linear/disjointed from the selected route
        if (routeId == R.id.rte_green_chain_walk) {
            view.invokeActivity(intents, DisjointedRouteOptionsActivity.class);
        } else {
            view.invokeActivity(intents, RouteOptionsActivity.class);
        }
    }

    public void menuItemSelected(int itemId) {
        switch (itemId) {
            case R.id.view_option_about:
                view.invokeActivity(new HashMap<String, Integer>(), AboutActivity.class);
                return;
            case android.R.id.home:
                view.endActivity();
                return;
        }
    }
}