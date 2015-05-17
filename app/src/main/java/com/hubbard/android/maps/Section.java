package com.hubbard.android.maps;

import java.util.ArrayList;

public class Section {
	public ArrayList<WayPoint> sectionWayPoints;
	public ArrayList<WayPoint> startLinkWayPoints;
	public ArrayList<WayPoint> endLinkWayPoints;
	
	public String sectionResource;
	public String startLinkResource;
	public String endLinkResource;
	public String poiResource;
	public double distanceInKm;
    public double startLinkDistanceInKm;
    public double endLinkDistanceInKm;
	
	public Section() {
	}
}
