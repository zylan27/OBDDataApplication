package seng521.obddataapplication;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import android.content.Context;
import android.location.Location;
import java.util.Calendar;

/**
 * Created by Jonathan on 2016-11-28.
 */


public class GPSLocation
{
    private GoogleApiClient myGoogleClient;
    private Location previousLocation, currentLocation;
    private long previousTime, currentTime;

    //Set this to true if you want dummy values instead of actual values
    private static boolean dummyValues = false;

    public GPSLocation(){}

    public GPSLocation(Context cont,
                       GoogleApiClient.ConnectionCallbacks callbackListener,
                       GoogleApiClient.OnConnectionFailedListener failedListener)
    {
        myGoogleClient = new GoogleApiClient.Builder(cont)
                .addConnectionCallbacks(callbackListener)
                .addOnConnectionFailedListener(failedListener)
                .addApi(LocationServices.API)
                .build();
        myGoogleClient.connect();

        previousTime = Calendar.getInstance().getTimeInMillis();
        currentTime = Calendar.getInstance().getTimeInMillis();

        if (dummyValues)
        {
            currentLocation.setLatitude(51.081468);
            currentLocation.setLongitude(-114.130640);
        }
        else
        {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient);
        }
    };

    public void getLocation()
    {
        previousLocation = currentLocation;
        previousTime = currentTime;

        currentTime = Calendar.getInstance().getTimeInMillis();

        if (dummyValues)
        {
            currentLocation.setLatitude(currentLocation.getLatitude() + 0.0001);
            currentLocation.setLongitude(currentLocation.getLongitude() - 0.0001);
        }

        currentLocation = LocationServices.FusedLocationApi.getLastLocation(myGoogleClient);
    }

    public double getLatitude()
    {
        if (currentLocation != null)
        {
            return currentLocation.getLatitude();
        }
        else
        {
            return 1001;
        }
    }

    public double getLongitude()
    {
        if (currentLocation != null)
        {
            return currentLocation.getLongitude();
        }
        else
        {
            return 1001;
        }
    }

    public float distanceCovered()
    {
        if (currentLocation != null && previousLocation != null)
        {
            return previousLocation.distanceTo(currentLocation);
        }
        else
        {
            return 0;
        }
    }

    //Return the time interval in milliseconds between retrieval of current location and the time before
    public long timeSinceLast()
    {
        return currentTime - previousTime;
    }
}
