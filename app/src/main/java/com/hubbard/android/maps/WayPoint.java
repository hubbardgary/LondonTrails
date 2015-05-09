package com.hubbard.android.maps;

public class WayPoint {
	public double lat;
	public double lon;
	
	public WayPoint() {
		lat = 0.0;
		lon = 0.0;
	}
	
	public WayPoint(double latitude, double longitude) {
		lat = latitude;
		lon = longitude;
	}
}
