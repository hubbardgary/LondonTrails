package com.hubbardgary.londontrails.view;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hubbardgary.londontrails.R;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.presenter.MapContentPresenter;
import com.hubbardgary.londontrails.view.interfaces.IMapContentView;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.PathViewModel;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import java.util.ArrayList;
import java.util.List;

 /*
 * Called when map has been displayed to perform potentially expensive
 * route calculation without blocking the UI thread.
 */
public class MapContentActivity extends AsyncTask<Void, Void, Integer> implements IMapContentView {

    private ShowMapActivity activity;
    private ShowMapActivity showMapActivity;
    PolylineOptions path;
    MapContentPresenter presenter;
    MapContentViewModel vm;
    ShowMapViewModel showMapVm;

    public MapContentActivity(ShowMapActivity activity, ShowMapActivity showMapActivity) {
        this.activity = activity;
        this.showMapActivity = showMapActivity;
        showMapVm = showMapActivity.getShowMapVm();
        presenter = new MapContentPresenter(this, showMapVm);
        vm = presenter.getMapContentViewModel();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        getPath(vm.path);

        // Set default zoom bounds based on the route and points of interest we're plotting
        initializeDefaultBounds(vm.minimumLatitude, vm.minimumLongitude, vm.maximumLatitude, vm.maximumLongitude);
        return 1;
    }

    /*
     * Route and markers have now been retrieved, so display them on the map.
     */
    @Override
    protected void onPostExecute(Integer i) {
        showMapActivity.setMapRoute(showMapActivity.getMap().addPolyline(path));

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
    }

    public void getPath(PathViewModel path) {
        if (path.getWayPoints().length > 1) {    // If length is 1, we only have 1 point so can't draw a line
            this.path = new PolylineOptions();
            this.path.width(5);
            this.path.color(Color.RED);

            LatLng from = new LatLng((path.getWayPointLat(0)), (path.getWayPointLng(0)));
            LatLng to;

            for (int i = 1; i < path.getWayPoints().length; i++) {
                to = new LatLng((path.getWayPointLat(i)), (path.getWayPointLng(i)));
                this.path.add(from, to);
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
        return activity.getApplicationContext().getAssets();
    }
}
