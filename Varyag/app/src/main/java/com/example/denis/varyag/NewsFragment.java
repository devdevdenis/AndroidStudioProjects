package com.example.denis.varyag;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbflow5.config.FlowManager;
import com.dbflow5.database.DatabaseWrapper;
import com.dbflow5.query.SQLite;

import org.intellij.lang.annotations.Flow;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean updated = false;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //            Log.i("MYLOGS", FlowManager.INSTANCE.toString());
        FlowManager.init(getActivity());

        EventModel evModel = new EventModel();

        evModel.setTitle("Вечерние тренировки в хоккейной школе Варяга");
        evModel.setImg("news_hockey");
        evModel.setDefine("Мы снова набираем вечернюю группу по тренировкам хоккею! Лучшие тренера Европы, 2 часа интенсивной работы, индивидуальный подход к каждому ученику. Принимаем желающих всех возрастов!");

        //        evModel.save(true);
        DatabaseWrapper dw = FlowManager.getWritableDatabase(AppDatabase.class);
        FlowManager.getModelAdapter(EventModel.class).save(evModel, dw);

        evModel = new EventModel();

        evModel.setTitle("Открытие горнолыжных склонов!");
        evModel.setImg("news_sklon");
        evModel.setDefine("Друзья, завтра долгожданный для всех день! Открываем сезон на горнолыжных склонах и склонах для тюбинга! Горнолыжные работают 01.12 и 02.12 работают с 12.00 до 22.00. Каток завтра с 14.00 до 22.00. Ежедневный режим работы, цены и подробности по ссылке ..");
        FlowManager.getModelAdapter(EventModel.class).save(evModel, dw);

        evModel = new EventModel();

        evModel.setTitle("Мы наконец-то засыпали горку для тюбинга!");
        evModel.setImg("news_tubing");
        evModel.setDefine("В эти выходные мы рады приветствовать на нашей горке для тюбинга! Подарите своему ребенку незабываемые выходные на горках Варяга! Ждем всех с 10 до 22.00 каждый день..");
        FlowManager.getModelAdapter(EventModel.class).save(evModel, dw);

        // List
        List<EventModel> events = SQLite.select()
                .from(EventModel.class)
                .queryList(dw);

        for (EventModel ev:events
                ) {
            CardView cw = new CardView(getActivity(), null, R.style.MyCardViewStyle);
            LinearLayout ll = new LinearLayout(getActivity());
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            ll.setOrientation(LinearLayout.VERTICAL);

            TextView twLabel = new TextView(getActivity(), null, R.style.EventLabel);
            twLabel.setText(ev.getTitle());

            TextView twDefine = new TextView(getActivity());
            twDefine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            twDefine.setText(ev.getDefine());

            Button btnMore = new Button(getActivity());
            btnMore.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnMore.setText(R.string.more);

            // img from drawable resourse
            ImageView img = new ImageView(getActivity());
            img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageResource(getResources().getIdentifier(ev.getImg(),
                    "drawable", getActivity().getPackageName()));
            img.setContentDescription(ev.getTitle());

            cw.addView(ll);
            ll.addView(twLabel);
            ll.addView(img);
            ll.addView(twDefine);
            ll.addView(btnMore);

            LinearLayout eventsContainer = (LinearLayout) getActivity().findViewById(R.id.eventsContainer);
            eventsContainer.addView(cw);

            Log.i("MYLOGS",ev.getImg() + " : " + ev.getTitle() + " : " + ev.getDefine());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
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
