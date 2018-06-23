package com.wzbc.myapplication.volley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by WZBC on 2018/6/3.
 */

public class MyApplication extends Application{
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getRequestQueue(){return requestQueue;}
}
