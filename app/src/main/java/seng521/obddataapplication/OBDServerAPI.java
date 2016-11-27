package seng521.obddataapplication;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ZoeyLan on 2016-11-27.
 */

public interface OBDServerAPI {

    @POST("/starttrip")
    Call<ResponseBody> startTrip();

    @POST("/endtrip")
    Call<ResponseBody> endTrip();

    @GET("/trips")
    Call<List<Trip>> getTrips();

    @GET("/trip")
    Call<Trip> getTrip(@Query("tripID") String tripId);

    @POST("/addPhoneRecords")
    Call<ResponseBody> addRecord(@Body Trip trip);


}
