package com.hubbardgary.londontrails.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.app.interfaces.ILondonTrailsApp;
import com.hubbardgary.londontrails.config.UserSettings;
import com.hubbardgary.londontrails.model.LondonTrailsPlacemark;
import com.hubbardgary.londontrails.presenter.ShowMapPresenter;
import com.hubbardgary.londontrails.view.interfaces.IShowMapView;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

public class ShowMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        IShowMapView {

    private static final int DEFAULT_BOUNDS_PADDING = 100;
    private static final int INITIAL_CAMERA_ANIMATION_SPEED_MS = 800;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 1;

    private final Context currentContext = this;
    private GoogleMap map;
    private LatLngBounds.Builder defaultBounds;
    private List<LondonTrailsPlacemark> placemarks;
    private Polyline mapRoute;  // Provides a means of accessing the polyline from within the map
    private boolean mapLoaded = false;
    private boolean showToast = true;
    private boolean toastTriggered = false;

    private ShowMapPresenter presenter;
    private ShowMapViewModel vm;

    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public int getIntFromIntent(String item) {
        return getIntent().getExtras().getInt(item);
    }

    @Override
    public String getStringFromResources(int resourceId) {
        return getResources().getString(resourceId);
    }

    @Override
    public void updateViewModel(ShowMapViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void setMarkerVisibility(boolean visibility) {
        for (LondonTrailsPlacemark p : placemarks) {
            if (!p.isAlwaysShow()) {
                p.getGoogleMapsMarker().setVisible(visibility);
            }
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

    public GoogleMap getMap() {
        return map;
    }

    public ShowMapViewModel getShowMapVm() {
        return vm;
    }

    public void setMapRoute(Polyline mapRoute) {
        this.mapRoute = mapRoute;
    }

    public void setPlacemarks(List<LondonTrailsPlacemark> placemarks) {
        this.placemarks = placemarks;
    }

    public void setDefaultBounds(LatLngBounds.Builder defaultBounds) {
        this.defaultBounds = defaultBounds;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setUpMapIfNeeded();
    }

    private void initializeLocationServices() {
        enableZoomControls();
        enableMyLocationIfAllowed();
    }

    private void enableZoomControls() {
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private void enableMyLocationIfAllowed() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, MY_PERMISSION_ACCESS_FINE_LOCATION);
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfAllowed();
                }
        }
    }

    @Override
    protected void onResume() {
        showToast = true;
        if(!mapLoaded && !toastTriggered) {
            setInternetConnectionAlert(10);
            toastTriggered = true;
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        showToast = false;
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
        initializeLocationServices();
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setMapType(vm.mapType);
        map.setInfoWindowAdapter(new InfoWindowAdapter(this));

        // Attempts to move camera before map has loaded will fail.
        // http://stackoverflow.com/questions/13692579/movecamera-with-cameraupdatefactory-newlatlngbounds-crashes
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapLoaded = true;
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
            setUpMap();
        }
    }

    private void setUpMap() {
        presenter = new ShowMapPresenter(this, new UserSettings((ILondonTrailsApp)getApplicationContext()));
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

    /**
     * Trigger an alert if map hasn't loaded after the given time period in seconds.
     * This will pop up a toast message suggesting the user checks their Internet connection.
     */
    private void setInternetConnectionAlert(int delayInSeconds) {
        ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        Runnable checkInternetTask = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(showToast && !((ShowMapActivity)currentContext).mapLoaded) {
                            Toast.makeText(
                                    currentContext,
                                    getString(R.string.check_internet_connection),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                        toastTriggered = false;
                    }
                });
            }
        };
        scheduler.schedule(checkInternetTask, delayInSeconds, TimeUnit.SECONDS);
    }

}
