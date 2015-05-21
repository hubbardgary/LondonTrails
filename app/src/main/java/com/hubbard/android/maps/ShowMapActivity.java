package com.hubbard.android.maps;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class ShowMapActivity extends Activity implements LocationListener, 
														LocationSource, 
														GooglePlayServicesClient.ConnectionCallbacks,
														GooglePlayServicesClient.OnConnectionFailedListener {
	private GoogleMap map;
	private LocationManager locationManager;
	private OnLocationChangedListener mListener = null;
	private Criteria crit = new Criteria();

	private static final int MILLISECONDS_PER_SECOND = 1000;
	private static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	private LocationRequest mLocationRequest;
	private LocationClient mLocationClient;
	private boolean mUpdatesRequested;
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mEditor;
	private LatLngBounds.Builder defaultBounds;
	private List<Marker> markers;
	private boolean bMarkersVisible;
	private int start;
	private int end;
	private Direction direction;
	
	private double maxLat;
	private double minLat;
	private double maxLon;
	private double minLon;	// Used to define range of path on map
	
	private GlobalObjects glob;
    private Route currRoute;

	private enum Direction {
		CLOCKWISE,
		ANTI_CLOCKWISE
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		glob = (GlobalObjects)getApplicationContext();
        currRoute = glob.getCurrentRoute();
        setTitle(currRoute.getName());

		start = getIntent().getExtras().getInt("latLngStart");
		end = getIntent().getExtras().getInt("latLngEnd");
		direction = getIntent().getExtras().getInt("direction") == 0 ? Direction.CLOCKWISE : Direction.ANTI_CLOCKWISE;

		SetPOIWindowAdapter();
		
		new BackgroundStuff().execute();

		// Attempts to move camera before map has loaded will fail.
		// http://stackoverflow.com/questions/13692579/movecamera-with-cameraupdatefactory-newlatlngbounds-crashes
		map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			@Override
			public void onMapLoaded() {
				CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), 100);
				map.animateCamera(cu, 800, null);
			}
		});

		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		crit.setAccuracy(Criteria.ACCURACY_COARSE); // How do you just make it use the best available?
		map.setMyLocationEnabled(true);

		boolean mUpdatesRequested;
		// Create the LocationRequest object
		mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(
				LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		// Set the fastest update interval to 1 second
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		// Open the shared preferences
		mPrefs = getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		// Get a SharedPreferences editor
		mEditor = mPrefs.edit();
		/*
		 * Create a new location client, using the enclosing class to
		 * handle callbacks.
		 */
		mLocationClient = new LocationClient(this, this, this);
		// Start with updates turned off
		mUpdatesRequested = true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.view_option_streetmap:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			return true;
		case R.id.view_option_satellite:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			return true;
		case R.id.view_option_terrain:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			return true;
		case R.id.view_option_hide_markers:
			for(Marker m : markers) {
				m.setVisible(false);
			}
			bMarkersVisible = false;
			return true;
		case R.id.view_option_show_markers:
			for(Marker m : markers) {
				m.setVisible(true);
			}
			bMarkersVisible = true;
			return true;
		case R.id.view_option_reset_focus:
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(defaultBounds.build(), 100);
			map.animateCamera(cu);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		SubMenu submenu = menu.addSubMenu(Menu.NONE, Menu.NONE, 1, "Change Map View");
		submenu.add(Menu.NONE, R.id.view_option_streetmap, 1, R.string.streetmap_view);
		submenu.add(Menu.NONE, R.id.view_option_satellite, 2, R.string.satellite_view);
		submenu.add(Menu.NONE, R.id.view_option_terrain, 3, R.string.terrain_view);

		if(bMarkersVisible) {
			menu.add(Menu.NONE, R.id.view_option_hide_markers, 4, R.string.hide_markers);
		} else {
			menu.add(Menu.NONE, R.id.view_option_show_markers, 4, R.string.show_markers);
		}
        menu.add(Menu.NONE, R.id.view_option_reset_focus, 5, R.string.reset_focus);

		switch(map.getMapType()) {
		case GoogleMap.MAP_TYPE_NORMAL:
			menu.removeItem(R.id.view_option_streetmap);
			break;
		case GoogleMap.MAP_TYPE_HYBRID:
			menu.removeItem(R.id.view_option_satellite);
			break;
		case GoogleMap.MAP_TYPE_TERRAIN:
			menu.removeItem(R.id.view_option_terrain);
			break;
		}
		return true;
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
	}

	@Override
	public void deactivate() {
		mListener = null;
	}

	@Override
	public void onLocationChanged(Location location) 
	{
		if( mListener != null )
		{
			mListener.onLocationChanged( location );
			LatLngBounds bounds = this.map.getProjection().getVisibleRegion().latLngBounds;
			if(!bounds.contains(new LatLng(location.getLatitude(), location.getLongitude())))
			{
				//Move the camera to the user's location once it's available!
				map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
			}
		}
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/*
	 * Called by Location Services when the request to connect the
	 * client finishes successfully. At this point, you can
	 * request the current location or latLngStart periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		//Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	}
	/*
	 * Called by Location Services if the connection to the
	 * location client drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		//Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}
	/*
	 * Called by Location Services if the attempt to
	 * Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * latLngStart a Google Play services activity that can resolve
		 * error.
		 */
		// IGNORING THIS FOR NOW
	}

	@Override
	protected void onPause() {
		// Save the current setting for updates
		mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
		mEditor.commit();
		super.onPause();
	}
	@Override
	protected void onStart() {
		mLocationClient.connect();
		super.onStart();
	}
	@Override
	protected void onResume() {
		/*
		 * Get any previous setting for location updates
		 * Gets "false" if an error occurs
		 */
		if (mPrefs.contains("KEY_UPDATES_ON")) {
			mUpdatesRequested =
					mPrefs.getBoolean("KEY_UPDATES_ON", false);

			// Otherwise, turn off location updates
		} else {
			mEditor.putBoolean("KEY_UPDATES_ON", false);
			mEditor.commit();
		}
		super.onResume();
	}

	
	private void SetPOIWindowAdapter() {
		// The in-built marker functionality truncates snippets that would span more than one line.
		// Override getInfoContents to display a custom view within the info window. This uses the popup.xml layout.
		// InfoWindowAdapter should be moved to a separate file.
		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker marker) {
				// Inflate the layout...
				View v = getLayoutInflater().inflate(R.layout.popup, null);
				// ... then get a handle on the title and snippet elements from the view...
				TextView title = (TextView) v.findViewById(R.id.title);
				TextView info= (TextView) v.findViewById(R.id.snippet);
				// ... and set them to the value that we would have displayed in the info box for the marker
				title.setText(marker.getTitle());
				info.setText(marker.getSnippet());
				return v;
			}

			@Override
			public View getInfoContents(Marker marker) {
				return null;
			}
		});
	}
	
	private class BackgroundStuff extends AsyncTask<Void, Void, Integer> {
		
		PolylineOptions line;
		LatLng latLngStart, latLngEnd;
		List<POI> pointsOfInterest = new ArrayList<POI>();
		
		@Override
		protected Integer doInBackground(Void... params) {

			int from = start;
			int to = end;

			if(direction == Direction.ANTI_CLOCKWISE) {
				// We're going anti-clockwise so swap form and to locations for the purpose of drawing the route.
				from = end;
				to = start;
			}
			
			Path path = GetPath(from, to, direction);
			latLngStart = SetStartCoords(path, direction);
			latLngEnd = SetEndCoords(path, direction);

			InitialiseMaxMinLatLon(latLngStart, latLngEnd);
			
			DrawPath(path);

			// Set viewable bounds based on the coordinates we calculated earlier.
			SetDefaultBounds(new LatLng(minLat, minLon), new LatLng(maxLat, maxLon));
			
			LoadPOIMarkers(from, to);
			return 1;
		}

		@Override
		protected void onPostExecute(Integer i) {
			PushPin(latLngStart, currRoute.getEndPoint(start), "Your walk starts here.", R.drawable.waypoint_start);
			PushPin(latLngEnd, currRoute.getEndPoint(end), "Your walk ends here.", R.drawable.waypoint_stop);
			map.addPolyline(line);
			
			markers = new ArrayList<Marker>();
			for (POI p: pointsOfInterest) {
				markers.add(
						PushPin(
								p.getCoords(),
								p.getTitle(),
								p.getSnippet(),
								R.drawable.waypoint_pause
						)
				);
			}
		}

		private String GetCoordinatesString(int startLocation, int endLocation) {
			int currentLocation = startLocation;
			String coordinates = "";
			while(currentLocation != endLocation) {

				InputStream myFile;
				try {
					Section s = currRoute.getSection(currentLocation);
					String filename;

					if(currentLocation == startLocation) {
						if(!s.getStartLinkResource().equals("")) {
							filename = s.getStartLinkResource();
							myFile = getApplicationContext().getAssets().open(filename);
							coordinates += CoordinateProvider.getRoute(myFile);
						}
					}
					filename = s.getSectionResource();
					myFile = getAssets().open(filename);
					coordinates += CoordinateProvider.getRoute(myFile);

					// We've constructed co-ordinates for this section so move to the next one
                    // If it's a circular route, allow wraparound from latLngEnd to latLngStart
					if(currRoute.isCircular() && currentLocation == currRoute.getSections().length - 1) {
						currentLocation = 0;
					} else {
						currentLocation++;
					}

					if(currentLocation == endLocation) {
						if(!s.getEndLinkResource().equals("")) {
							filename = s.getEndLinkResource();
							myFile = getAssets().open(filename);
							coordinates += CoordinateProvider.getRoute(myFile);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return coordinates;
		}
		
		private double[][] ParseCoordinates(String coordinates) {
            String[] coordinatesParsed;
			coordinatesParsed = coordinates.split(",0.000000"); // SAXParser loses "\n" characters so split on altitude which isn't set

			int lenNew = coordinatesParsed.length;
			double[][] wayPoints = new double[lenNew][2];
			for (int i = 0; i < lenNew; i++) {
				String[] xyParsed = coordinatesParsed[i].split(",");
				for (int j = 0; j < 2 && j < xyParsed.length; j++) {
					try {
						wayPoints[i][j] = Double.parseDouble(xyParsed[j]);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			return wayPoints;
		}
		
		private Path GetPath(int startLocation, int endLocation, Direction direction) {
			String coordinates = GetCoordinatesString(startLocation, endLocation);

			Path path = new Path();
			path.setWayPoints(ParseCoordinates(coordinates));
			return path;
		}
		
		private LatLng SetStartCoords(Path path, Direction direction) {
			if(direction == Direction.CLOCKWISE)
				return new LatLng((path.getWayPointLat(0)), (path.getWayPointLng(0)));
			else
				return new LatLng((path.getWayPointLat(path.getWayPoints().length-1)), (path.getWayPointLng(path.getWayPoints().length-1)));
		}

		private LatLng SetEndCoords(Path path, Direction direction) {
			if(direction == Direction.CLOCKWISE)
				return new LatLng((path.getWayPointLat(path.getWayPoints().length-1)), (path.getWayPointLng(path.getWayPoints().length-1)));
			else
				return new LatLng((path.getWayPointLat(0)), (path.getWayPointLng(0)));
		}
		
		private void InitialiseMaxMinLatLon(LatLng start, LatLng end) {
			maxLat = start.latitude > end.latitude ? start.latitude : end.latitude;
			minLat = start.latitude < end.latitude ? start.latitude : end.latitude;
			maxLon = start.longitude > end.longitude ? start.longitude : end.longitude;
			minLon = start.longitude < end.longitude ? start.longitude : end.longitude;
		}
		
		private void UpdateMaxMinLatLng(LatLng point) {
			if(point.latitude > maxLat) {
				maxLat = point.latitude;
			}
			if(point.latitude < minLat) {
				minLat = point.latitude;
			}
			if(point.longitude > maxLon) {
				maxLon = point.longitude;
			}
			if(point.longitude < minLon) {
				minLon = point.longitude;
			}
		}
		
		private PolylineOptions DrawPath(Path path) {
			if (path.getWayPoints().length > 2) {	// If length is 1, we only have 1 point so can't draw a line
				line = new PolylineOptions();
				line.width(5);
				line.color(Color.RED);
				
				LatLng from = null;
				LatLng to = null;

				for (int i = 1; i < path.getWayPoints().length; i++) {
					
					if(from == null)
						from = new LatLng((path.getWayPointLat(0)), (path.getWayPointLng(0)));
					else
						from = new LatLng(to.latitude, to.longitude);						
					
					to = new LatLng((path.getWayPointLat(i)), (path.getWayPointLng(i)));
					line.add(from, to);

					UpdateMaxMinLatLng(to);
				}
				return line;
			}
			return null;
		}
		
		private Marker PushPin(LatLng point, String title, String snippet, int icon) {
			return map.addMarker(new MarkerOptions()
					.position(point)
					.title(title)
					.snippet(snippet)
					.icon(BitmapDescriptorFactory
							.fromResource(icon)));
		}
		
		private void SetDefaultBounds(LatLng minLatLng, LatLng maxLatLng) {
			defaultBounds = new LatLngBounds.Builder();
			defaultBounds.include(minLatLng);
			defaultBounds.include(maxLatLng);
		}

		private void LoadPOIMarkers(int startLocation, int endLocation) {
			int currentLocation = startLocation;
			while(currentLocation != endLocation) {
				InputStream myFile;
				try {
					Section s = currRoute.getSection(currentLocation);
					myFile = getAssets().open(s.getPoiResource());
					
					pointsOfInterest.addAll(POIProvider.getPOIs(myFile));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// for circular routes, if we're at the final section, jump to the first section
				if(currRoute.isCircular() && currentLocation == currRoute.getSections().length - 1) {
					currentLocation = 0;
				} else {
					currentLocation++;
				}
			}
			bMarkersVisible = true;
		}
	}
} 
