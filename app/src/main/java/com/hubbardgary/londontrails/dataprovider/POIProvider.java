package com.hubbardgary.londontrails.dataprovider;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.hubbardgary.londontrails.model.POI;
import com.hubbardgary.londontrails.model.dto.RoutePoiDto;
import com.hubbardgary.londontrails.model.interfaces.IRoute;
import com.hubbardgary.londontrails.model.interfaces.ISection;

public class POIProvider implements com.hubbardgary.londontrails.dataprovider.interfaces.IPOIProvider {

    private IRoute route;
    private AssetManager assetManager;

    @Override
    public void initialize(IRoute route, AssetManager assetManager) {
        this.route = route;
        this.assetManager = assetManager;
    }

    @Override
    public RoutePoiDto getPOIsForRoute(int startLocation, int endLocation) {
        if (!route.isLinear() && startLocation != endLocation) {
            throw new IllegalArgumentException("Non-linear routes must have matching startLocation and endLocation.");
        }
        if ((route.isCircular() && (startLocation < 0 || startLocation > route.getSections().length - 1))
                || (!route.isCircular() && (startLocation < 0 || startLocation > route.getSections().length))) {
            throw new IllegalArgumentException("startLocation must be a valid section start point.");
        }
        if ((route.isCircular() && (endLocation < 0 || endLocation > route.getSections().length - 1))
                || (!route.isCircular() && (endLocation < 0 || endLocation > route.getSections().length))) {
            throw new IllegalArgumentException("endLocation must be a valid section end point.");
        }
        if (route.isLinear() && !route.isCircular() && endLocation <= startLocation) {
            throw new IllegalArgumentException("endLocation must be greater than startLocation for non-circular routes.");
        }

        RoutePoiDto routePoiDto = new RoutePoiDto();
        int currentLocation = startLocation;
        List<POI> allPOIs = new ArrayList<>();
        do {
            InputStream is = null;
            try {
                ISection s = route.getSection(currentLocation);
                is = assetManager.open(s.getPoiResource(route.getShortName()));

                List<POI> pois = getPOIs(is);

                for (POI poi : pois) {

                    if (allPOIs.isEmpty() || poi.getLatitude() > routePoiDto.getMaximumLatitude()) {
                        routePoiDto.setMaximumLatitude(poi.getLatitude());
                    }
                    if (allPOIs.isEmpty() || poi.getLongitude() > routePoiDto.getMaximumLongitude()) {
                        routePoiDto.setMaximumLongitude(poi.getLongitude());
                    }
                    if (allPOIs.isEmpty() || poi.getLatitude() < routePoiDto.getMinimumLatitude()) {
                        routePoiDto.setMinimumLatitude(poi.getLatitude());
                    }
                    if (allPOIs.isEmpty() || poi.getLongitude() < routePoiDto.getMinimumLongitude()) {
                        routePoiDto.setMinimumLongitude(poi.getLongitude());
                    }

                    allPOIs.add(poi);
                }
            } catch (IOException e) {
                // Couldn't open the required asset. Possibly the user's install is corrupted.
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    // Problem closing the file.
                }
            }

            // If route is not linear, we only display one section at a time,
            // so no need to increment the section.
            if (route.isLinear()) {
                // for circular routes, if we're at the final section, jump to the first section
                if (route.isCircular() && currentLocation == route.getSections().length - 1) {
                    currentLocation = 0;
                } else {
                    currentLocation++;
                }
            }
        }
        while (currentLocation != endLocation);

        routePoiDto.setPOIs(allPOIs);
        return routePoiDto;
    }

    private static List<POI> getPOIs(InputStream is) {
        POIHandler handler = new POIHandler();
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(is, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler.pointsOfInterest;
    }
}

class POIHandler extends DefaultHandler {

    private boolean name = false;
    private boolean description = false;
    private boolean coordinates = false;
    private boolean alternativeEndPoint = false;
    private POI currentPOI;

    List<POI> pointsOfInterest;

    POIHandler() {
        pointsOfInterest = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) {
        if (name.equalsIgnoreCase("placemark")) {
            currentPOI = new POI();
        } else if (name.equalsIgnoreCase("name")) {
            this.name = true;
        } else if (name.equalsIgnoreCase("description")) {
            description = true;
        } else if (name.equalsIgnoreCase("coordinates")) {
            coordinates = true;
        } else if (name.equalsIgnoreCase("alternativeEndPoint")) {
            alternativeEndPoint = true;
        }
    }

    public void characters(char[] ch, int start, int length) {
        if (name) {
            currentPOI.setTitle(new String(ch, start, length));
            name = false;
        }
        if (description) {
            currentPOI.setSnippet(new String(ch, start, length));
            description = false;
        }
        if (coordinates) {
            String lonLat = new String(ch, start, length);
            String lon = lonLat.substring(0, lonLat.indexOf(','));
            String lat = lonLat.substring(lonLat.indexOf(',') + 1, lonLat.lastIndexOf(','));
            currentPOI.setLatitude(Double.parseDouble(lat));
            currentPOI.setLongitude(Double.parseDouble(lon));
            coordinates = false;
        }
        if (alternativeEndPoint) {
            currentPOI.setIsAlternativeEndPoint(true);
            alternativeEndPoint = false;
        }
    }

    public void endElement(String uri, String localName, String name) {
        if (name.equalsIgnoreCase("placemark")) {
            pointsOfInterest.add(currentPOI);
        }
    }

}