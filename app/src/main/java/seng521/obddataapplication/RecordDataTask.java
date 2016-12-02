package seng521.obddataapplication;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;

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

        GoogleApiClient.ConnectionCallbacks callbackListener;
        GoogleApiClient.OnConnectionFailedListener failedListener;

        while(!isCancelled())
        {/*
            GPSLocation myGPS = new GPSLocation(activityContext, callbackListener, failedListener);

            RecordedData myData = new RecordedData(Calendar.getInstance().getTime(),
                                                    myGPS.getLatitude(),
                                                    myGPS.getLongitude(),
                                                    myGPS.getSpeed()
                                                    );
*/
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(activityContext.getString(R.string.server_address))
                    .build();

            OBDServerAPI apiService = retrofit.create(OBDServerAPI.class);
            Call<ResponseBody> endResponse = apiService.endTrip();
            endResponse.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // use response.code, response.headers, etc.
                    Log.d("myTag", "End Trip: Success");

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // handle failure
                    Log.d("myTag", "End Trip: Failed");
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
