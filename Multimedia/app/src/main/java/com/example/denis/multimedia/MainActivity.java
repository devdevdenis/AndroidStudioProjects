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

import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends AppCompatActivity {

    private boolean isRecording = false;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startRecord = findViewById(R.id.startRecord);
        final Button stopRecord = findViewById(R.id.stopRecord);
        final Button playRecord = findViewById(R.id.playRecord);

        startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });

        stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });

        playRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecord();
            }
        });
    }

    void startRecord(){
        Log.i("MYLOGS", "starting record");
        Log.i("MYLOGS", "dir: " + Environment.getExternalStorageDirectory());
        verifyStoragePermissions(this);
        int frequency = 11025;
        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        File file = new File(Environment.getExternalStorageDirectory(), "raw.pcm/test");
        // Создайте новый файл.
        try {
            boolean r = file.createNewFile();
            Log.i("MYLOGS", "create file" + r);
        } catch (IOException e) {
            Log.e("MYLOGS", "Error creating new file!\n" + e.getMessage());
        }

        try {
            Log.i("MYLOGS", file.getAbsolutePath());
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            int bufferSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration,
                    audioEncoding);
            short[] buffer = new short[bufferSize];
            // Создайте новый объект AudioRecord, чтобы записать звук.
            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    frequency,
                    channelConfiguration,
                    audioEncoding, bufferSize);
            audioRecord.startRecording();
            isRecording = true;
            // TODO: запустить в отдельном потоке?
            while (dos.size() < 40000) {
                int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                Log.i("MYLOGS", "Rec.." + " buff: " + dos.size());
                for (int i = 0; i < bufferReadResult; i++)
                    dos.writeShort(buffer[i]);
            }
            audioRecord.stop();
            Log.i("MYLOGS", "Audio recording stopped");
            dos.close();
        } catch (Throwable t) {
            Log.e("MYLOGS", "Error while recording!\n" + t.getMessage());
        }
    }

    void stopRecord(){
        Log.i("MYLOGS", "stop record");
        isRecording = false;
    }

    void playRecord(){
        Log.i("MYLOGS", "play record");
        int frequency = 11025/2;
//        int frequency = 11025;
        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        File file = new File(Environment.getExternalStorageDirectory(), "raw.pcm/test");
        // Массив типа short для хранения аудиоданных (звук 16-битный,
        // поэтому выделяем по 2 байта на значение)
        int audioLength = (int)(file.length()/2);
        short[] audio = new short[audioLength];
        try {
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            int i = 0;
            try
            {
                while (dis.available() > 0) {
                    Log.i("MYLOGS", "read.. dis avail: " + dis.available());
                    audio[i] = dis.readShort();
                    i++;
                }
                Log.i("MYLOGS", "1");
            }
            catch (Exception e)
            {
                Log.e("MYLOGS", "Reading error: " + e.getMessage());
            }
            Log.i("MYLOGS", "2");
            // Закрытие входящих потоков.
            dis.close();
            Log.i("MYLOGS", "3");
            // Создание объекта AudioTrack и проигрывание звука с его помощью
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    frequency,
                    channelConfiguration,
                    audioEncoding,
                    audioLength,
                    AudioTrack.MODE_STREAM);
            audioTrack.play();
            audioTrack.write(audio, 0, audioLength);
        } catch (Throwable t) {
            Log.e("MYLOGS", "Error during play record!\n" + t.getMessage());
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
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
}