package com.smaranjit.easylocationlib;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class EasyLocationSettings {
    static Activity mActivity;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static LocationRequest mLocationRequest = new LocationRequest();
    private int REQUEST_LOCATION;
    static long interval = 2000;
    static long fastestinterval = 1000;
    static int priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
    static int smallestdisplacement = 0;
    public static final int PRIORITY_HIGH_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    public static final int PRIORITY_LOW_POWER = LocationRequest.PRIORITY_LOW_POWER;
    public static final int PRIORITY_NO_POWER = LocationRequest.PRIORITY_NO_POWER;

    public EasyLocationSettings(){
        Dexter.withActivity(mActivity)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        //TODO:
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        //TODO:
                    }
                }).check();
    }

    public static EasyLocationSettings withActivity(Activity activity){
        mActivity = activity;
        return new EasyLocationSettings();
    }
    public EasyLocationSettings setInterval(long val){
        interval = val;
        return this;
    }

    public EasyLocationSettings setFastestInterval(long val){
        fastestinterval = val;
        return this;
    }
    public EasyLocationSettings setPriority(int val){
        priority = val;
        return this;
    }
    public EasyLocationSettings setSmallestDisplacement(int val){
        smallestdisplacement = val;
        return this;
    }

    public void build(){
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //L.sop("Location Permission Problem");
            //L.t(mActivity,"Location Permission Problem");
            // Check permission
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }

        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(fastestinterval);
        mLocationRequest.setPriority(priority);
        mLocationRequest.setSmallestDisplacement(smallestdisplacement);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        SettingsClient client = LocationServices.getSettingsClient(mActivity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        });

        task.addOnFailureListener(mActivity, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(mActivity,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }
}
