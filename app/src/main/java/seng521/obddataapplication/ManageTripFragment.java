package seng521.obddataapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Response;
import retrofit2.Retrofit;
//import retrofit2.Callback;
import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class ManageTripFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private boolean started = false;
    public ManageTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ManageTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageTripFragment newInstance() {

        ManageTripFragment fragment = new ManageTripFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_manage_trip, container, false);
        setRecordText(view, getString(R.string.start_record));
        started = false;

        Button recordButton = (Button) view.findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageRecordTrip(view);
            }
        });


        TextView vehicleIdMessage = (TextView) view.findViewById(R.id.vehicleIdMessageId);

        String message = new String("Recording Data For Vehicle ID: ");
        message = message + getActivity().getIntent().getStringExtra("vehicleId");

        vehicleIdMessage.setText(message);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onManageTripInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onManageTripInteraction(Uri uri);
    }

    private void manageRecordTrip(View view)
    {
        if(started)
        {
            started = !started;
            setRecordText(view, getString(R.string.start_record));

            //End Trip
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.server_address))
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

        }
        else
        {
            started = !started;
            setRecordText(view, getString(R.string.end_record));

            //Start trip
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.server_address))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OBDServerAPI apiService = retrofit.create(OBDServerAPI.class);
            Call<ResponseBody> startRequest = apiService.startTrip();
            startRequest.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // use response.code, response.headers, etc.
                    Log.d("myTag", "Start Trip: Success");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // handle failure
                    Log.d("myTag", "Start Trip: Failed");
                    String message = t.getMessage();
                    Log.d("Failure", message);

                }
            });

        }
    }

    void setRecordText(View view, String text)
    {
        Button recordButton = (Button) view.findViewById(R.id.recordButton);
        recordButton.setText(text);
    }
}
