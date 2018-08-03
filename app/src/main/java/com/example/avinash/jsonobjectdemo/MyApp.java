package com.example.avinash.jsonobjectdemo;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;


public class MyApp extends Application {

    /// application context variable
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // Set the application context
        context = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    /// Get application context
    public static Context sharedContext() {
        return context;
    }

}
