package com.hubbardgary.londontrails.model;

public class POI {
    private double latitude;
    private double longitude;
    private String title;
    private String snippet;
    private boolean isAlternativeEndPoint = false;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public boolean getIsAlternativeEndPoint() {
        return isAlternativeEndPoint;
    }

    public void setIsAlternativeEndPoint(boolean isAlternativeEndPoint) {
        this.isAlternativeEndPoint = isAlternativeEndPoint;
    }
}
