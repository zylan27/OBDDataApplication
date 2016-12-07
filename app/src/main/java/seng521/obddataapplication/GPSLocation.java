package seng521.obddataapplication;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Calendar;

import retrofit2.http.Path;

/**
 * Created by Jonathan on 2016-11-28.
 */


public class GPSLocation implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    protected GoogleApiClient myGoogleClient;
    private double currentSpeed, previousLatitude, previousLongitude, currentLatitude, currentLongitude;
    private long previousTime, currentTime;
    private Location currentLocation;
    Context thisContext;

    //LocationManager lm = (LocationManager)thisContext.getSystemService(thisContext.LOCATION_SERVICE);

    //Set this to true if you want dummy values instead of actual values
    protected static boolean dummyValues = false;
    private static long timeOffset = (1 * 60 * 1000) + 12000;
    //private static long timeOffset = 0;

    public GPSLocation(){}

    public GPSLocation(Context cont)
    {
        thisContext = cont;
        previousTime = Calendar.getInstance().getTimeInMillis() + timeOffset;
        currentTime = Calendar.getInstance().getTimeInMillis() + timeOffset;
        Log.d("Time Offset: ", Long.toString(timeOffset));
        currentSpeed = 0;

        if (dummyValues)
        {   //GPS coordinates near university
            previousLatitude = 51.081467;
            previousLongitude = -114.130641;
            currentLatitude = 51.081468;
            currentLongitude = -114.130640;
        }
        else
        {
            myGoogleClient = new GoogleApiClient.Builder(cont)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            if (ContextCompat.checkSelfPermission( cont, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
            {
                if (!myGoogleClient.isConnected()) myGoogleClient.connect();

                if (myGoogleClient.isConnected())
                {
                    LocationRequest lr = LocationRequest.create()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(1 * 1000)        // 1 second, in milliseconds
                            .setFastestInterval(1 * 1000); // 1 second, in milliseconds

                    LocationServices.FusedLocationApi.requestLocationUpdates(myGoogleClient, lr, this);
                    currentLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient);
                }

                if(currentLocation!=null)
                {
                    currentLatitude = currentLocation.getLatitude();
                    currentLongitude = currentLocation.getLongitude();
                }

            }
            else
            {
                previousLatitude = 51.081467;
                previousLongitude = -114.130641;
                currentLatitude = 51.081468;
                currentLongitude = -114.130640;
            }
        }
    };

    public void getLocation()
    {
        previousLatitude = currentLatitude;
        previousLongitude = currentLongitude;

        previousTime = currentTime;
        currentTime = Calendar.getInstance().getTimeInMillis() + timeOffset;

        if (dummyValues)
        {
            currentLatitude += 0.0001;
            currentLongitude += 0.0001;
            setSpeed();
            return;
        }

        if (ContextCompat.checkSelfPermission( thisContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            if (!myGoogleClient.isConnected()) myGoogleClient.connect();

            if (myGoogleClient.isConnected())
            {
                LocationRequest lr = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1 * 1000)        // 1 second, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds

                LocationServices.FusedLocationApi.requestLocationUpdates(myGoogleClient, lr, this);
                currentLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient);
            }

            if(currentLocation != null)
            {
                currentLatitude = currentLocation.getLatitude();
                currentLongitude = currentLocation.getLongitude();
            }
        }

        setSpeed();
        return;
    }

    public long getCurrentUnixTimeStamp()
    {
        return currentTime / 1000;
    }

    public double getLatitude()
    {
        return currentLatitude;
    }

    public double getLongitude()
    {
        return currentLongitude;
    }

    //Get's the distance between the previous and current locations in metres
    public float distanceCovered()
    {
        float[] results = new float[3];

        Location.distanceBetween(previousLatitude, previousLongitude, currentLatitude, currentLongitude, results);

        return results[0];
    }

    //Return the time interval in milliseconds between retrieval of current location and the time before
    public long timeSinceLast()
    {
        return currentTime - previousTime;
    }

    private void setSpeed()
    {
        double speedCalc = 0;
        speedCalc = distanceCovered() / timeSinceLast();    // m/ms
        speedCalc *= 1000;  // m/s
        speedCalc *= 3600;  // m/hr
        speedCalc /= 1000;  // km/hr

        currentSpeed = speedCalc;
    }

    //Get (and set) the speed in kmph
    public double getSpeed()
    {
        return currentSpeed;
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void onProviderEnabled(String provider) {}

    public void onProviderDisabled(String provider) {}
}
