package seng521.obddataapplication;

import java.util.Date;

/**
 * Created by ZoeyLan on 2016-11-21.
 */

public class RecordedData {

    private Date timestamp;
    private double latitude; // y-coordinate
    private double longitude; // x-coordinate
    private double speed;
    private String _id;

    public RecordedData(Date timestamp, double latitude, double longitude, double speed) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public RecordedData(Date timestamp, double latitude, double longitude, double speed, String _id) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this._id = _id;
    }

    @Override
    public String toString() {
        String string = "Time: " + timestamp.toString()
                + "\nLatitude: " + Double.toString(latitude)
                + "\nLongitude: " + Double.toString(longitude)
                + "\nSpeed: " + Double.toString(speed);
        return string;
    }
}
