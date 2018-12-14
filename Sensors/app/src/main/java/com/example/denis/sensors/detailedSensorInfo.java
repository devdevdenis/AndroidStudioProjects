package com.example.denis.sensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class detailedSensorInfo extends AppCompatActivity {

    TextView tvIndicators;
    SensorManager sensorManager;
    SensorEventListener mySensorEventListener;
    SeekBar sb;
    Sensor detailedSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_sensor_info);

        Bundle arguments = getIntent().getExtras();
        int sensorType = arguments.getInt("SENSOR_TYPE");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);

        LinearLayout ll = findViewById(R.id.sensorDetailsLayout);

        String[] parameters = new String[]{
                "Название сенсора: " + sensor.getName(),
                "Производитель: " + sensor.getVendor(),
                "Версия: " + sensor.getVersion(),
                "Разрешение: " + sensor.getResolution(),
                "Потребляемая мощность: " + sensor.getPower() + " mA",
                "Максимальный диапазон измерения: " + sensor.getMaximumRange(),
                "Минимальная задержка в обновлении: " + sensor.getMinDelay(),
                "Максимальная задержка в обновлении: " + sensor.getMaxDelay(),
        };

        for (String p : parameters
                ) {
            TextView tv = new TextView(this);
            tv.setTextSize(20);
            tv.setText(p);
            ll.addView(tv);
        }

        detailedSensor = sensor;

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE || sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            TextView tvTitle = findViewById(R.id.sensorDefineTextView);
            String sensorDefine = "Показатели ";
            if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                sensorDefine += "гироскопа. Все значения приведены в радианах в секунду и измеряют скорость вращения вокруг локальной оси X, Y и Z устройства. Система координат такая же, как и для датчика ускорения. Вращение положительное в направлении против часовой стрелки. То есть наблюдатель, смотрящий из некоторого положительного положения на оси x, y или z на устройство, расположенное в исходной точке, сообщит о положительном вращении, если устройство будет вращаться против часовой стрелки.";
            } else {
                sensorDefine += "датчика магнитного поля. Все значения приведены в микротеслах (мТл) и измеряют внешнее магнитное поле по осям X, Y и Z.";
            }

            tvTitle.setText(sensorDefine);

            tvIndicators = findViewById(R.id.indicatorTextView);
            tvIndicators.setTextColor(Color.parseColor("#FF5733"));

            Log.i("MYLOGS", "По варианту");
            mySensorEventListener = new SensorEventListener() {
                public void onSensorChanged(SensorEvent sensorEvent) {
                    String ind = "x: " + sensorEvent.values[0] + "\ny: " + sensorEvent.values[1] + "\nz: " + sensorEvent.values[2];
                    ind += "\nТочность показаний: " + sensorEvent.accuracy;
                    tvIndicators.setText(ind);
                }

                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    checkAccuracy(accuracy);
                }
            };

            sensorManager.unregisterListener(mySensorEventListener);
            sensorManager.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);


            sb = (SeekBar) findViewById(R.id.seekBar);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.i("MYLOGS", "Прогресс: " + progress);
                    sensorManager.unregisterListener(mySensorEventListener);
                    switch (progress) {
                        case 0: {
                            sensorManager.registerListener(mySensorEventListener, detailedSensor, SensorManager.SENSOR_DELAY_NORMAL);
                            break;
                        }
                        case 1: {
                            sensorManager.registerListener(mySensorEventListener, detailedSensor, SensorManager.SENSOR_DELAY_UI);
                            break;
                        }
                        case 2: {
                            sensorManager.registerListener(mySensorEventListener, detailedSensor, SensorManager.SENSOR_DELAY_GAME);
                            break;
                        }
                        case 3: {
                            sensorManager.registerListener(mySensorEventListener, detailedSensor, SensorManager.SENSOR_DELAY_FASTEST);
                            break;
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        else
        {
            LinearLayout linlay = (LinearLayout) findViewById(R.id.controls);
            linlay.setVisibility(View.INVISIBLE);
        }
    }

    public void returnBack(View v)
    {
        sensorManager.unregisterListener(mySensorEventListener);
        finish();
    }

    public void checkAccuracy(int accuracy)
    {
        Toast toast = new Toast(getApplicationContext());
        switch (accuracy){
            case 0:
            {
                toast = Toast.makeText(getApplicationContext(),
                        "Недостоверные данные, пользоваться данными нельзя, до осуществления калибровки.", Toast.LENGTH_LONG);
                break;
            }
            case 1:
            {
                toast = Toast.makeText(getApplicationContext(),
                        "Низкая точность вычислений, датчик нуждается в калибровке.", Toast.LENGTH_LONG);
                break;
            }
            case 2:
            {
                toast = Toast.makeText(getApplicationContext(),
                        "Средняя точность вычислений, рекомендуется калибровка датчика.", Toast.LENGTH_LONG);
                break;
            }
            case 3:
            {
                toast = Toast.makeText(getApplicationContext(),
                        "Максимальная точность вычислений датчика.", Toast.LENGTH_LONG);
                break;
            }
        }
        toast.show();
    }
}
