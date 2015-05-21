package com.hubbard.android.maps;

public class Route {

    private Section[] sections;
    private String[] endPoints;  // Can't go in Section because if route is non-circular, there are more end points than sections.
    private boolean circular;
    private String name;

    public Route() {
    }

    public Section[] getSections() {
        return sections;
    }

    public Section getSection(int i) {
        return sections[i];
    }

    public void setSections(Section[] sections) {
        this.sections = sections;
    }

    public void setSection(int i, Section section) {
        this.sections[i] = section;
    }

    public String getEndPoint(int i) {
        return endPoints[i];
    }

    public void setEndPoints(String[] endPoints) {
        this.endPoints = endPoints;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
