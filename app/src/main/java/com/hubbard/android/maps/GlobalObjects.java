package com.hubbard.android.maps;

import java.util.HashMap;

import android.app.Application;

public class GlobalObjects extends Application {

	private Route currentRoute;
	private HashMap<String, Integer> hshSectionMap;
	
	@Override
	public void onCreate() {
		
		super.onCreate();
	}
	
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
}
