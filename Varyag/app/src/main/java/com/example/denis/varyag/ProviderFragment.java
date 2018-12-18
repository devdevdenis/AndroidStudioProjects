package com.example.denis.varyag;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProviderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    final Uri EVENT_URI = Uri
            .parse("content://com.example.denis.varyag.provider/EventModel");

    final String EVENT_TITLE = "title";
    final String EVENT_IMG = "img";
    final String EVENT_DEFINE = "define";
    public View currentView;

    private OnFragmentInteractionListener mListener;

    public ProviderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProviderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderFragment newInstance(String param1, String param2) {
        ProviderFragment fragment = new ProviderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_provider, container, false);

        Button btnInsert = view.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        currentView = view;
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

    public void addItem(){
        ContentValues cv = new ContentValues();

        EditText el = (EditText) currentView.findViewById(R.id.eventLabel);
        EditText ed = (EditText) currentView.findViewById(R.id.eventDefine);
        EditText ei = (EditText) currentView.findViewById(R.id.eventImg);

        cv.put(EVENT_TITLE, el.getText().toString());
        cv.put(EVENT_IMG, ei.getText().toString());
        cv.put(EVENT_DEFINE, ed.getText().toString());
        Uri newUri = currentView.getContext().getContentResolver().insert(EVENT_URI, cv);
        Log.d("MYLOGS", "insert, result Uri : " + newUri.toString());
    }

    public void deleteItem(){
        EditText rn = (EditText) currentView.findViewById(R.id.rowId);
        if (!rn.getText().toString().equals(""))
        {
            Long rowId = Long.parseLong(rn.getText().toString());
//            Uri uri = ContentUris.withAppendedId(EVENT_URI, rowId);
            String[] args = {rowId.toString()};
            int cnt = currentView.getContext().getContentResolver().delete(EVENT_URI, "id = ?", args);
            Log.d("MYLOGS", "delete, count = " + cnt);

            if (cnt == 0)
            {
                Toast.makeText(getActivity(), "Запись с указанным id не существует.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Указанная запись успешно удалена.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Укажите идентификатор записи!", Toast.LENGTH_SHORT).show();
        }
    }
}
