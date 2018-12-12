package com.example.denis.varyag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.synnapps.carouselview.ImageClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObjectsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ObjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObjectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectsFragment newInstance(String param1, String param2) {
        ObjectsFragment fragment = new ObjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_objects, container, false);

        // Array of strings for ListView Title
        String[] listviewTitle = new String[]{
                "Каток", "Горнолыжный склон", "Спа-центр", "Фитнес-центр",
                "Бани"
        };

        int[] listviewImage = new int[]{
                R.drawable.obj_katok, R.drawable.obj_sklon, R.drawable.obj_spa_center, R.drawable.obj_fitnes_center,
                R.drawable.obj_bany
        };

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.listview_image, R.id.listview_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_activity, from, to);
        ListView androidListView = (ListView) view.findViewById(R.id.listView);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "Clicked item: "+ position + " Parent: " + parent + " View: " + view + " id: " + id, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ObjectActivity.class);

                int [] imges = new int[]{};
                switch (position)
                {
                    case 0:
                    {
                        imges = new int[]{R.drawable.katok1, R.drawable.katok2, R.drawable.katok3, R.drawable.katok4};
                        break;
                    }
                    case 1:
                    {
                        imges = new int[]{R.drawable.sklon1, R.drawable.sklon2, R.drawable.sklon3, R.drawable.sklon4, R.drawable.sklon5};
                        break;
                    }
                    case 2:
                    {
                        imges = new int[]{R.drawable.spa1, R.drawable.spa2, R.drawable.spa3, R.drawable.spa4, R.drawable.spa5};
                        break;
                    }
                    case 3:
                    {
                        imges = new int[]{R.drawable.fitnes1, R.drawable.fitnes2, R.drawable.fitnes3, R.drawable.fitnes4};
                        break;
                    }
                    case 4:
                    {
                        imges = new int[]{R.drawable.banya1, R.drawable.banya2, R.drawable.banya3, R.drawable.banya4};
                        break;
                    }
                }

                intent.putExtra("img_array", imges);
                startActivity(intent);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
