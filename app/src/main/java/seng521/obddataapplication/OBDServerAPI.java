package seng521.obddataapplication;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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


    @GET("/getPhoneTrip")
    Call<List<RecordedData>> getPhoneData(@Header("tripID") String tripID);

    @GET("/getOBDTrip")
    Call<List<ObdData>> getObdData(@Header("tripID") String tripId);

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("/addPhoneRecords")
    Call<ResponseBody> addRecord(@Field("data") String data);


}
