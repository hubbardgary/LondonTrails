package com.hubbardgary.londontrails.view;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.maps.model.PolylineOptions;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.presenter.ShowMapPresenter;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.PathViewModel;
import com.hubbardgary.londontrails.presenter.MapContentPresenter;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

public class ShowMapActivity extends Activity implements IShowMapView {
    private GoogleMap map;
    private LatLngBounds.Builder defaultBounds;
    private List<Marker> markers;

    private boolean mapHasLoaded = false;
    private boolean initialAnimationHasFired = false;

    private Polyline mapRoute;  // Provides a means of accessing the polyline from within the map

    private static final int DEFAULT_BOUNDS_PADDING = 100;
    private static final int INITIAL_CAMERA_ANIMATION_SPEED_MS = 800;

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

        presenter = new ShowMapPresenter(this, (GlobalObjects) getApplicationContext());
        vm = presenter.getViewModel();
        setTitle(vm.name);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(vm.mapType);
        map.setInfoWindowAdapter(new com.hubbardgary.londontrails.view.InfoWindowAdapter(this));

        // Perform heavy lifting on a background thread
        new MapContentActivity(this).execute();

        // Attempts to move camera before map has loaded will fail.
        // http://stackoverflow.com/questions/13692579/movecamera-with-cameraupdatefactory-newlatlngbounds-crashes
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                zoomIn();
            }
        });

        initializeLocationServices();
    }

    private void initializeLocationServices() {
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
    }

    private void zoomIn() {
        mapHasLoaded = true;
        if(defaultBounds != null) {
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), DEFAULT_BOUNDS_PADDING);
            map.animateCamera(cu, INITIAL_CAMERA_ANIMATION_SPEED_MS, null);
            initialAnimationHasFired = true;
        }
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

    /*
     * Called when map has been displayed to perform potentially expensive
     * route calculation without blocking the UI thread.
     */
    public class MapContentActivity extends AsyncTask<Void, Void, Integer> implements IMapContentView {

        private ShowMapActivity showMapActivity;
        PolylineOptions line;
        MapContentPresenter presenter;
        MapContentViewModel vm;
        ShowMapViewModel showMapVm;

        public MapContentActivity(ShowMapActivity showMapActivity) {
            this.showMapActivity = showMapActivity;
            showMapVm = showMapActivity.getShowMapVm();
            presenter = new MapContentPresenter(this, showMapVm);
            vm = presenter.getMapContentViewModel();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            drawPath(vm.path);

            // Set default zoom bounds based on the route and points of interest we're plotting
            initializeDefaultBounds(vm.minimumLatitude, vm.minimumLongitude, vm.maximumLatitude, vm.maximumLongitude);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer i) {
            showMapActivity.setMapRoute(showMapActivity.getMap().addPolyline(line));

            if (showMapVm.start == showMapVm.end) {
                pushPin(vm.startLatitude, vm.startLongitude, showMapVm.route.getEndPoint(showMapVm.start), "Your walk starts and ends here.", R.drawable.waypoint_startstop);
            } else {
                pushPin(vm.startLatitude, vm.startLongitude, showMapVm.route.getEndPoint(showMapVm.start), "Your walk starts here.", R.drawable.waypoint_start);
                pushPin(vm.endLatitude, vm.endLongitude, showMapVm.route.getEndPoint(showMapVm.end), "Your walk ends here.", R.drawable.waypoint_stop);
            }

            List<Marker> markers = new ArrayList<Marker>();
            for (POI p : vm.poi) {
                markers.add(
                    pushPin(
                            p.getLatitude(),
                            p.getLongitude(),
                            p.getTitle(),
                            p.getSnippet(),
                            R.drawable.waypoint_pause
                    )
                );
            }
            showMapActivity.setMarkers(markers);

//            if(mapHasLoaded && !initialAnimationHasFired) {
//                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), DEFAULT_BOUNDS_PADDING);
//                map.animateCamera(cu, INITIAL_CAMERA_ANIMATION_SPEED_MS, null);
//            }
        }

        public void drawPath(PathViewModel path) {
            if (path.getWayPoints().length > 1) {    // If length is 1, we only have 1 point so can't draw a line
                line = new PolylineOptions();
                line.width(5);
                line.color(Color.RED);

                LatLng from = new LatLng((path.getWayPointLat(0)), (path.getWayPointLng(0)));
                LatLng to;

                for (int i = 1; i < path.getWayPoints().length; i++) {
                    to = new LatLng((path.getWayPointLat(i)), (path.getWayPointLng(i)));
                    line.add(from, to);
                    from = new LatLng(to.latitude, to.longitude);
                }
            }
        }

        private Marker pushPin(double latitude, double longitude, String title, String snippet, int icon) {
            return showMapActivity.getMap().addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromResource(icon)));
        }

        @Override
        public void initializeDefaultBounds(double minLatitude, double minLongitude, double maxLatitude, double maxLongitude) {
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            bounds.include(new LatLng(minLatitude, minLongitude));
            bounds.include(new LatLng(maxLatitude, maxLongitude));
            showMapActivity.setDefaultBounds(bounds);
        }
        @Override
        public AssetManager getAssetManager() {
            return getApplicationContext().getAssets();
        }
    }
}
