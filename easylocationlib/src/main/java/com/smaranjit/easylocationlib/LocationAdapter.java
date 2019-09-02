package com.smaranjit.easylocationlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class LocationAdapter {
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private Context ctx;
    public LocationAdapter(Context ctx) {
        this.ctx = ctx;
    }


    public void startAdapter() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.ctx);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    try {
                        for(EasyLocation.OnLocationEventListener listener : EasyLocation.listeners){
                            if(listener!=null){
                                listener.onLocationEvent(location);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        };


        startLocationUpdates();
        // Create one instance and share it

    }

    public void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(EasyLocationSettings.mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }
}
