package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.dataprovider.CoordinateProvider;
import com.hubbardgary.londontrails.dataprovider.POIProvider;
import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.view.interfaces.IMapContentView;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.PathViewModel;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

import java.util.List;

public class MapContentPresenter {

    private IMapContentView view;
    private ShowMapViewModel showMapVm;

    public MapContentPresenter(IMapContentView view, ShowMapViewModel showMapVm) {
        this.view = view;
        this.showMapVm = showMapVm;
    }

    public MapContentViewModel getMapContentViewModel() {
        MapContentViewModel vm = new MapContentViewModel();

        vm.path = getPath();
        vm = setStartCoordinates(vm);
        vm = setEndCoordinates(vm);
        vm = initialiseMaxMinLatLon(vm);

        vm.poi = getPOI();
        vm = updateMaxMinLatLng(vm, vm.poi);

        return vm;
    }

    public PathViewModel getPath() {
        int start = showMapVm.start;
        int end = showMapVm.end;

        // If anti-clockwise, swap start and end and calculate as normal
        if(!showMapVm.isClockwise) {
            start = showMapVm.end;
            end = showMapVm.start;
        }
        CoordinateProvider cp = new CoordinateProvider(showMapVm.route, view.getAssetManager(), start, end);
        PathViewModel path = new PathViewModel();
        path.setWayPoints(cp.GetPathWayPoints());
        return path;
    }

    private List<POI> getPOI() {
        int start = showMapVm.start;
        int end = showMapVm.end;

        // If anti-clockwise, swap start and end and calculate as normal
        if(!showMapVm.isClockwise) {
            start = showMapVm.end;
            end = showMapVm.start;
        }
        POIProvider pp = new POIProvider(showMapVm.route, view.getAssetManager(), start, end);
        return pp.getPOIsForRoute();
    }

    private MapContentViewModel setStartCoordinates(MapContentViewModel vm) {
        if (showMapVm.isClockwise) {
            vm.startLatitude = vm.path.getWayPointLat(0);
            vm.startLongitude = vm.path.getWayPointLng(0);
        }
        else {
            vm.startLatitude = vm.path.getWayPointLat(vm.path.getWayPoints().length - 1);
            vm.startLongitude = vm.path.getWayPointLng(vm.path.getWayPoints().length - 1);
        }
        return vm;
    }

    private MapContentViewModel setEndCoordinates(MapContentViewModel vm) {
        if (showMapVm.isClockwise) {
            vm.endLatitude = vm.path.getWayPointLat(vm.path.getWayPoints().length - 1);
            vm.endLongitude = vm.path.getWayPointLng(vm.path.getWayPoints().length - 1);
        }
        else {
            vm.endLatitude = vm.path.getWayPointLat(0);
            vm.endLongitude = vm.path.getWayPointLng(0);
        }
        return vm;
    }

    private MapContentViewModel initialiseMaxMinLatLon(MapContentViewModel vm) {
        vm.maximumLatitude = vm.startLatitude > vm.endLatitude ? vm.startLatitude : vm.endLatitude;
        vm.minimumLatitude = vm.startLatitude < vm.endLatitude ? vm.startLatitude : vm.endLatitude;
        vm.maximumLongitude = vm.startLongitude > vm.endLongitude ? vm.startLongitude : vm.endLongitude;
        vm.minimumLongitude = vm.startLongitude < vm.endLongitude ? vm.startLongitude : vm.endLongitude;
        return vm;
    }

    private MapContentViewModel updateMaxMinLatLng(MapContentViewModel vm, double latitude, double longitude) {
        if (latitude > vm.maximumLatitude)
            vm.maximumLatitude = latitude;
        if (latitude < vm.minimumLatitude)
            vm.minimumLatitude = latitude;
        if (longitude > vm.maximumLongitude)
            vm.maximumLongitude = longitude;
        if (longitude < vm.minimumLongitude)
            vm.minimumLongitude = longitude;
        return vm;
    }

    private MapContentViewModel updateMaxMinLatLng(MapContentViewModel vm, List<POI> points) {
        for (int i = 0; i < points.size(); i++) {
            updateMaxMinLatLng(vm, points.get(i).getLatitude(), points.get(i).getLongitude());
        }
        return vm;
    }
}