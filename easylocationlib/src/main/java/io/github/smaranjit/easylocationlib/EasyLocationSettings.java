package io.github.smaranjit.easylocationlib;

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

/**
 * this class manages and builds location request settings
 * created by Smaranjit M.
 */
public class EasyLocationSettings {
    private Activity mActivity;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    static LocationRequest mLocationRequest = new LocationRequest();
    private int REQUEST_LOCATION;
    private static long interval = 2000;
    private static long fastestinterval = 1000;
    private static int priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
    private static int smallestdisplacement = 0;
    public static final int PRIORITY_HIGH_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    public static final int PRIORITY_LOW_POWER = LocationRequest.PRIORITY_LOW_POWER;
    public static final int PRIORITY_NO_POWER = LocationRequest.PRIORITY_NO_POWER;

    private EasyLocationSettings(Activity activity) {
        this.mActivity = activity;
        Dexter.withActivity(this.mActivity)
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

    /**
     * get activity reference for location setting client
     *
     * @param activity reference of calling {@link Activity} class
     */
    public static EasyLocationSettings withActivity(Activity activity) {
        return new EasyLocationSettings(activity);
    }

    /**
     * sets interval setting for setting client, default val = 2000
     *
     * @param val interval value in milliseconds
     */
    public EasyLocationSettings setInterval(long val) {
        interval = val;
        return this;
    }

    /**
     * sets fastest interval setting for setting client, default val = 1000
     *
     * @param val fastest interval value in milliseconds
     */
    public EasyLocationSettings setFastestInterval(long val) {
        fastestinterval = val;
        return this;
    }

    /**
     * sets priority setting for setting client, default val = EasyLocationSettings.PRIORITY_HIGH_ACCURACY
     *
     * @param val value could be EasyLocationSettings.PRIORITY_HIGH_ACCURACY
     *            or EasyLocationSettings.PRIORITY_BALANCED_POWER_ACCURACY
     *            or EasyLocationSettings.PRIORITY_LOW_POWER
     *            or EasyLocationSettings.PRIORITY_NO_POWER
     */
    public EasyLocationSettings setPriority(int val) {
        priority = val;
        return this;
    }

    /**
     * set smallest displacement setting for setting client in meter, default val = 0
     *
     * @param val fastest interval value in milliseconds
     */
    public EasyLocationSettings setSmallestDisplacement(int val) {
        smallestdisplacement = val;
        return this;
    }

    /**
     * builds location request setting using {@link LocationRequest}<br>
     * shows dialog for turning on gps if not already done
     */
    public void build() {
        if (ActivityCompat.checkSelfPermission(this.mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //L.sop("Location Permission Problem");
            //L.t(mActivity,"Location Permission Problem");
            // Check permission
            ActivityCompat.requestPermissions(this.mActivity,
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

        SettingsClient client = LocationServices.getSettingsClient(this.mActivity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        });

        task.addOnFailureListener(this.mActivity, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(this.mActivity,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }
}
