package com.hubbardgary.londontrails.dataprovider;

import android.content.res.AssetManager;

import com.hubbardgary.londontrails.model.Route;
import com.hubbardgary.londontrails.model.Section;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CoordinateProvider {

    private Route route;
    private AssetManager assetManager;
    private int startLocation;
    private int endLocation;

    public CoordinateProvider(Route route, AssetManager assetManager, int startLocation, int endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.route = route;
        this.assetManager = assetManager;
    }

    public double[][] GetPathWayPoints() {
        return ParseCoordinates(GetCoordinatesString());
    }

    private String GetCoordinatesString() {
        int currentLocation = startLocation;
        StringBuilder coordinates = new StringBuilder();

        do {
            Section s = route.getSection(currentLocation);

            if (currentLocation == startLocation) {
                coordinates.append(AppendCoordinates(s.getStartLinkResource(s.getRouteShortName())));
            }

            coordinates.append(AppendCoordinates(s.getSectionResource(s.getRouteShortName())));

            // If route is not linear, we only display one section at a time,
            // so no need to increment the section.
            if (route.isLinear()) {
                currentLocation = NextSection(currentLocation);
            }

            if (currentLocation == endLocation) {
                coordinates.append(AppendCoordinates(s.getEndLinkResource(s.getRouteShortName())));
            }
        }
        while(currentLocation != endLocation);

        return coordinates.toString();
    }

    private String AppendCoordinates(String filename) {
        InputStream is = null;
        try {
            is = assetManager.open(filename);
            return getRoute(is);
        } catch (IOException e) {
            // Not all routes have a start or end link. Swallow any exception here.
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return "";
    }

    private int NextSection(int currentLocation) {
        // If it's a circular route, allow wraparound from last section to first section
        if (route.isCircular() && currentLocation == route.getSections().length - 1) {
            return 0;
        } else {
            return currentLocation + 1;
        }
    }
    private double[][] ParseCoordinates(String coordinates) {
        String[] coordinatesParsed;
        coordinatesParsed = coordinates.split(",0.000000"); // SAXParser loses "\n" characters so split on altitude which isn't set

        int lenNew = coordinatesParsed.length;
        double[][] wayPoints = new double[lenNew][2];
        for (int i = 0; i < lenNew; i++) {
            String[] xyParsed = coordinatesParsed[i].split(",");
            for (int j = 0; j < 2 && j < xyParsed.length; j++) {
                try {
                    wayPoints[i][j] = Double.parseDouble(xyParsed[j]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return wayPoints;
    }

    public static String getRoute(InputStream is) {
        CoordinateProviderHandler handler = new CoordinateProviderHandler();
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
        return handler.coordinates;
    }
}

class CoordinateProviderHandler extends DefaultHandler {
    private StringBuffer elementContent;
    String coordinates = "";

    public CoordinateProviderHandler() {
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        elementContent = new StringBuffer();
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String chars = new String(ch, start, length).trim();
        elementContent.append(chars);
    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {
        if (elementContent.length() > 0) {
            if (localName.equalsIgnoreCase("coordinates")) {
                coordinates = elementContent.toString();
            }
        }
    }
}