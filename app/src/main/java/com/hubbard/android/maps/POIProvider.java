package com.hubbard.android.maps;

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

import com.google.android.gms.maps.model.LatLng;

class POIProvider {

	public static List<POI> getPOIs(InputStream is) {
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
	private POI currentPOI;
	
	List<POI> pointsOfInterest;
	
	public POIHandler() {
		pointsOfInterest = new ArrayList<POI>();
	}
	
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if(name.equalsIgnoreCase("placemark")) {
			currentPOI = new POI();	
		}
		else if(name.equalsIgnoreCase("name")) {
			this.name = true;
		}
		else if(name.equalsIgnoreCase("description")) {
			description = true;
		}
		else if(name.equalsIgnoreCase("coordinates")) {
			coordinates = true;
		}
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if(name) {
			currentPOI.setTitle(new String(ch, start, length));
			name = false;
		}
		if(description) {
			currentPOI.setSnippet(new String(ch, start, length));
			description = false;
		}
		if(coordinates) {
			String sLonLat = new String(ch, start, length);
			String sLon = sLonLat.substring(0, sLonLat.indexOf(','));
			String sLat = sLonLat.substring(sLonLat.indexOf(',') + 1, sLonLat.lastIndexOf(','));
			currentPOI.setCoords(new LatLng(Double.parseDouble(sLat), Double.parseDouble(sLon)));
			coordinates = false;
		}
	}
	
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if(name.equalsIgnoreCase("placemark")) {
			pointsOfInterest.add(currentPOI);
		}
	}
	
}