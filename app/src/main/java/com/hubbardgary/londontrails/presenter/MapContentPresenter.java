package com.hubbardgary.londontrails.presenter;

import com.hubbardgary.londontrails.dataprovider.interfaces.ICoordinateProvider;
import com.hubbardgary.londontrails.dataprovider.interfaces.IPOIProvider;
import com.hubbardgary.londontrails.model.dto.RoutePoiDto;
import com.hubbardgary.londontrails.model.dto.RouteCoordinatesDto;
import com.hubbardgary.londontrails.view.interfaces.IMapContentView;
import com.hubbardgary.londontrails.viewmodel.MapContentViewModel;
import com.hubbardgary.londontrails.viewmodel.PathViewModel;
import com.hubbardgary.londontrails.viewmodel.ShowMapViewModel;

public class MapContentPresenter {

    private IMapContentView view;
    private ICoordinateProvider coordinateProvider;
    private IPOIProvider poiProvider;
    private ShowMapViewModel showMapVm;

    public MapContentPresenter(IMapContentView view,
                               ShowMapViewModel showMapVm,
                               ICoordinateProvider coordinateProvider,
                               IPOIProvider poiProvider) {
        this.view = view;
        this.showMapVm = showMapVm;
        this.coordinateProvider = coordinateProvider;
        this.coordinateProvider.initialize(showMapVm.route, view.getAssetManager());
        this.poiProvider = poiProvider;
        this.poiProvider.initialize(showMapVm.route, view.getAssetManager());
    }

    public MapContentViewModel getMapContentViewModel() {
        MapContentViewModel vm = new MapContentViewModel();

        RouteCoordinatesDto routeCoordinatesDto = getPath();
        RoutePoiDto routePoiDto = getPOI();

        vm.path = new PathViewModel();

        if (routeCoordinatesDto != null) {
            vm.path.setCoordinates(routeCoordinatesDto.getCoordinates());
        }

        if (routePoiDto != null) {
            vm.poi = routePoiDto.getPOIs();
        }

        vm = setStartCoordinates(vm);
        vm = setEndCoordinates(vm);
        vm = setMaxMinLatLon(vm, routeCoordinatesDto, routePoiDto);

        return vm;
    }

    private RouteCoordinatesDto getPath() {
        int start = showMapVm.start;
        int end = showMapVm.end;

        // If anti-clockwise, swap start and end and calculate as normal
        if(!showMapVm.isClockwise) {
            start = showMapVm.end;
            end = showMapVm.start;
        }

        return coordinateProvider.getRouteCoordinates(start, end);
    }

    private RoutePoiDto getPOI() {
        int start = showMapVm.start;
        int end = showMapVm.end;

        // If anti-clockwise, swap start and end and calculate as normal
        if(!showMapVm.isClockwise) {
            start = showMapVm.end;
            end = showMapVm.start;
        }

        return poiProvider.getPOIsForRoute(start, end);
    }

    private MapContentViewModel setStartCoordinates(MapContentViewModel vm) {
        if (showMapVm.isClockwise) {
            vm.startLatitude = vm.path.getCoordinateLat(0);
            vm.startLongitude = vm.path.getCoordinateLng(0);
        }
        else {
            vm.startLatitude = vm.path.getCoordinateLat(vm.path.getCoordinates().size() - 1);
            vm.startLongitude = vm.path.getCoordinateLng(vm.path.getCoordinates().size() - 1);
        }
        return vm;
    }

    private MapContentViewModel setEndCoordinates(MapContentViewModel vm) {
        if (showMapVm.isClockwise) {
            vm.endLatitude = vm.path.getCoordinateLat(vm.path.getCoordinates().size() - 1);
            vm.endLongitude = vm.path.getCoordinateLng(vm.path.getCoordinates().size() - 1);
        }
        else {
            vm.endLatitude = vm.path.getCoordinateLat(0);
            vm.endLongitude = vm.path.getCoordinateLng(0);
        }
        return vm;
    }

    private MapContentViewModel setMaxMinLatLon(MapContentViewModel vm, RouteCoordinatesDto coordinates, RoutePoiDto poi) {
        if (poi == null || poi.getPOIs() == null || poi.getPOIs().size() == 0) {
            vm.minimumLatitude = coordinates.getMinimumLatitude();
            vm.maximumLatitude = coordinates.getMaximumLatitude();
            vm.minimumLongitude = coordinates.getMinimumLongitude();
            vm.maximumLongitude = coordinates.getMaximumLongitude();
        } else {
            vm.minimumLatitude = coordinates.getMinimumLatitude() < poi.getMinimumLatitude() ? coordinates.getMinimumLatitude() : poi.getMinimumLatitude();
            vm.maximumLatitude = coordinates.getMaximumLatitude() > poi.getMaximumLatitude() ? coordinates.getMaximumLatitude() : poi.getMaximumLatitude();
            vm.minimumLongitude = coordinates.getMinimumLongitude() < poi.getMinimumLongitude() ? coordinates.getMinimumLongitude() : poi.getMinimumLongitude();
            vm.maximumLongitude = coordinates.getMaximumLongitude() > poi.getMaximumLongitude() ? coordinates.getMaximumLongitude() : poi.getMaximumLongitude();
        }
        return vm;
    }
}