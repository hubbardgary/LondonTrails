package com.hubbardgary.londontrails.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.PolylineOptions;
import com.hubbardgary.londontrails.config.GlobalObjects;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.PathViewModel;
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.presenter.MapContentPresenter;

public class ShowMapActivity extends Activity implements LocationListener,
                                                         LocationSource,
                                                         GooglePlayServicesClient.ConnectionCallbacks,
                                                         GooglePlayServicesClient.OnConnectionFailedListener {
    private GoogleMap map;
    private LocationManager locationManager;
    private OnLocationChangedListener locationChangedListener = null;
    private Criteria criteria = new Criteria();

    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    private LocationRequest locationRequest;
    private LocationClient locationClient;
    private boolean updatesRequested;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private LatLngBounds.Builder defaultBounds;
    private List<Marker> markers;
    private boolean markersVisible = true;
    private int start;
    private int end;
    private int direction;

    private GlobalObjects glob;
    private Route currentRoute;
    private boolean mapHasLoaded = false;
    private boolean initialAnimationHasFired = false;

    private Polyline mapRoute;  // Provides a means of accessing the polyline from within the map

    private static final int DEFAULT_BOUNDS_PADDING = 100;
    private static final int INITIAL_CAMERA_ANIMATION_SPEED_MS = 800;


    public GoogleMap getMap() {
        return map;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Route getCurrentRoute() {
        return currentRoute;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        start = getIntent().getExtras().getInt("startSection");
        end = getIntent().getExtras().getInt("endSection");
        direction = getIntent().getExtras().getInt("direction");

        // Open the shared preferences
        prefs = getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE);

        // Default to Terrain view if no preference is present
        int mapType = prefs.getInt(getString(R.string.shared_prefs_map_type), GoogleMap.MAP_TYPE_TERRAIN);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.setMapType(mapType);

        glob = (GlobalObjects) getApplicationContext();
        currentRoute = glob.getCurrentRoute();
        setTitle(currentRoute.getName() + ": Section " + String.valueOf(start + 1) + " - " + String.valueOf(end + 1));

        SetPOIWindowAdapter();

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
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); // How do you just make it use the best available?
        map.setMyLocationEnabled(true);

        boolean updatesRequested;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        editor = prefs.edit();
        // Create a new location client, using the enclosing class to handle callbacks.
        locationClient = new LocationClient(this, this, this);
        // Start with updates turned off
        updatesRequested = true;
    }

    private void SetPOIWindowAdapter() {
        map.setInfoWindowAdapter(new com.hubbardgary.londontrails.view.InfoWindowAdapter(this));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_option_streetmap:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                glob.setMapPreference(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.view_option_satellite:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                glob.setMapPreference(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.view_option_terrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                glob.setMapPreference(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.view_option_hide_markers:
                for (Marker m : markers) {
                    m.setVisible(false);
                }
                markersVisible = false;
                return true;
            case R.id.view_option_show_markers:
                for (Marker m : markers) {
                    m.setVisible(true);
                }
                markersVisible = true;
                return true;
            case R.id.view_option_reset_focus:
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), DEFAULT_BOUNDS_PADDING);
                map.animateCamera(cu);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        SubMenu submenu = menu.addSubMenu(Menu.NONE, Menu.NONE, 1, "Change Map View");
        submenu.add(Menu.NONE, R.id.view_option_streetmap, 1, R.string.streetmap_view);
        submenu.add(Menu.NONE, R.id.view_option_satellite, 2, R.string.satellite_view);
        submenu.add(Menu.NONE, R.id.view_option_terrain, 3, R.string.terrain_view);

        if (markersVisible) {
            menu.add(Menu.NONE, R.id.view_option_hide_markers, 4, R.string.hide_markers);
        } else {
            menu.add(Menu.NONE, R.id.view_option_show_markers, 4, R.string.show_markers);
        }
        menu.add(Menu.NONE, R.id.view_option_reset_focus, 5, R.string.reset_focus);

        switch (map.getMapType()) {
            case GoogleMap.MAP_TYPE_NORMAL:
                submenu.removeItem(R.id.view_option_streetmap);
                break;
            case GoogleMap.MAP_TYPE_HYBRID:
                submenu.removeItem(R.id.view_option_satellite);
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                submenu.removeItem(R.id.view_option_terrain);
                break;
        }
        return true;
    }
    @Override
    public void activate(OnLocationChangedListener listener) {
        locationChangedListener = listener;
    }
    @Override
    public void deactivate() {
        locationChangedListener = null;
    }
    @Override
    public void onLocationChanged(Location location) {
        if (locationChangedListener != null) {
            locationChangedListener.onLocationChanged(location);
            LatLngBounds bounds = this.map.getProjection().getVisibleRegion().latLngBounds;
            if (!bounds.contains(new LatLng(location.getLatitude(), location.getLongitude()))) {
                //Move the camera to the user's location once it's available
                map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        }
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or latLngStart periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        //Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }
    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        //Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }
    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * latLngStart a Google Play services activity that can resolve
		 * error.
		 */
        // IGNORING THIS FOR NOW
    }
    @Override
    protected void onPause() {
        // Save the current setting for updates
        editor.putBoolean("KEY_UPDATES_ON", updatesRequested);
        editor.commit();
        super.onPause();
    }
    @Override
    protected void onStart() {
        locationClient.connect();
        super.onStart();
    }
    @Override
    protected void onResume() {
		/*
		 * Get any previous setting for location updates
		 * Gets "false" if an error occurs
		 */
        if (prefs.contains("KEY_UPDATES_ON")) {
            updatesRequested = prefs.getBoolean("KEY_UPDATES_ON", false);

            // Otherwise, turn off location updates
        } else {
            editor.putBoolean("KEY_UPDATES_ON", false);
            editor.commit();
        }
        super.onResume();
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

        public MapContentActivity(ShowMapActivity showMapActivity) {
            this.showMapActivity = showMapActivity;
            presenter = new MapContentPresenter(this);
            vm = presenter.getMapContentViewModel();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            drawPath(vm.path);

            // Set default zoom bounds based on the route and points of interest we're plotting
            initializeDefaultBounds(new LatLng(vm.minimumLatitude, vm.minimumLongitude), new LatLng(vm.maximumLatitude, vm.maximumLongitude));
            return 1;
        }

        @Override
        protected void onPostExecute(Integer i) {
            showMapActivity.setMapRoute(showMapActivity.getMap().addPolyline(line));

            if (showMapActivity.getStart() == showMapActivity.getEnd()) {
                pushPin(vm.startLatitude, vm.startLongitude, showMapActivity.getCurrentRoute().getEndPoint(showMapActivity.getStart()), "Your walk starts and ends here.", R.drawable.waypoint_startstop);
            } else {
                pushPin(vm.startLatitude, vm.startLongitude, showMapActivity.getCurrentRoute().getEndPoint(showMapActivity.getStart()), "Your walk starts here.", R.drawable.waypoint_start);
                pushPin(vm.endLatitude, vm.endLongitude, showMapActivity.getCurrentRoute().getEndPoint(showMapActivity.getEnd()), "Your walk ends here.", R.drawable.waypoint_stop);
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

        public void initializeDefaultBounds(LatLng minLatLng, LatLng maxLatLng) {
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            bounds.include(minLatLng);
            bounds.include(maxLatLng);
            showMapActivity.setDefaultBounds(bounds);
        }

        @Override
        public Route getRoute() {
            return glob.getCurrentRoute();
        }

        @Override
        public AssetManager getAssetManager() {
            return getApplicationContext().getAssets();
        }

        @Override
        public int getStart() {
            return start;
        }

        @Override
        public int getEnd() {
            return end;
        }

        @Override
        public boolean isClockwise() {
            return direction == 0;
        }
    }
}
