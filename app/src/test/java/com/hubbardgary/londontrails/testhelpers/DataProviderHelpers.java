package com.hubbardgary.londontrails.testhelpers;

import com.hubbardgary.londontrails.model.Coordinates;
import com.hubbardgary.londontrails.model.POI;

import java.util.List;

public class DataProviderHelpers {
    private static String buildPlacemarkXml(POI[] placemarks) {
        String xml = "";
        for (POI p : placemarks) {
            xml = xml +
                    "  <Placemark>\n" +
                    "    <name>" + p.getTitle() + "</name>\n" +
                    "    <description><![CDATA[" + p.getSnippet() + "]]></description>\n" +
                    "    <styleUrl>#style4</styleUrl>\n" +
                    "    <Point>\n" +
                    "      <coordinates>" + p.getLatitude() + "," + p.getLongitude() + ",0.000000</coordinates>\n" +
                    "    </Point>\n";
            if (p.getIsAlternativeEndPoint()) {
                xml = xml +
                    "<alternativeEndPoint>true</alternativeEndPoint>\n";
            }

            xml = xml + "  </Placemark>\n";
        }
        return xml;
    }

    public static String buildPoiSectionXml(POI[] placemarks) {
        String placemarkXml = buildPlacemarkXml(placemarks);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "<Document>\n" +
                placemarkXml +
                "</Document>\n" +
                "</kml>\n";
    }

    public static String buildSectionCoordinatesXml(List<Coordinates> coordinates) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "<Document>\n" +
                "  <name>Test Route</name>\n" +
                "  <description><![CDATA[]]></description>\n" +
                "  <Style id=\"style1\">\n" +
                "    <LineStyle>\n" +
                "      <color>99FF0000</color>\n" +
                "      <width>5</width>\n" +
                "    </LineStyle>\n" +
                "  </Style>\n" +
                "  <Placemark>\n" +
                "    <name>Test section</name>\n" +
                "    <description><![CDATA[]]></description>\n" +
                "    <styleUrl>#style1</styleUrl>\n" +
                "    <LineString>\n" +
                "      <tessellate>1</tessellate>\n" +
                "      <coordinates>\n" +
                getCoordinates(coordinates) +
                "      </coordinates>\n" +
                "    </LineString>\n" +
                "  </Placemark>\n" +
                "</Document>\n" +
                "</kml>";
        return xml;
    }

    private static String getCoordinates(List<Coordinates> coordinates) {
        String s = "";
        for (Coordinates c : coordinates) {
            s = s + "        ";
            s = s + c.getLongitude() + ",";
            s = s + c.getLatitude() + ",";
            s = s + "0.000000\n";
        }
        return s;
    }
}
