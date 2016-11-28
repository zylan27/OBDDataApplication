package seng521.obddataapplication;

import android.app.Service;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Bundle;
import android.content.Intent;

/**
 * Created by Jonathan on 2016-11-28.
 */

public class GPSLocation extends Service implements LocationListener {
    //private final Context mContext;

    private static final int MIN_UPDATE_DISTANCE = 5; // 10 meters
    private static final int MIN_UPDATE_TIME = 1000; // 1 second

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSLocation(/*Context context*/) {
        //this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            /*locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
*/
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_UPDATE_TIME,
                                MIN_UPDATE_DISTANCE, this);
                        //Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}