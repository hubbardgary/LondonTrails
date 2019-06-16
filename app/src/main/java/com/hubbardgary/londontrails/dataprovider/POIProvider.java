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
import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.Section;

public class POIProvider {

    private Route route;
    private AssetManager assetManager;
    private int startLocation;
    private int endLocation;

    public POIProvider(Route route, AssetManager assetManager, int startLocation, int endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.route = route;
        this.assetManager = assetManager;
    }

    public List<POI> getPOIsForRoute() {
        int currentLocation = startLocation;
        List<POI> poi = new ArrayList<>();
        do {
            InputStream is = null;
            try {
                Section s = route.getSection(currentLocation);
                is = assetManager.open(s.getPoiResource(route.getShortName()));
                poi.addAll(getPOIs(is));
            } catch (IOException e) {
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
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

        return poi;
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