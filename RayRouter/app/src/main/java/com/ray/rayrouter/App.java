package com.ray.rayrouter;

import android.app.Application;

import com.ray.router_runtime.RayRouter;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RayRouter.INSTANCE.init();
    }
}
