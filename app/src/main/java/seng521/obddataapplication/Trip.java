package seng521.obddataapplication;

import java.util.Date;
import java.util.List;

import retrofit2.http.Path;

/**
 * Created by ZoeyLan on 2016-11-21.
 */

public class Trip {

    private Date startTime;
    private Date endTime;
    private String vehicleId;
    private boolean inProgress;
    private String _id;
    private List<RecordedData> recordedData;

    public Trip(Date startTime, Date endTime, String vehicleId, boolean inProgress)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.vehicleId = vehicleId;
        this.inProgress = inProgress;
    }

    public String toString()
    {
        String string = "ID: " + _id + "\nStart Time: " + startTime.toString() + "\nEnd Time: " + endTime.toString();

        return string;
    }

    public String getId()
    {
        return _id;
    }

    public String getStartTime()
    {
        return startTime.toString();
    }

    public String getEndTime()
    {
        return endTime.toString();
    }
}
