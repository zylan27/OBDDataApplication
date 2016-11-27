package seng521.obddataapplication;

import java.util.Date;
import java.util.List;

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
        String string = "ID: " + _id;

        return string;
    }

}
