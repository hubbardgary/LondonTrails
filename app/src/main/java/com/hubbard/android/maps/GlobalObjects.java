package com.hubbard.android.maps;

import java.util.HashMap;

import android.app.Application;
import android.text.Html;

public class GlobalObjects extends Application {

    private Route currentRoute;
    private HashMap<String, Integer> hshSectionMap;

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route r) {
        currentRoute = r;
    }

    public HashMap<String, Integer> getSectionMap() {
        return hshSectionMap;
    }

    public void setSectionMap(HashMap<String, Integer> h) {
        hshSectionMap = h;
    }

    public static CharSequence getButtonText(String title, String subtitle) {
        return Html.fromHtml("<b><big>" + title + "</big></b>" + "<br />" + "<small>" + subtitle + "</small>");
    }
    public static CharSequence getButtonText(String title) {
        return Html.fromHtml("<b><big>" + title + "</big></b>");
    }
}
