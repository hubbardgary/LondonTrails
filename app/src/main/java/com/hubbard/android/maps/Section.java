package com.hubbard.android.maps;

public class Section {

	private String sectionResource;
	private String startLinkResource;
	private String endLinkResource;
	private String poiResource;
	private double distanceInKm;
	private double startLinkDistanceInKm;
	private double endLinkDistanceInKm;

	public Section() {
	}

	public String getSectionResource() {
		return sectionResource;
	}

	public void setSectionResource(String sectionResource) {
		this.sectionResource = sectionResource;
	}

	public String getStartLinkResource() {
		return startLinkResource;
	}

	public void setStartLinkResource(String startLinkResource) {
		this.startLinkResource = startLinkResource;
	}

	public String getEndLinkResource() {
		return endLinkResource;
	}

	public void setEndLinkResource(String endLinkResource) {
		this.endLinkResource = endLinkResource;
	}

	public String getPoiResource() {
		return poiResource;
	}

	public void setPoiResource(String poiResource) {
		this.poiResource = poiResource;
	}

	public double getDistanceInKm() {
		return distanceInKm;
	}

	public void setDistanceInKm(double distanceInKm) {
		this.distanceInKm = distanceInKm;
	}

	public double getStartLinkDistanceInKm() {
		return startLinkDistanceInKm;
	}

	public void setStartLinkDistanceInKm(double startLinkDistanceInKm) {
		this.startLinkDistanceInKm = startLinkDistanceInKm;
	}

	public double getEndLinkDistanceInKm() {
		return endLinkDistanceInKm;
	}

	public void setEndLinkDistanceInKm(double endLinkDistanceInKm) {
		this.endLinkDistanceInKm = endLinkDistanceInKm;
	}
}
