/*package com.hubbard.android.maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class RoadProvider {

	public static Path getRoute(InputStream is) {
		KMLHandler handler = new KMLHandler();
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
		return handler.mPath;
	}

	public static String getUrl(double fromLat, double fromLon, double toLat,
			double toLon) {// connect to map web service
		StringBuffer urlString = new StringBuffer();
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");// from
		urlString.append(Double.toString(fromLat));
		urlString.append(",");
		urlString.append(Double.toString(fromLon));
		urlString.append("&daddr=");// to
		urlString.append(Double.toString(toLat));
		urlString.append(",");
		urlString.append(Double.toString(toLon));
		urlString.append("&ie=UTF8&0&om=0&output=kml");
		return urlString.toString();
	}
}
*/
/*class KMLHandler extends DefaultHandler {
	Path mPath;
	boolean isPlacemark;
	boolean isRoute;
	boolean isItemIcon;
	private Stack<String> mCurrentElement = new Stack<String>();
	private String mString;

	public KMLHandler() {
		mPath = new Path();
	}

	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		mCurrentElement.push(localName);
		mString = new String();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String chars = new String(ch, start, length).trim();
		mString = mString.concat(chars);
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (mString.length() > 0) {
			if(localName.equalsIgnoreCase("name")) {
				mPath.mName = mString;
			}
			if (localName.equalsIgnoreCase("coordinates")) {
				String[] coodrinatesParsed = split(mString, ",0.000000"); // SAXParser loses "\n" characters so split on altitude which isn't set
				int lenNew = coodrinatesParsed.length;
				int lenOld = mPath.mWayPoints.length;
				double[][] temp = new double[lenOld + lenNew][2];
				for (int i = 0; i < lenOld; i++) {
					temp[i] = mPath.mWayPoints[i];
				}
				for (int i = 0; i < lenNew; i++) {
					String[] xyParsed = split(coodrinatesParsed[i], ",");
					for (int j = 0; j < 2 && j < xyParsed.length; j++)
						temp[lenOld + i][j] = Double
								.parseDouble(xyParsed[j]);
				}
				mPath.mWayPoints = temp;
			}
		}
		mCurrentElement.pop();
	}

	private static String[] split(String strString, String strDelimiter) {
		String[] strArray;
		int iOccurrences = 0;
		int iIndexOfInnerString = 0;
		int iIndexOfDelimiter = 0;
		int iCounter = 0;
		if (strString == null) {
			throw new IllegalArgumentException("Input string cannot be null.");
		}
		if (strDelimiter.length() <= 0 || strDelimiter == null) {
			throw new IllegalArgumentException(
					"Delimeter cannot be null or empty.");
		}
		if (strString.startsWith(strDelimiter)) {
			strString = strString.substring(strDelimiter.length());
		}
		if (!strString.endsWith(strDelimiter)) {
			strString += strDelimiter;
		}
		while ((iIndexOfDelimiter = strString.indexOf(strDelimiter,
				iIndexOfInnerString)) != -1) {
			iOccurrences += 1;
			iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();
		}
		strArray = new String[iOccurrences];
		iIndexOfInnerString = 0;
		iIndexOfDelimiter = 0;
		while ((iIndexOfDelimiter = strString.indexOf(strDelimiter,
				iIndexOfInnerString)) != -1) {
			strArray[iCounter] = strString.substring(iIndexOfInnerString,
					iIndexOfDelimiter);
			iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();
			iCounter += 1;
		}

		return strArray;
	}
}*/