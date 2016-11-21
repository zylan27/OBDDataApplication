package seng521.obddataapplication;

import java.util.Date;

/**
 * Created by ZoeyLan on 2016-11-21.
 */

public class RecordedData {

    private Date mTimestamp;
    private double mLatitude; // y-coordinate
    private double mLongitude; // x-coordinate
    private double mSpeed;

    public RecordedData(Date timestamp, double latitude, double longitude, double speed) {
        mTimestamp = timestamp;
        mLatitude = latitude;
        mLongitude = longitude;
        mSpeed = speed;
    }
    
}
