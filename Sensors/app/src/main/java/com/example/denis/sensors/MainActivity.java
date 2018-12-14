package com.example.denis.sensors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String vibratorService = Context.VIBRATOR_SERVICE;
        final Vibrator vibrator = (Vibrator)getSystemService(vibratorService);

        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        String[] sensorsNames = new String[allSensors.size()];
        final Integer[] sensorsTypes = new Integer[allSensors.size()];

        int i = 0;
        for (Sensor s:allSensors
             ) {
            sensorsNames[i] = s.getName();
            if (s.getType() == Sensor.TYPE_GYROSCOPE || s.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            {
                sensorsNames[i] += " \uD83D\uDE08";
            }
            sensorsTypes[i] = s.getType();
            i++;
        }

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sensorsNames);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("MYLOGS", sensorsTypes[position].toString());

                Intent intent = new Intent(getApplicationContext(), detailedSensorInfo.class);
                intent.putExtra("SENSOR_TYPE", sensorsTypes[position]);

                vibrator.vibrate(50); // Вибрировать на протяжении одной секунды.

                startActivity(intent);
            }
        });
    }
}
