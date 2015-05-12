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

public class POIProvider {

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
	
	boolean bName = false;
	boolean bDescription = false;
	boolean bCoordinates = false;	
	POI currentPOI;
	
	List<POI> pointsOfInterest;
	
	public POIHandler() {
		pointsOfInterest = new ArrayList<POI>();
	}
	
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if(name.equalsIgnoreCase("Placemark")) {
			currentPOI = new POI();	
		}
		else if(name.equalsIgnoreCase("name")) {
			bName = true;
		}
		else if(name.equalsIgnoreCase("description")) {
			bDescription = true;
		}
		else if(name.equalsIgnoreCase("coordinates")) {
			bCoordinates = true;
		}
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if(bName) {
			currentPOI.title = new String(ch, start, length);
			bName = false;
		}
		if(bDescription) {
			currentPOI.snippet = new String(ch, start, length);
			bDescription = false;
		}
		if(bCoordinates) {
			String sLonLat = new String(ch, start, length);
			String sLon = sLonLat.substring(0, sLonLat.indexOf(','));
			String sLat = sLonLat.substring(sLonLat.indexOf(',') + 1, sLonLat.lastIndexOf(','));
			currentPOI.coords = new LatLng(Double.parseDouble(sLat), Double.parseDouble(sLon));
			bCoordinates = false;
		}
	}
	
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if(name.equalsIgnoreCase("Placemark")) {
			pointsOfInterest.add(currentPOI);
		}
	}
	
}