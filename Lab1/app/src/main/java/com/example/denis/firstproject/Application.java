package com.example.denis.firstproject;

import android.app.ActivityManager;
import android.util.Log;

public class Application extends android.app.Application{
    public Application() {
        super();
    }

    final String TAG_APP = "application";

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG_APP, "App created");
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        Log.d(TAG_APP, "Low memory");
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Log.d(TAG_APP, "App terminated");
    }
}
