package com.hubbardgary.londontrails.dataprovider;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.Section;
import com.hubbardgary.londontrails.model.dto.RouteCoordinatesDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CoordinateProvider implements com.hubbardgary.londontrails.dataprovider.interfaces.ICoordinateProvider {

    private Route route;
    private AssetManager assetManager;

    @Override
    public void initialize(Route route, AssetManager assetManager) {
        this.route = route;
        this.assetManager = assetManager;
    }

    @Override
    public RouteCoordinatesDto getRouteCoordinates(int startLocation, int endLocation) {
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

        return parseCoordinates(getCoordinatesString(startLocation, endLocation));
    }

    private String getCoordinatesString(int startLocation, int endLocation) {
        int currentLocation = startLocation;
        StringBuilder coordinates = new StringBuilder();

        do {
            Section s = route.getSection(currentLocation);

            if (currentLocation == startLocation) {
                coordinates.append(appendCoordinates(s.getStartLinkResource(s.getRouteShortName())));
            }

            coordinates.append(appendCoordinates(s.getSectionResource(s.getRouteShortName())));

            // If route is not linear, we only display one section at a time,
            // so no need to increment the section.
            if (route.isLinear()) {
                currentLocation = nextSection(currentLocation);
            }

            if (currentLocation == endLocation) {
                coordinates.append(appendCoordinates(s.getEndLinkResource(s.getRouteShortName())));
            }
        }
        while(currentLocation != endLocation);

        return coordinates.toString();
    }

    private String appendCoordinates(String filename) {
        InputStream is = null;
        try {
            String[] assetList = assetManager.list("");

            // Not all routes have a start or end link.
            // Don't bother trying to open a file that doesn't exist
            if (assetList != null && Arrays.asList(assetList).contains(filename)) {
                is = assetManager.open(filename);
                return getRoute(is);
            }
        } catch (IOException e) {
            // Couldn't open the required asset. Possibly the user's install is corrupted.
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // Problem closing the file.
            }
        }
        return "";
    }

    private int nextSection(int currentLocation) {
        // If it's a circular route, allow wraparound from last section to first section
        if (route.isCircular() && currentLocation == route.getSections().length - 1) {
            return 0;
        } else {
            return currentLocation + 1;
        }
    }

    private RouteCoordinatesDto parseCoordinates(String rawCoordinates) {
        String[] coordinatesParsed;
        coordinatesParsed = rawCoordinates.split(",0.000000"); // SAXParser loses "\n" characters so split on altitude which isn't set

        RouteCoordinatesDto routeCoordinatesDto = new RouteCoordinatesDto();
        int lenNew = coordinatesParsed.length;
        double[][] coordinates = new double[lenNew][2];
        for (int i = 0; i < lenNew; i++) {
            String[] xyParsed = coordinatesParsed[i].split(",");
            for (int j = 0; j < 2 && j < xyParsed.length; j++) {
                try {
                    coordinates[i][j] = Double.parseDouble(xyParsed[j]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (i == 0 || coordinates[i][0] > routeCoordinatesDto.getMaximumLatitude()) {
                routeCoordinatesDto.setMaximumLatitude(coordinates[i][1]);
            }
            if (i == 0 || coordinates[i][1] > routeCoordinatesDto.getMaximumLongitude()) {
                routeCoordinatesDto.setMaximumLongitude(coordinates[i][0]);
            }
            if (i == 0 || coordinates[i][1] < routeCoordinatesDto.getMinimumLatitude()) {
                routeCoordinatesDto.setMinimumLatitude(coordinates[i][1]);
            }
            if (i == 0 || coordinates[i][1] < routeCoordinatesDto.getMinimumLongitude()) {
                routeCoordinatesDto.setMinimumLongitude(coordinates[i][0]);
            }
        }

        routeCoordinatesDto.setCoordinates(coordinates);
        return routeCoordinatesDto;
    }

    private static String getRoute(InputStream is) {
        CoordinateProviderHandler handler = new CoordinateProviderHandler();
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            SAXParser parser = saxParserFactory.newSAXParser();
            parser.parse(is, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler.coordinates;
    }
}

class CoordinateProviderHandler extends DefaultHandler {
    private StringBuffer elementContent;
    String coordinates = "";

    CoordinateProviderHandler() {
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) {
        elementContent = new StringBuffer();
    }

    public void characters(char[] ch, int start, int length) {
        String chars = new String(ch, start, length).trim();
        elementContent.append(chars);
    }

    public void endElement(String uri, String localName, String name) {
        if (elementContent.length() > 0) {
            if (localName.equalsIgnoreCase("coordinates")) {
                coordinates = elementContent.toString();
            }
        }
    }
}