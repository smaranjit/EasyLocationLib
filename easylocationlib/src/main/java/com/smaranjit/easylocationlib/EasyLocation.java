package com.smaranjit.easylocationlib;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Smaranjit M.
 */
public class EasyLocation {
    private LocationAdapter mLocationAdapter;
    static List<OnLocationEventListener> listeners = new ArrayList<OnLocationEventListener>();
    private boolean isLocationAdapterStarted = false;

    private static EasyLocation single_instance = null;

    private EasyLocation(Context ctx) {
        mLocationAdapter = new LocationAdapter(ctx);
    }

    /**
     * get {@link Context} reference
     *
     * @param ctx context reference
     */
    public static EasyLocation with(Context ctx) {
        if (single_instance == null)
            single_instance = new EasyLocation(ctx);
        return single_instance;
    }

    /**
     * add listener of type {@link OnLocationEventListener} for receiving location
     *
     * @param listener listener reference of type {@link OnLocationEventListener}
     */
    public EasyLocation listener(OnLocationEventListener listener) {
        try {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * add listener of type {@link OnLocationEventListener} for receiving location
     *
     * @param listener listener reference of type {@link OnLocationEventListener}
     */
    public static void addListener(OnLocationEventListener listener) {
        try {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * removes listener of type {@link OnLocationEventListener}
     *
     * @param listener listener reference of type {@link OnLocationEventListener}
     */
    public static void removeListener(OnLocationEventListener listener) {
        try {
            listeners.remove(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * start get location and providing to all attached listener of type {@link OnLocationEventListener}
     */
    public EasyLocation start() {
        if (!isLocationAdapterStarted) {
            mLocationAdapter.startAdapter();
            isLocationAdapterStarted = true;
        }
        return this;
    }

    /**
     * stop all location service
     */
    public void stop() {
        if (isLocationAdapterStarted) {
            mLocationAdapter.stopLocationUpdates();
            isLocationAdapterStarted = false;
        }

    }

    /**
     * interface to implement, for attaching and receiving location data
     */
    public interface OnLocationEventListener {
        void onLocationEvent(Location location);
    }
}
