package com.yashodainfotech.queuingapp;

import android.app.Application;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;

public class Myapplication extends Application {
    private static Myapplication sInstance;
    private RequestQueue mRequestQueue;

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        sInstance = this;
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
    public static synchronized Myapplication getInstance() {
        return sInstance;
    }
    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
}
