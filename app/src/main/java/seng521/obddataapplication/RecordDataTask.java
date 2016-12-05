package seng521.obddataapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jonathan on 2016-12-02.
 */

public class RecordDataTask extends AsyncTask<Context, Void, Void>
{
    protected Void doInBackground(Context...cont)
    {
        Context activityContext = cont[0];

        GPSLocation myGPS = new GPSLocation(activityContext);

        Retrofit retrofitLoop = new Retrofit.Builder()
                .baseUrl(activityContext.getString(R.string.server_address))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OBDServerAPI apiService = retrofitLoop.create(OBDServerAPI.class);

        while(!isCancelled())
        {
            myGPS.getLocation();

            RecordedData currentData = new RecordedData(Long.toString(myGPS.getCurrentUnixTimeStamp()),
                    Double.toString(myGPS.getLatitude()),
                    Double.toString(myGPS.getLongitude()),
                    Double.toString(myGPS.getSpeed())
            );


            Log.d("myTag", currentData.toJSON());
            Call<ResponseBody> phoneRequest = apiService.addRecord(currentData.toJSON());
            phoneRequest.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // use response.code, response.headers, etc.
                    Log.d("myTag", "Uploading phone data");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // handle failure
                    Log.d("myTag", "Failed uploading data");
                    String message = t.getMessage();
                    Log.d("Failure", message);

                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
