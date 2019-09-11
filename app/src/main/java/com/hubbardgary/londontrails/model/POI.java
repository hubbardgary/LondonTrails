package com.hubbardgary.londontrails.model;

public class POI {
    private double latitude;
    private double longitude;
    private String title;
    private String snippet;
    private boolean isAlternativeEndPoint = false;

    public POI() {}

    public POI(String title, String snippet, double latitude, double longitude, boolean isAlternativeEndPoint) {
        this.title = title;
        this.snippet = snippet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAlternativeEndPoint = isAlternativeEndPoint;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof POI)) {
            return false;
        }

        POI poi = (POI)obj;
        return this.title.equals(poi.title) &&
                this.snippet.equals(poi.snippet) &&
                this.latitude == poi.latitude &&
                this.longitude == poi.longitude &&
                this.isAlternativeEndPoint == poi.isAlternativeEndPoint;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (title == null ? 0 : title.hashCode());
        result = 37 * result + (snippet == null ? 0 : snippet.hashCode());
        long temp = Double.doubleToLongBits(latitude);
        result = 37 * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 37 * result + (int)(temp ^ (temp >>> 32));
        result = 37 * result + (isAlternativeEndPoint ? 0 : 1);
        return result;
    }

}
