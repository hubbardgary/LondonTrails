//package com.hubbardgary.londontrails.view;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.IntentSender;
//import android.location.Location;
//import android.os.Bundle;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//
///*
// * Heavily based on this excellent tutorial:
// * http://blog.teamtreehouse.com/beginners-guide-location-android
// *
// * TODO: Not currently used due to issues with accuracy. Consider reintroducing at a later date.
// */
//public class LocationProvider implements
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener {
//
//    public interface LocationCallback {
//        void handleNewLocation(Location location);
//    }
//
//    /*
//     * Define a request code to send to Google Play services
//     * This code is returned in Activity.onActivityResult
//     */
//    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
//
//    private LocationCallback locationCallback;
//    private Context context;
//    private GoogleApiClient googleApiClient;
//    private LocationRequest locationRequest;
//
//    public LocationProvider(Context context, LocationCallback callback) {
//        locationCallback = callback;
//        googleApiClient = new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        locationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(30 * 1000)
//                .setFastestInterval(15 * 1000);
//
//        this.context = context;
//    }
//
//    public void connect() {
//        googleApiClient.connect();
//    }
//
//    public void disconnect() {
//        if (googleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//            googleApiClient.disconnect();
//        }
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//        if (location != null) {
//            locationCallback.handleNewLocation(location);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        /*
//         * Google Play services can resolve some errors it detects.
//         * If the error has a resolution, try sending an Intent to
//         * start a Google Play services activity that can resolve
//         * error.
//         */
//        if (connectionResult.hasResolution() && context instanceof Activity) {
//            try {
//                Activity activity = (Activity) context;
//                // Start an Activity that tries to resolve the error
//                connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//            /*
//             * Thrown if Google Play services cancelled the original
//             * PendingIntent
//             */
//            } catch (IntentSender.SendIntentException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        locationCallback.handleNewLocation(location);
//    }
//}
