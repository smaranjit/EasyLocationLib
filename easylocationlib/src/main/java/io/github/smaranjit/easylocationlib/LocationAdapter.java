package io.github.smaranjit.easylocationlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * this class starts and stops location service using {@link FusedLocationProviderClient}<br>
 * this class also provides data to all callback location listeners
 */
class LocationAdapter {
    private FusedLocationProviderClient mFusedLocationClient;
    private static LocationCallback mLocationCallback;
    private Context ctx;

    LocationAdapter(Context ctx) {
        this.ctx = ctx;
    }


    /**
     * initializes and starts location service for fetching location data
     */
    void startAdapter() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.ctx);


        //receives location data
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    try {
                        for (EasyLocation.OnLocationEventListener listener : EasyLocation.listeners) {
                            if (listener != null) {
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

    /**
     * starts fetching location data
     */
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(EasyLocationSettings.mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    /**
     * stops fetching location data
     */
    void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
