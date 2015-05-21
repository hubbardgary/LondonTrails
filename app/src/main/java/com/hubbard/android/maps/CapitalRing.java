package com.hubbard.android.maps;

class CapitalRing extends Route {

	public CapitalRing() {
		setName("Capital Ring");
		setCircular(true);
		setSections(new Section[15]);
		setEndPoints(populateEndPoints());

		for(int i = 0; i < getSections().length; i++) {
			setSection(i, populateSections(i));
		}
	}

	private String[] populateEndPoints() {
		return new String[]{
			"Woolwich Arsenal Rail Station",
			"Falconwood Rail Station",
			"Grove Park Rail Station",
			"Crystal Palace Rail Station",
			"Streatham Common Rail Station",
			"Wimbledon Park Underground Station",
			"Richmond Rail Station",
			"Boston Manor Underground Station",
			"Greenford Station (Rail and Underground)",
			"South Kenton Station (Overground and Underground)",
			"Hendon Central Underground Station",
			"Highgate Underground Station",
			"Stoke Newington Rail Station",
			"Hackney Wick Rail Station",
			"Royal Albert DLR Station"
		};
	}

	private Section populateSections(int section) {
		Section s = new Section();

		switch (section) {
			case 0:
				s.setStartLinkResource("cr-01-start_link.kml");
				s.setSectionResource("cr-01-woolwich-falconwood.kml");
				s.setEndLinkResource("cr-01-end_link.kml");
				s.setPoiResource("cr-01-placemarks.kml");
				s.setDistanceInKm(9.02);
				s.setStartLinkDistanceInKm(1.21);
				s.setEndLinkDistanceInKm(0.5);
				break;
			case 1:
				s.setStartLinkResource("cr-02-start_link.kml");
				s.setSectionResource("cr-02-falconwood-grove_park.kml");
				s.setEndLinkResource("cr-02-end_link.kml");
				s.setPoiResource("cr-02-placemarks.kml");
				s.setDistanceInKm(6.27);
				s.setStartLinkDistanceInKm(0.5);
				s.setEndLinkDistanceInKm(0.01);
				break;
			case 2:
				s.setStartLinkResource("cr-03-start_link.kml");
				s.setSectionResource("cr-03-grove_park-crystal_palace.kml");
				s.setEndLinkResource("");
				s.setPoiResource("cr-03-placemarks.kml");
				s.setDistanceInKm(12.4);
				s.setStartLinkDistanceInKm(0.01);
				s.setEndLinkDistanceInKm(0);
				break;
			case 3:
				s.setStartLinkResource("");
				s.setSectionResource("cr-04-crystal_palace-streatham.kml");
				s.setEndLinkResource("cr-04-end_link.kml");
				s.setPoiResource("cr-04-placemarks.kml");
				s.setDistanceInKm(6.57);
				s.setStartLinkDistanceInKm(0);
				s.setEndLinkDistanceInKm(0.2);
				break;
			case 4:
				s.setStartLinkResource("cr-05-start_link.kml");
				s.setSectionResource("cr-05-streatham-wimbledon_park.kml");
				s.setEndLinkResource("");
				s.setPoiResource("cr-05-placemarks.kml");
				s.setDistanceInKm(8.84);
				s.setStartLinkDistanceInKm(0.2);
				s.setEndLinkDistanceInKm(0);
				break;
			case 5:
				s.setStartLinkResource("");
				s.setSectionResource("cr-06-wimbledon_park-richmond.kml");
				s.setEndLinkResource("cr-06-end_link.kml");
				s.setPoiResource("cr-06-placemarks.kml");
				s.setDistanceInKm(11);
				s.setStartLinkDistanceInKm(0);
				s.setEndLinkDistanceInKm(0.8);
				break;
			case 6:
				s.setStartLinkResource("cr-07-start_link.kml");
				s.setSectionResource("cr-07-richmond-osterley_lock.kml");
				s.setEndLinkResource("cr-07-end_link.kml");
				s.setPoiResource("cr-07-placemarks.kml");
				s.setDistanceInKm(6.23);
				s.setStartLinkDistanceInKm(0.8);
				s.setEndLinkDistanceInKm(0.83);
				break;
			case 7:
				s.setStartLinkResource("cr-08-start_link.kml");
				s.setSectionResource("cr-08-osterley_lock-greenford.kml");
				s.setEndLinkResource("cr-08-end_link.kml");
				s.setPoiResource("cr-08-placemarks.kml");
				s.setDistanceInKm(7.82);
				s.setStartLinkDistanceInKm(0.83);
				s.setEndLinkDistanceInKm(0.29);
				break;
			case 8:
				s.setStartLinkResource("cr-09-start_link.kml");
				s.setSectionResource("cr-09-greenford-south_kenton.kml");
				s.setEndLinkResource("");
				s.setPoiResource("cr-09-placemarks.kml");
				s.setDistanceInKm(8.74);
				s.setStartLinkDistanceInKm(0.29);
				s.setEndLinkDistanceInKm(0);
				break;
			case 9:
				s.setStartLinkResource("");
				s.setSectionResource("cr-10-south_kenton-hendon_park.kml");
				s.setEndLinkResource("cr-10-end_link.kml");
				s.setPoiResource("cr-10-placemarks.kml");
				s.setDistanceInKm(9.78);
				s.setStartLinkDistanceInKm(0);
				s.setEndLinkDistanceInKm(0.74);
				break;
			case 10:
				s.setStartLinkResource("cr-11-start_link.kml");
				s.setSectionResource("cr-11-hendon_park-highgate.kml");
				s.setEndLinkResource("cr-11-end_link.kml");
				s.setPoiResource("cr-11-placemarks.kml");
				s.setDistanceInKm(8.21);
				s.setStartLinkDistanceInKm(0.74);
				s.setEndLinkDistanceInKm(0.13);
				break;
			case 11:
				s.setStartLinkResource("cr-12-start_link.kml");
				s.setSectionResource("cr-12-highgate-stoke_newington.kml");
				s.setEndLinkResource("cr-12-end_link.kml");
				s.setPoiResource("cr-12-placemarks.kml");
				s.setDistanceInKm(8.38);
				s.setStartLinkDistanceInKm(0.13);
				s.setEndLinkDistanceInKm(0.14);
				break;
			case 12:
				s.setStartLinkResource("cr-13-start_link.kml");
				s.setSectionResource("cr-13-stoke_newington-hackney_wick.kml");
				s.setEndLinkResource("cr-13-end_link.kml");
				s.setPoiResource("cr-13-placemarks.kml");
				s.setDistanceInKm(6.03);
				s.setStartLinkDistanceInKm(0.14);
				s.setEndLinkDistanceInKm(0.43);
				break;
			case 13:
				s.setStartLinkResource("cr-14-start_link.kml");
				s.setSectionResource("cr-14-hackney_wick-beckton_district_park.kml");
				s.setEndLinkResource("cr-14-end_link.kml");
				s.setPoiResource("cr-14-placemarks.kml");
				s.setDistanceInKm(7.47);
				s.setStartLinkDistanceInKm(0.43);
				s.setEndLinkDistanceInKm(0.45);
				break;
			case 14:
				s.setStartLinkResource("cr-15-start_link.kml");
				s.setSectionResource("cr-15-beckton_district_park-woolwich.kml");
				s.setEndLinkResource("cr-15-end_link.kml");
				s.setPoiResource("cr-15-placemarks.kml");
				s.setDistanceInKm(4.49);
				s.setStartLinkDistanceInKm(0.45);
				s.setEndLinkDistanceInKm(1.21);
				break;
		}
		return s;
	}

}
