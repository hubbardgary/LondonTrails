package com.hubbard.android.maps;

import java.util.ArrayList;

public class CapitalRing extends Route {

	public CapitalRing() {
		name = "Capital Ring";
		circular = true;
		sections = new Section[15];
		for(int i = 0; i < sections.length; i++) {
			sections[i] = populateSection(i);
		}
	}

	private Section populateSection(int section) {
		Section s = new Section();

		// Initalise WayPoint arrays but don't populate until we know we'll use them
		s.startLinkWayPoints = new ArrayList<WayPoint>();
		s.sectionWayPoints = new ArrayList<WayPoint>();
		s.endLinkWayPoints = new ArrayList<WayPoint>();

		switch (section) {
			case 0:
				s.startLinkResource = "cr-01-start_link.kml";
				s.sectionResource = "cr-01-woolwich-falconwood.kml";
				s.endLinkResource = "cr-01-end_link.kml";
				s.poiResource = "cr-01-placemarks.kml";
				s.distanceInKm = 9.02;
				s.startLinkDistanceInKm = 1.21;
				s.endLinkDistanceInKm = 0.5;
				break;
			case 1:
				s.startLinkResource = "cr-02-start_link.kml";
				s.sectionResource = "cr-02-falconwood-grove_park.kml";
				s.endLinkResource = "cr-02-end_link.kml";
				s.poiResource = "cr-02-placemarks.kml";
				s.distanceInKm = 6.27;
				s.startLinkDistanceInKm = 0.5;
				s.endLinkDistanceInKm = 0.01;
				break;
			case 2:
				s.startLinkResource = "cr-03-start_link.kml";
				s.sectionResource = "cr-03-grove_park-crystal_palace.kml";
				s.endLinkResource = "";
				s.poiResource = "cr-03-placemarks.kml";
				s.distanceInKm = 12.4;
				s.startLinkDistanceInKm = 0.01;
				s.endLinkDistanceInKm = 0;
				break;
			case 3:
				s.startLinkResource = "";
				s.sectionResource = "cr-04-crystal_palace-streatham.kml";
				s.endLinkResource = "cr-04-end_link.kml";
				s.poiResource = "cr-04-placemarks.kml";
				s.distanceInKm = 6.57;
				s.startLinkDistanceInKm = 0;
				s.endLinkDistanceInKm = 0.2;
				break;
			case 4:
				s.startLinkResource = "cr-05-start_link.kml";
				s.sectionResource = "cr-05-streatham-wimbledon_park.kml";
				s.endLinkResource = "";
				s.poiResource = "cr-05-placemarks.kml";
				s.distanceInKm = 8.84;
				s.startLinkDistanceInKm = 0.2;
				s.endLinkDistanceInKm = 0;
				break;
			case 5:
				s.startLinkResource = "";
				s.sectionResource = "cr-06-wimbledon_park-richmond.kml";
				s.endLinkResource = "cr-06-end_link.kml";
				s.poiResource = "cr-06-placemarks.kml";
				s.distanceInKm = 11;
				s.startLinkDistanceInKm = 0;
				s.endLinkDistanceInKm = 0.8;
				break;
			case 6:
				s.startLinkResource = "cr-07-start_link.kml";
				s.sectionResource = "cr-07-richmond-osterley_lock.kml";
				s.endLinkResource = "cr-07-end_link.kml";
				s.poiResource = "cr-07-placemarks.kml";
				s.distanceInKm = 6.23;
				s.startLinkDistanceInKm = 0.8;
				s.endLinkDistanceInKm = 0.83;
				break;
			case 7:
				s.startLinkResource = "cr-08-start_link.kml";
				s.sectionResource = "cr-08-osterley_lock-greenford.kml";
				s.endLinkResource = "cr-08-end_link.kml";
				s.poiResource = "cr-08-placemarks.kml";
				s.distanceInKm = 7.82;
				s.startLinkDistanceInKm = 0.83;
				s.endLinkDistanceInKm = 0.29;
				break;
			case 8:
				s.startLinkResource = "cr-09-start_link.kml";
				s.sectionResource = "cr-09-greenford-south_kenton.kml";
				s.endLinkResource = "";
				s.poiResource = "cr-09-placemarks.kml";
				s.distanceInKm = 8.74;
				s.startLinkDistanceInKm = 0.29;
				s.endLinkDistanceInKm = 0;
				break;
			case 9:
				s.startLinkResource = "";
				s.sectionResource = "cr-10-south_kenton-hendon_park.kml";
				s.endLinkResource = "cr-10-end_link.kml";
				s.poiResource = "cr-10-placemarks.kml";
				s.distanceInKm = 9.78;
				s.startLinkDistanceInKm = 0;
				s.endLinkDistanceInKm = 0.74;
				break;
			case 10:
				s.startLinkResource = "cr-11-start_link.kml";
				s.sectionResource = "cr-11-hendon_park-highgate.kml";
				s.endLinkResource = "cr-11-end_link.kml";
				s.poiResource = "cr-11-placemarks.kml";
				s.distanceInKm = 8.21;
				s.startLinkDistanceInKm = 0.74;
				s.endLinkDistanceInKm = 0.13;
				break;
			case 11:
				s.startLinkResource = "cr-12-start_link.kml";
				s.sectionResource = "cr-12-highgate-stoke_newington.kml";
				s.endLinkResource = "cr-12-end_link.kml";
				s.poiResource = "cr-12-placemarks.kml";
				s.distanceInKm = 8.38;
				s.startLinkDistanceInKm = 0.13;
				s.endLinkDistanceInKm = 0.14;
				break;
			case 12:
				s.startLinkResource = "cr-13-start_link.kml";
				s.sectionResource = "cr-13-stoke_newington-hackney_wick.kml";
				s.endLinkResource = "cr-13-end_link.kml";
				s.poiResource = "cr-13-placemarks.kml";
				s.distanceInKm = 6.03;
				s.startLinkDistanceInKm = 0.14;
				s.endLinkDistanceInKm = 0.43;
				break;
			case 13:
				s.startLinkResource = "cr-14-start_link.kml";
				s.sectionResource = "cr-14-hackney_wick-beckton_district_park.kml";
				s.endLinkResource = "cr-14-end_link.kml";
				s.poiResource = "cr-14-placemarks.kml";
				s.distanceInKm = 7.47;
				s.startLinkDistanceInKm = 0.43;
				s.endLinkDistanceInKm = 0.45;
				break;
			case 14:
				s.startLinkResource = "cr-15-start_link.kml";
				s.sectionResource = "cr-15-beckton_district_park-woolwich.kml";
				s.endLinkResource = "cr-15-end_link.kml";
				s.poiResource = "cr-15-placemarks.kml";
				s.distanceInKm = 4.49;
				s.startLinkDistanceInKm = 0.45;
				s.endLinkDistanceInKm = 1.21;
				break;
		}
		return s;
	}

}
