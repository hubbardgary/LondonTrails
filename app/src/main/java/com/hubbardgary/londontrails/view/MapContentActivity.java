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
import com.hubbardgary.londontrails.dataprovider.CoordinateProvider;
import com.hubbardgary.londontrails.dataprovider.POIProvider;
import com.hubbardgary.londontrails.model.LondonTrailsPlacemark;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.presenter.MapContentPresenter;
import com.hubbardgary.londontrails.view.interfaces.IMapContentView;
import com.hubbardgary.londontrails.view.interfaces.IShowMapView;
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

    private IShowMapView showMapView;
    private PolylineOptions path;
    private MapContentViewModel vm;
    private ShowMapViewModel showMapVm;

    MapContentActivity(IShowMapView showMapView) {
        this.showMapView = showMapView;
        showMapVm = showMapView.getShowMapVm();
        MapContentPresenter presenter = new MapContentPresenter(this, showMapVm, new CoordinateProvider(), new POIProvider());
        vm = presenter.getMapContentViewModel();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        getPath(vm.path);

        // Set default zoom bounds based on the route and points of interest we're plotting
        initializeDefaultBounds(vm.minimumLatitude, vm.minimumLongitude, vm.maximumLatitude, vm.maximumLongitude);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer i) {
        // Route and markers have now been retrieved, so display them on the map.
        showMapView.setMapRoute(showMapView.getMap().addPolyline(path));
        addStartAndEndMarkers(showMapVm.start, showMapVm.end, showMapVm.route);
        addWayPointMarkers();
    }

    @Override
    public void getPath(PathViewModel path) {
        if (path.getCoordinates().size() > 1) {    // If length is 1, we only have 1 point so can't draw a line
            this.path = new PolylineOptions();
            this.path.width(5);
            this.path.color(Color.RED);

            LatLng from = new LatLng((path.getCoordinateLat(0)), (path.getCoordinateLng(0)));
            LatLng to;

            for (int i = 1; i < path.getCoordinates().size(); i++) {
                to = new LatLng((path.getCoordinateLat(i)), (path.getCoordinateLng(i)));
                this.path.add(from, to);
                from = new LatLng(to.latitude, to.longitude);
            }
        }
    }

    @Override
    public void initializeDefaultBounds(double minLatitude, double minLongitude, double maxLatitude, double maxLongitude) {
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        bounds.include(new LatLng(minLatitude, minLongitude));
        bounds.include(new LatLng(maxLatitude, maxLongitude));
        showMapView.setDefaultBounds(bounds);
    }

    @Override
    public AssetManager getAssetManager() {
        return showMapView.getApplicationContextFromView().getAssets();
    }

     private void addStartAndEndMarkers(int start, int end, IRoute route) {
         if (showMapVm.route.isLinear() && start == end) {
             // Linear, and we're walking the full circuit
             pushPin(vm.startLatitude, vm.startLongitude, route.getSection(start).getStartLocationName(), "Your walk starts and ends here.", R.drawable.waypoint_startstop);
             return;
         }

         String startLabel, endLabel;
         if (route.isLinear()) {
             if (route.isCircular()) {
                 if (showMapVm.isClockwise) {
                     startLabel = route.getSection(start).getStartLocationName();
                     endLabel = route.getSection(end).getStartLocationName();
                 } else {
                     startLabel = route.getSection(start).getStartLocationName();
                     endLabel = route.getSection(end).getStartLocationName();
                 }
             } else {
                 if (showMapVm.isClockwise) {
                     startLabel = route.getSection(start).getStartLocationName();
                     endLabel = route.getSection(end - 1).getEndLocationName();
                 } else {
                     startLabel = route.getSection(start - 1).getEndLocationName();
                     endLabel = route.getSection(end).getStartLocationName();
                 }
             }
         } else {
             startLabel = route.getSection(start).getStartLocationName();
             endLabel = route.getSection(end).getEndLocationName();
         }

         pushPin(vm.startLatitude, vm.startLongitude, startLabel, "Your walk starts here.", R.drawable.waypoint_start);
         pushPin(vm.endLatitude, vm.endLongitude, endLabel, "Your walk ends here.", R.drawable.waypoint_stop);
     }

     private void addWayPointMarkers() {
         List<LondonTrailsPlacemark> placemarks = new ArrayList<>();
         for (POI p : vm.poi) {
             Marker marker = pushPin(
                     p.getLatitude(),
                     p.getLongitude(),
                     p.getTitle(),
                     p.getSnippet(),
                     p.getIsAlternativeEndPoint() ? R.drawable.waypoint_stop : R.drawable.waypoint_pause
             );

             placemarks.add(new LondonTrailsPlacemark(marker, p.getIsAlternativeEndPoint()));
         }
         showMapView.setPlacemarks(placemarks);
     }

     private Marker pushPin(double latitude, double longitude, String title, String snippet, int icon) {
         return showMapView.getMap().addMarker(new MarkerOptions()
                 .position(new LatLng(latitude, longitude))
                 .title(title)
                 .snippet(snippet)
                 .icon(BitmapDescriptorFactory.fromResource(icon)));
     }
}
