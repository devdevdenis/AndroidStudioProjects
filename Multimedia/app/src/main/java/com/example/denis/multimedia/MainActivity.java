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

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };
    private boolean isRecording = false;
    private int frequency = 11025;
    int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    DataOutputStream dos;
    AudioRecord audioRecord;
    int bufferSize;
    short[] buffer;
    private static String TAG = "MYLOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
    }

    public void startRecord(View v) {
        File file = new File(Environment.getExternalStorageDirectory(), "raw.pcm/test");
        // Создайте новый файл.
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.e(TAG, "Ошибка при создании файла для записи: " + e.getMessage());
        }
        try {
            Log.i(TAG, "Запись начата..");
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            dos = new DataOutputStream(bos);
            bufferSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration,
                    audioEncoding);
            buffer = new short[bufferSize];
            // Создайте новый объект AudioRecord, чтобы записать звук.
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    frequency,
                    channelConfiguration,
                    audioEncoding, bufferSize);
            audioRecord.startRecording();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (audioRecord == null)
                        return;

                    try {
                        while (isRecording) {
                            int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                            for (int i = 0; i < bufferReadResult; i++)
                                dos.writeShort(buffer[i]);
                        }
                        audioRecord.stop();
                        dos.close();
                    }catch (IOException e){
                        Log.e(TAG, "Проблема при чтении сырого значения в буфер: " + e.getMessage());
                    }
                }
            }).start();
        } catch (Throwable t) {
            Log.e(TAG, "Ошибка при чтении содержимого файла: " + t.getMessage());
        }
    }

    public void stopRecord(View v) {
        Log.i(TAG, "Запись завершена");
        isRecording = false;
    }

    public void playRecord(View v) {
        File file = new File(Environment.getExternalStorageDirectory(), "raw.pcm/test");
        // Массив типа short для хранения аудиоданных (звук 16-битный,
        // поэтому выделяем по 2 байта на значение)
        int audioLength = (int) (file.length() / 2);
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
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    frequency,
                    channelConfiguration,
                    audioEncoding,
                    audioLength,
                    AudioTrack.MODE_STREAM);
            audioTrack.play();
            audioTrack.write(audio, 0, audioLength);
        } catch (Throwable t) {
            Log.e(TAG, "Ошибка при воспроизведении файла: " + t.getMessage());
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