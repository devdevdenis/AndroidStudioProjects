package com.example.denis.multimedia;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };

    // Timer
    private Timer mTimer;
    private TimerTask mMyTimerTask;
    private int recordTime = 3000;

    private ProgressBar pb;

    // Record
    private boolean isRecording = false;
    private int frequency = 11025;
    int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    DataOutputStream dos;
    AudioRecord audioRecord;
    int bufferSize;
    short[] buffer;

    // Logs
    private static String TAG = "MYLOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

        pb = findViewById(R.id.progressBar2);
    }

    public void startRecord(View v) {

//        //Timer task
//        if (mTimer != null) {
//            mTimer.cancel();
//        }
//
//        mTimer = new Timer();
//        mMyTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                isRecording = false;
//                Log.i(TAG, "Запись завершена.");
//            }
//        };
//        // Timer task execute
//        mTimer.schedule(mMyTimerTask, recordTime);

        File file = new File(getFilesDir(), "myRecord");
        // Создайте новый файл.Environment.getDataDirectory()
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.e(TAG, "Ошибка при создании файла для записи: " + e.getMessage());
        }
        try {
            Log.i(TAG, "Запись начата..");
            pb.setVisibility(View.VISIBLE);
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);
            bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
            buffer = new short[bufferSize];

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Создайте новый объект AudioRecord, чтобы записать звук.
                    audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, bufferSize);
                    audioRecord.startRecording();
                    
                    try {
                        isRecording = true;
                        while (isRecording) {
                            int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                            for (int i = 0; i < bufferReadResult; i++)
                                dos.writeShort(buffer[i]);
                        }
                        audioRecord.stop();
                        dos.close();
                    }catch (IOException e){
                        Log.e(TAG, "Проблема при записи в файл myRecord: " + e.getMessage());
                    }
                }
            }).start();

        } catch (Throwable t) {
            Log.e(TAG, "Ошибка записи: " + t.getMessage());
        }
    }

    // Not used
    public void stopRecord(View v) {
        Log.i(TAG, "Запись завершена.");
        pb.setVisibility(View.INVISIBLE);
        isRecording = false;
    }

    public void playRecord(View v) {
        Log.i(TAG, "Воспроизведение..");
        File file = new File(getFilesDir(), "myRecord");
        // Массив типа short для хранения аудиоданных (звук 16-битный,
        // поэтому выделяем по 2 байта на значение)
//        int audioLength = (int) (file.length() / 2);
        int audioLength = (int) (file.length());
        short[] audio = new short[audioLength];
        try {
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            int i = 0;
            while (dis.available() > 0) {
                audio[i] = dis.readShort();
                i++;
            }
            // Закрытие входящих потоков.
            dis.close();

            // Создание объекта AudioTrack и проигрывание звука с его помощью
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency, channelConfiguration, audioEncoding, audioLength, AudioTrack.MODE_STREAM);
            audioTrack.write(audio, 0, audioLength);
            audioTrack.play();
        } catch (Throwable t) {
            Log.e(TAG, "Ошибка при воспроизведении файла myRecord: " + t.getMessage());
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isRecording = false;
        if (audioRecord != null) {
            audioRecord.release();
        }
    }
}