package com.hubbardgary.londontrails.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.presenter.ShowMapPresenter;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

public class ShowMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        IShowMapView {

    private static final int DEFAULT_BOUNDS_PADDING = 100;
    private static final int INITIAL_CAMERA_ANIMATION_SPEED_MS = 800;

    private GoogleMap map;
    private LatLngBounds.Builder defaultBounds;
    private List<Marker> markers;
    private Polyline mapRoute;  // Provides a means of accessing the polyline from within the map

    private ShowMapPresenter presenter;
    private ShowMapViewModel vm;

    public GoogleMap getMap() {
        return map;
    }

    public ShowMapViewModel getShowMapVm() {
        return vm;
    }

    public void setMapRoute(Polyline mapRoute) {
        this.mapRoute = mapRoute;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    public void setDefaultBounds(LatLngBounds.Builder defaultBounds) {
        this.defaultBounds = defaultBounds;
    }

    @Override
    public int getIntFromIntent(String item) {
        return getIntent().getExtras().getInt(item);
    }

    @Override
    public void updateViewModel(ShowMapViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void setMarkerVisibility(boolean visibility) {
        for (Marker m : markers) {
            m.setVisible(visibility);
        }
    }

    @Override
    public void resetCameraPosition() {
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), DEFAULT_BOUNDS_PADDING);
        map.animateCamera(cu);
    }

    @Override
    public void setMapType(int mapType) {
        map.setMapType(mapType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        setUpMapIfNeeded();
        initializeLocationServices();
    }

    private void initializeLocationServices() {
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_map, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        SubMenu subMenu = menu.addSubMenu(Menu.NONE, Menu.NONE, 0, vm.mapTypeSubMenu.nameResource);
        addMenuItems(subMenu, vm.mapTypeSubMenu.menuItems);
        addMenuItems(menu, vm.optionsMenu.menuItems);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            presenter.menuItemSelected(item.getItemId());
            return true;
        }
        catch(Exception e) {
            return super.onOptionsItemSelected(item);
        }
    }

    public void addMenuItems(Menu menu, LinkedHashMap<Integer, String> items) {
        int i = 0;
        for (Map.Entry<Integer, String> entry : items.entrySet()) {
            menu.add(Menu.NONE, entry.getKey(), i, entry.getValue());
            i++;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setMapType(vm.mapType);
        map.setInfoWindowAdapter(new InfoWindowAdapter(this));

        // Attempts to move camera before map has loaded will fail.
        // http://stackoverflow.com/questions/13692579/movecamera-with-cameraupdatefactory-newlatlngbounds-crashes
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                zoomToCurrentRoute();
            }
        });
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        presenter = new ShowMapPresenter(this, (GlobalObjects) getApplicationContext());
        vm = presenter.getViewModel();
        setTitle(vm.name);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        // Perform route calculation on a background thread
        new MapContentActivity(this, this).execute();
    }

    private void zoomToCurrentRoute() {
        if(defaultBounds != null) {
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), DEFAULT_BOUNDS_PADDING);
            map.animateCamera(cu, INITIAL_CAMERA_ANIMATION_SPEED_MS, null);
        }
    }

}
