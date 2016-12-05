package seng521.obddataapplication;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Jonathan on 2016-11-28.
 */


public class GPSLocation implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private GoogleApiClient myGoogleClient;
    private double currentSpeed, previousLatitude, previousLongitude, currentLatitude, currentLongitude;
    private long previousTime, currentTime;

    //Set this to true if you want dummy values instead of actual values
    private static boolean dummyValues = true;

    public GPSLocation(){}

    public GPSLocation(Context cont)
    {
        previousTime = Calendar.getInstance().getTimeInMillis();
        currentTime = Calendar.getInstance().getTimeInMillis() + (4 * 60 * 1000) + 25000;
        currentSpeed = 0;

        if (dummyValues)
        {   //GPS coordinates near university
            previousLatitude = 51.081468;
            previousLongitude = -114.130640;
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
            myGoogleClient.connect();

            currentLatitude = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient).getLatitude();
            currentLongitude = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient).getLongitude();
        }
    };

    public void getLocation()
    {
        previousLatitude = currentLatitude;
        previousLongitude = currentLongitude;

        previousTime = currentTime;
        currentTime = Calendar.getInstance().getTimeInMillis() + (4 * 60 * 1000) + 25000;

        if (dummyValues)
        {
            currentLatitude += 0.0001;
            currentLongitude += 0.0001;
            setSpeed();
            return;
        }

        currentLatitude = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient).getLatitude();
        currentLongitude = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient).getLongitude();
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
        double timeInHours = (timeSinceLast() / 1000) / 3600;
        double distanceInKilos = distanceCovered() / 1000;
        currentSpeed = distanceInKilos/timeInHours;
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
}
