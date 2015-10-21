package com.hubbardgary.londontrails.viewmodel;

public class PathViewModel {
    private String name;
    private String description;
    private int color;
    private int width;
    private double[][] wayPoints = new double[][]{};

    public double[][] getWayPoints() {
        return wayPoints;
    }

    public double getWayPointLat(int i) {
        return wayPoints[i][1];
    }

    public double getWayPointLng(int i) {
        return wayPoints[i][0];
    }

    public void setWayPoints(double[][] wayPoints) {
        this.wayPoints = wayPoints;
    }

}
