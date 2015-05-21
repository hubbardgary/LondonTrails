package com.hubbard.android.maps;

import com.google.android.gms.maps.model.LatLng;

class POI {
    private LatLng coords;
    private String title;
    public String snippet;

    public LatLng getCoords() {
        return coords;
    }

    public void setCoords(LatLng coords) {
        this.coords = coords;
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
}
