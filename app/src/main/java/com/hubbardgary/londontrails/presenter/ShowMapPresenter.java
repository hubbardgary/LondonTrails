package com.hubbardgary.londontrails.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.view.IShowMapView;
import com.hubbardgary.londontrails.viewmodel.MenuViewModel;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import java.util.LinkedHashMap;

public class ShowMapPresenter {

    private IShowMapView view;
    private GlobalObjects globals;
    private ShowMapViewModel vm;
    private int mapType;
    private boolean markersVisible = true;

    public ShowMapPresenter(IShowMapView view, GlobalObjects globals) {
        this.view = view;
        this.globals = globals;
        this.mapType = globals.getMapPreference();
        vm = new ShowMapViewModel(view.getIntFromIntent("startSection"), view.getIntFromIntent("endSection"), view.getIntFromIntent("direction"), globals.getCurrentRoute(), mapType);
        vm.optionsMenu = initialiseMenu();
        vm.mapTypeSubMenu = initialiseMapTypeSubMenu();
    }

    public ShowMapViewModel getViewModel() {
        return vm;
    }

    private MenuViewModel initialiseMenu() {
        MenuViewModel menuVm = new MenuViewModel();
        menuVm.menuItems = new LinkedHashMap<Integer, String>();
        if(markersVisible) {
            menuVm.menuItems.put(R.id.view_option_hide_markers, globals.getStringFromResource(R.string.hide_markers));
        } else {
            menuVm.menuItems.put(R.id.view_option_show_markers, globals.getStringFromResource(R.string.show_markers));
        }
        menuVm.menuItems.put(R.id.view_option_reset_focus, globals.getStringFromResource(R.string.reset_focus));
        menuVm.menuItems.put(R.id.view_option_where_am_i, globals.getStringFromResource(R.string.where_am_i));
        return menuVm;
    }

    private MenuViewModel initialiseMapTypeSubMenu() {
        MenuViewModel mapTypeSubMenu = new MenuViewModel();
        mapTypeSubMenu.nameResource = R.string.change_map_view;
        mapTypeSubMenu.menuItems = new LinkedHashMap<Integer, String>();

        if(mapType != GoogleMap.MAP_TYPE_HYBRID)
            mapTypeSubMenu.menuItems.put(R.id.view_option_satellite, globals.getStringFromResource(R.string.satellite_view));
        if(mapType != GoogleMap.MAP_TYPE_NORMAL)
            mapTypeSubMenu.menuItems.put(R.id.view_option_streetmap, globals.getStringFromResource(R.string.streetmap_view));
        if(mapType != GoogleMap.MAP_TYPE_TERRAIN)
            mapTypeSubMenu.menuItems.put(R.id.view_option_terrain, globals.getStringFromResource(R.string.terrain_view));
        return mapTypeSubMenu;
    }

    public void menuItemSelected(int itemId) {
        switch (itemId) {
            case R.id.view_option_streetmap:
                updateMapType(GoogleMap.MAP_TYPE_NORMAL);
                return;
            case R.id.view_option_satellite:
                updateMapType(GoogleMap.MAP_TYPE_HYBRID);
                return;
            case R.id.view_option_terrain:
                updateMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return;
            case R.id.view_option_hide_markers:
                setMarkerVisibility(false);
                return;
            case R.id.view_option_show_markers:
                setMarkerVisibility(true);
                return;
            case R.id.view_option_reset_focus:
                view.resetCameraPosition();
                return;
            case R.id.view_option_where_am_i:
                view.goToMyLocation();
                return;
        }
        view.updateViewModel(vm);
    }

    private void updateMapType(int mapType) {
        this.mapType = mapType;
        globals.setMapPreference(mapType);
        view.setMapType(mapType);
        vm.mapType = mapType;

        // Refresh menu so current map type isn't available as an option
        vm.mapTypeSubMenu = initialiseMapTypeSubMenu();
    }

    private void setMarkerVisibility(boolean showMarkers) {
        markersVisible = showMarkers;
        vm.markersVisible = showMarkers;

        // Refresh menu to hide non-applicable options
        vm.optionsMenu = initialiseMenu();
        view.setMarkerVisibility(showMarkers);
    }
}
