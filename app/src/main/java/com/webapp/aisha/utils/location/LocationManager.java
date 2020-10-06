package com.webapp.aisha.utils.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.webapp.aisha.R;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;

public class LocationManager {

    // NOTE: If an activity uses this class, IT CANNOT USE MATCHING CODES
    public static final int LOCATION_SERVICES_CODE = 350;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 9001;

    private static final long DESIRED_LOCATION_TURNAROUND = 1000L;
    private static final long MILLISECONDS_BEFORE_FAILURE = 10000L;
    private final Handler locationChecker = new Handler();
    private Listener listener;
    private Activity activity;
    private Fragment fragment;
    private FusedLocationProviderClient locationFetcher;
    private LocationRequest locationRequest;
    private boolean locationFetched;
    private LocationServicesManager locationServicesManager;
    private MaterialDialog locationDenialDialog;
    private MaterialDialog locationPermissionDialog;

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult != null) {
                stopFetchingCurrentLocation();
                locationFetched = true;
                Location location = locationResult.getLastLocation();
                listener.onLocationFound(location.getLatitude(), location.getLongitude());
            }
        }
    };
    private final Runnable locationCheckTask = new Runnable() {
        @Override
        public void run() {
            stopFetchingCurrentLocation();
            if (!locationFetched) {
                onLocationFetchFail();
            }
        }
    };

    public LocationManager(@NonNull Listener listener, @NonNull Activity activity, @NonNull Fragment fragment) {
        this.listener = listener;
        this.activity = activity;
        this.fragment = fragment;
        initNonContext();
    }

    private void initNonContext() {
        locationServicesManager = new LocationServicesManager(activity);
        locationDenialDialog = new MaterialDialog.Builder(activity)
                .cancelable(false)
                .title(R.string.location_services_needed)
                .content(R.string.location_services_denial)
                .positiveText(R.string.location_services_confirm)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    locationServicesManager.askForLocationServices(LOCATION_SERVICES_CODE);
                    listener.onServicesOrPermissionChoice();
                })
                .onNegative((dialog, which) -> {
                    onLocationFetchFail();
                    listener.onServicesOrPermissionChoice();
                })
                .build();

        locationPermissionDialog = new MaterialDialog.Builder(activity)
                .cancelable(false)
                .title(R.string.location_permission_needed)
                .content(R.string.location_permission_denial)
                .positiveText(R.string.give_location_permission)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    requestLocationPermission();
                    listener.onServicesOrPermissionChoice();
                })
                .onNegative((dialog, which) -> {
                    onLocationFetchFail();
                    listener.onServicesOrPermissionChoice();
                })
                .build();

        locationFetcher = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = LocationRequest.create()
                .setInterval(DESIRED_LOCATION_TURNAROUND)
                .setFastestInterval(DESIRED_LOCATION_TURNAROUND)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void fetchCurrentLocation() {
        if (PermissionUtil.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION, activity)) {
            checkLocationServicesAndFetchLocationIfOn();
        } else {
            requestLocationPermission();
        }
    }

    private void checkLocationServicesAndFetchLocationIfOn() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(activity);
        client.checkLocationSettings(builder.build())
                .addOnSuccessListener(locationSettingsResponse -> fetchAutomaticLocation())
                .addOnFailureListener(exception -> {
                    if (exception instanceof ResolvableApiException) {
                        locationServicesManager.askForLocationServices(LOCATION_SERVICES_CODE);
                    } else {
                        onLocationFetchFail();
                    }
                });
    }

    private void requestLocationPermission() {
        if (fragment == null) {
            PermissionUtil.requestPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            PermissionUtil.requestPermissionOnFragment(fragment, Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void fetchAutomaticLocation() {
        //ToolUtils.showLongToast("R.string.location_services_on", activity);
        locationFetched = false;
        try {
            locationChecker.postDelayed(locationCheckTask, MILLISECONDS_BEFORE_FAILURE);
            locationFetcher.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException exception) {
            requestLocationPermission();
        }
    }

    private void stopFetchingCurrentLocation() {
        locationChecker.removeCallbacks(locationCheckTask);
        locationFetcher.removeLocationUpdates(locationCallback);
    }

    public void showLocationDenialDialog() {
        locationDenialDialog.show();
    }

    public void showLocationPermissionDialog() {
        locationPermissionDialog.show();
    }

    private void onLocationFetchFail() {
        ToolUtils.showLongToast(activity.getString(R.string.auto_location_fail), activity);
    }

    public void cleanUp() {
        activity = null;
    }

    public String distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return DecimalFormatterManager.getFormatterInstance().format(dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public String getRequestUri(LatLng origin, LatLng dest, Context context) {
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor-false";
        String mode = "mode=driving";
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode + "&key=";
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + context.getString(R.string.google_maps_key);
        Log.e("Urls.getRequestUri()", url);
        return url;
    }


    public interface Listener {
        void onServicesOrPermissionChoice();

        void onLocationFound(double latitude, double longitude);
    }
}