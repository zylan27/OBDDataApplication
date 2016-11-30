package seng521.obddataapplication;

import java.util.Date;
import java.util.List;

/**
 * Created by ZoeyLan on 2016-11-28.
 */

public class ObdData {

    private Date timestamp;
    private List<String> errors;

    public ObdData(Date timestamp, List<String> errors) {
        this.timestamp = timestamp;
        this.errors = errors;
    }

    @Override
    public String toString() {
        String string = "Date " + timestamp.toString()
                +  "\n Errors: " + errors.toString();
        return string;
    }
}
