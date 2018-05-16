package com.ray.raylib;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ray.lib.loading.LoadingViewController;
import com.ray.lib.loading.LoadingViewManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.test_layout);
        final LoadingViewController register = LoadingViewManager.register(view);
        register.switchLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                register.switchEmpty();
            }
        }, 2000);
    }
}
