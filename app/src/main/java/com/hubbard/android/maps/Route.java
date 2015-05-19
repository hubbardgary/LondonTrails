package com.hubbard.android.maps;

public class Route {
    public Section[] sections;
    public String[] endPoints;  // Can't go in Section because if route is non-circular, there are more end points than sections.
    public boolean circular;
    public String name;

    public Route() {
    }
}
