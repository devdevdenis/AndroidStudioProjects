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
                String define = "";
                String title = "";
                switch (position)
                {
                    case 0:
                    {
                        title = "Великолепный каток";
                        imges = new int[]{R.drawable.katok1, R.drawable.katok2, R.drawable.katok3, R.drawable.katok4};
                        define = "Каток спорт-парка «Варяг» защищен от осадков и не боится никакой погоды, т.к. сделан на искусственном льду и находится под крышей. В то же, время свежий воздух сопровождает весь период катания на льду.<br><br>" +
                                "Каток спортпарка «Варяг» - это место в Брянске, где можно отдохнуть, развлечься, отвлечься от городской суеты, пообщаться с друзьями. Это залог здоровья и хорошего настроения.<br><br>" +
                                "Родители, наш каток может легко помочь развлечь ваших детей. Вам больше не надо думать о том, куда еще сходить с ними. Просто приходите к нам и пускай дети наслаждаются. А вы можете посетить наше кафе, выпить чашечку горячего кофе или чая. Или же присоединиться к своим детям.<br><br>" +
                                "Каток «Варяг» оборудован звуковой и световой техникой, работает пункт проката коньков, заточка коньков, удобная и теплая раздевалка, парковка, кафе, туалеты. Для тех, кто хочет поиграть в хоккей, у нас работают секции. Для детей работает детская хоккейная школа «Варяг».<br><br>" +
                                "Режим работы:<br>Пон - Пт: <strong>с 19.00 - 22.00</strong><br>Сб - Вс: <strong>15.30 - 22.00</strong>";
                        break;
                    }
                    case 1:
                    {
                        title = "Горнолыжные склоны";
                        imges = new int[]{R.drawable.sklon1, R.drawable.sklon2, R.drawable.sklon3, R.drawable.sklon4, R.drawable.sklon5};
                        define = "В самом центре города Брянск, недалеко от Центрального парка культуры и отдыха имени 1000-летия города Брянска находятся горнолыжные склоны спортпарка «Варяг».<br><br>" +
                                "Для любителей горных лыж и сноуборда в Брянске «Варяг» предлагает пять склонов (Солнечный, Веселый, Молния 1, Молния 2, Спортивный), которые отличаются по своей сложности и протяженности. Здесь могут кататься как дети, так и взрослые.<br><br>" +
                                "Горнолыжные склоны оборудованы кресельным подъемником. Инфраструктура спортпарка «Варяг» позволяет горнолыжникам и сноубордистам чувствовать себя уютно и комфортно как при катании, так и во время отдыха. В перерывах между катанием вы можете выпить чашечку горячего кофе или чая и перекусить в нашем кафе. Для больших компаний в спортпарке «Варяг» можно арендовать домики для отдыха.<br><br>" +
                                "Режим работы:<br>Пон - Пт: <strong>с 19.00 - 22.00</strong><br>Сб - Вс: <strong>15.30 - 22.00</strong>";
                        break;
                    }
                    case 2:
                    {
                        title = "Многофункциональный SPA - центр";
                        imges = new int[]{R.drawable.spa1, R.drawable.spa2, R.drawable.spa3, R.drawable.spa4, R.drawable.spa5};
                        define = "SPA-центр на базе спортпарка «Варяг» включает в себя:<br>" +
                                " - 3 бассейна, оснащенных системами противотока, гидромассажа, системой гейзер. Один из бассейнов находится на открытом воздухе;<br>" +
                                " - джакузи;<br>" +
                                " - купель с холодной водой;<br>" +
                                " - турецкий хамам;<br>" +
                                " - 2 финские сауны.<br><br>" +
                                "Режим работы:<br>Пон - Пт: <strong>с 19.00 - 22.00</strong><br>Сб - Вс: <strong>15.30 - 22.00</strong>";
                        break;
                    }
                    case 3:
                    {
                        title = "Фитнес - Центр";
                        imges = new int[]{R.drawable.fitnes1, R.drawable.fitnes2, R.drawable.fitnes3, R.drawable.fitnes4};
                        define = "Фитнес-клуб «Варяг» – современный и многофункциональный фитнес-центр.<br><br>" +
                                "У нас вы можете воспользоваться услугами тренажерного зала, посетить групповые тренировки (регулярно проводятся занятия йогой, калланетикой, фитнес-аэробикой, танцами), записаться на бокс или единоборье.<br><br>" +
                                "В нашем фитнес-центре работает кабинет функциональной диагностики (кабинет фитнес-тестирования, где вы можете узнать состав своего тела на предмет соотношения мышечной и жировой массы), массажный кабинет и фитнес-бар с коктейлями и спортивным питанием.<br><br>" +
                                "Мы предлагаем своим гостям лучшее для поддержания крепкого здоровья и хорошей физической формы.<br><br>" +
                                "Режим работы:<br>Пон - Пт: <strong>с 19.00 - 22.00</strong><br>Сб - Вс: <strong>15.30 - 22.00</strong>";
                        break;
                    }
                    case 4:
                    {
                        title = "Банный комплекс";
                        imges = new int[]{R.drawable.banya1, R.drawable.banya2, R.drawable.banya3, R.drawable.banya4};
                        define = "Примите во внимание и тот факт, что в процессе парения мышцы освобождаются от накопившихся мочевины и молочной кислоты и становятся упругими и эластичными, приобретают ощутимый тонус. Значит, очень полезно посещать баню после физических нагрузок, например после тренировки в фитнес-центре. Делать это в один и тот же день или чередовать посещения, решать вам, взвесив все «за» и «против».<br><br>" +
                                "Главное, ходите в баню правильно. Прислушивайтесь к себе, своему телу, оценивайте объективно свое самочувствие — не переусердствуйте в погоне за здоровьем. Ведь лучше посетить баню дважды в неделю и провести там пару часов, чем пробыть часа четыре за один присест. Уже после первых правильных «банных» визитов вы ощутите прилив энергии, почувствуете себя сильнее и моложе. Не пренебрегайте положительной реакцией своего организма — посещайте хорошую баню систематически и как можно чаще." +
                                "Режим работы:<br>Пон - Пт: <strong>с 19.00 - 22.00</strong><br>Сб - Вс: <strong>15.30 - 22.00</strong>";
                        break;
                    }
                }

                intent.putExtra("object_name", title);
                intent.putExtra("img_array", imges);
                intent.putExtra("define", define);
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
