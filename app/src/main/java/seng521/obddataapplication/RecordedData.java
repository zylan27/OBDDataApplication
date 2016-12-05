package seng521.obddataapplication;

import java.util.Date;

/**
 * Created by ZoeyLan on 2016-11-21.
 */

public class RecordedData {

    private String timeStamp;
    private String latitude; // y-coordinate
    private String longitude; // x-coordinate
    private String speed;

    private String _id;

    public RecordedData(String timeStamp, String latitude, String longitude, String speed) {
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public RecordedData(String timeStamp, String latitude, String longitude, String speed, String _id) {
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this._id = _id;
    }

    @Override
    public String toString() {
        String returnString = "Time: " + timeStamp
                + "\nLatitude: " + latitude
                + "\nLongitude: " + longitude
                + "\nSpeed: " + speed;
        return returnString;
    }

    public String toJSON() {
        String returnString =
                "{ \"records\": [{" +
                "\"timeStamp\": \"" + timeStamp + "\", " +
                "\"latitude\": \"" + latitude + "\", " +
                "\"longitude\": \"" + longitude + "\", " +
                "\"speed\": \"" + speed + "\" " +
                "}]}";
        return returnString;
    }
}
