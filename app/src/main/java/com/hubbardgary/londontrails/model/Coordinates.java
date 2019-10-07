package com.hubbardgary.londontrails.model;

public class Coordinates {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates)) {
            return false;
        }

        Coordinates c = (Coordinates)obj;
        return this.latitude == c.latitude &&
                this.longitude == c.longitude;
    }

    @Override
    public int hashCode() {
        int result = 17;
        long temp = Double.doubleToLongBits(latitude);
        result = 37 * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 37 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }
}
