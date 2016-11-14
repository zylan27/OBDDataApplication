package seng521.obddataapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTripFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    ArrayList<String> trips = new ArrayList<String>();

    public ViewTripFragment() {
        // Required empty public constructor
    }


    public static ViewTripFragment newInstance() {
        ViewTripFragment fragment = new ViewTripFragment();
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

        View view = inflater.inflate(R.layout.fragment_view_trip, container, false);

        ListView tripsList = (ListView) view.findViewById(R.id.tripsListId);

        trips.add("Trip 1");
        trips.add("Trip 2");
        trips.add("Trip 3");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.layout_trip_number,
                R.id.tripNumberTextId,
                trips);

        tripsList.setAdapter(arrayAdapter);

        tripsList.setOnItemClickListener(new OnItemClickListener()
            {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {

                        TextView textView = (TextView) view.findViewById(R.id.tripNumberTextId);
                        String selectedValue = textView.getText().toString();
                        Intent intent = new Intent(getActivity().getApplicationContext(), ViewTripDataActivity.class);
                        intent.putExtra("selectedValue ", selectedValue);
                        startActivity(intent);
                    }
            });

        return view;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onViewTripInteraction(uri);
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
        void onViewTripInteraction(Uri uri);
    }
}
