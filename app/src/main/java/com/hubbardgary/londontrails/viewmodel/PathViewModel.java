package com.hubbardgary.londontrails.viewmodel;

public class PathViewModel {
    private String name;
    private String description;
    private int color;
    private int width;
    private double[][] coordinates = new double[][]{};

    public double[][] getCoordinates() {
        return coordinates;
    }

    public double getCoordinateLat(int i) {
        return coordinates[i][1];
    }

    public double getCoordinateLng(int i) {
        return coordinates[i][0];
    }

    public void setCoordinates(double[][] coordinates) {
        this.coordinates = coordinates;
    }

}
