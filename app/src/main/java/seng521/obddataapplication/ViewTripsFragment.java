package seng521.obddataapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewTripsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTripsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    ArrayList<String> trips = new ArrayList<String>();

    public ViewTripsFragment() {
        // Required empty public constructor
    }


    public static ViewTripsFragment newInstance() {
        ViewTripsFragment fragment = new ViewTripsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ListView tripsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_trip, container, false);

        tripsList = (ListView) view.findViewById(R.id.tripsListId);

        Button refreshTripsButton = (Button) view.findViewById(R.id.refreshTripsButtonId);
        refreshTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshTrips(view);
            }
        });

        return view;
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
        void onViewTripInteraction(Uri uri);
    }

    private void refreshTrips(View view)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.server_address))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OBDServerAPI apiService = retrofit.create(OBDServerAPI.class);
        Call<List<Trip>> getTrips = apiService.getTrips();


        getTrips.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip> > response) {
                // use response.code, response.headers, etc.
                Log.d("myTag", "Get Trips: Success");

                final List<Trip> trips = response.body();

                Log.d("myTag", "Printing Trips");
                Log.d("myTag", trips.toString());

                ArrayAdapter<Trip> arrayAdapter = new ArrayAdapter<Trip>(
                        getActivity().getApplicationContext(),
                        R.layout.layout_trip_number,
                        R.id.tripNumberTextId,
                        trips);

                tripsList.setAdapter(arrayAdapter);

                tripsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                Trip trip = trips.get(position);
                                Intent intent = new Intent(getActivity().getApplicationContext(), ViewTripDataActivity.class);
                                intent.putExtra("tripId", trip.getId());
                                intent.putExtra("startTime", trip.getStartTime());
                                intent.putExtra("endTime", trip.getEndTime());

                                startActivity(intent);
                            }
                    });
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                // handle failure
                Log.d("myTag", "Get Trips: Failed");
                String message = t.getMessage();
                Log.d("Failure", message);

            }
        });



    }
}
