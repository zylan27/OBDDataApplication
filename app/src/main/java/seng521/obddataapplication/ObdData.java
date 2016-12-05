package seng521.obddataapplication;

import java.util.Date;

/**
 * Created by ZoeyLan on 2016-11-28.
 */

public class ObdData {

    private String timeStamp;
    private String[] errors;
    private String _id;

    public ObdData(String timeStamp, String[] errors) {
        this.timeStamp = timeStamp;
        this.errors = errors;
    }

    public ObdData(String timeStamp, String[] errors, String _id) {
        this.timeStamp = timeStamp;
        this.errors = errors;
        this._id = _id;
    }

    @Override
    public String toString() {
        Date time=new Date(Long.parseLong(timeStamp)*(long)1000);
        String returnString = "Time: " + time
                +  "\n Errors: ";
        if(errors.length==0){
            returnString+="None";
        }else {
            for(int i=0; i<errors.length; i++){
                returnString+=errors[i]+", ";
            }
            //remove last comma
            returnString=returnString.substring(0,returnString.length()-2);
        }

        return returnString;
    }
}
