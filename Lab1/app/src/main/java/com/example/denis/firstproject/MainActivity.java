package com.example.denis.firstproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    final String TAG = "lifecycle";
    final String TAG_INTERACT = "interact";

    TextView phoneNumber;
    TextView emailData;
    Button ringStudentBtn;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d(TAG, "Activity создано");

        phoneNumber = (TextView) findViewById(R.id.phoneData);
        emailData = (TextView) findViewById(R.id.emailData);
        ringStudentBtn = (Button) findViewById(R.id.ringBtn);

        //Вызов номера студента с запросом прав на совершение звонка
        ringStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG_INTERACT, "Вызов студента");
                String number = phoneNumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
                else
                {
                    //You already have permission
                    try {
                        startActivity(intent);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Набор номера студента (без вызова)
//        phoneNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG_INTERACT, "Набор номера студента");
//                String number = phoneNumber.getText().toString();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
//                startActivity(intent);
//            }
//        });

        //Отправка письма с заданным шаблоном
        emailData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG_INTERACT, "Отправка письма");
                String email = emailData.getText().toString();
                String uriText =
                        "mailto:" + email +
                                "?subject=" + Uri.encode("Invited in new world of Android!") +
                                "&body=" + Uri.encode("Hello Denis, i really happy that i have a chance to invite you in our big company!");

                Uri uri = Uri.parse(uriText);

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(uri);
                startActivity(intent.createChooser(intent, "Send email"));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity становится видимым");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity получает фокус (состояние Resumed)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity приостановлено (состояние Paused)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity остановлено (сосятоние Stopped)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity уничтожено");
    }
}