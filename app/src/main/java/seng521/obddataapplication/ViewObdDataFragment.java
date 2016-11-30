package seng521.obddataapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewObdDataFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public ViewObdDataFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ViewObdDataFragment newInstance() {
        ViewObdDataFragment fragment = new ViewObdDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ListView obdDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obd_data, container, false);

        obdDataList = (ListView) view.findViewById(R.id.obdDataList);

        // Display list;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.server_address))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OBDServerAPI apiService = retrofit.create(OBDServerAPI.class);
        String tripId = getActivity().getIntent().getStringExtra("tripId");

        Call<List<ObdData>> getObdData = apiService.getObdData(tripId);

        getObdData.enqueue(new Callback<List<ObdData>>() {
            @Override
            public void onResponse(Call<List<ObdData>> call, Response<List<ObdData> > response) {
                // use response.code, response.headers, etc.
                Log.d("myTag", "Get OBD Data: Success");

                final List<ObdData> recordedData = response.body();

                Log.d("myTag", "Printing OBD Data");
                Log.d("myTag", recordedData.toString());

                ArrayAdapter<ObdData> arrayAdapter = new ArrayAdapter<ObdData>(
                        getActivity().getApplicationContext(),
                        R.layout.layout_trip_number,
                        R.id.tripNumberTextId,
                        recordedData);

                obdDataList.setAdapter(arrayAdapter);

            }

            @Override
            public void onFailure(Call<List<ObdData>> call, Throwable t) {
                // handle failure
                Log.d("myTag", "Get Phone Data: Failed");
                String message = t.getMessage();
                Log.d("Failure", message);

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onObdDataFragmentInteraction(Uri uri);
    }
}
