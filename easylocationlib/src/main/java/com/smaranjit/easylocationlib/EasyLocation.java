package com.smaranjit.easylocationlib;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;


public class EasyLocation {
    static Context mCtx;
    static LocationAdapter mLocationAdapter;
    protected static List<OnLocationEventListener> listeners = new ArrayList<OnLocationEventListener>();

    public EasyLocation(){

    }
    public static EasyLocation with(Context ctx){
        mCtx = ctx;
        return new EasyLocation();
    }
    public EasyLocation listener(OnLocationEventListener listener){
        try {
            listeners.add(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public static void addListener(OnLocationEventListener listener) {
        try {
            listeners.add(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removeListener(OnLocationEventListener listener) {
        try {
            listeners.remove(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EasyLocation start(){
        mLocationAdapter = new LocationAdapter(mCtx);
        mLocationAdapter.startAdapter();
        return this;
    }
    public static void stop(){
        mLocationAdapter.stopLocationUpdates();
    }

    public interface OnLocationEventListener {
        void onLocationEvent(Location location);
    }
}
