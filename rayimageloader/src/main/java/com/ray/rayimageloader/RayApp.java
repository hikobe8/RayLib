package com.ray.rayimageloader;

import android.app.Application;

public class RayApp extends Application {

    public static RayApp APP;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
    }
}
