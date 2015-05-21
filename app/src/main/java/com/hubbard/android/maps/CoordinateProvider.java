package com.hubbard.android.maps;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class CoordinateProvider {
	
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