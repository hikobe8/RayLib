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
        final View view = findViewById(R.id.test_layout);
        final LoadingViewController register = LoadingViewManager.register(view);
        register.switchLoading();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                register.switchEmpty();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        register.switchLoading();
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                register.switchError();
                            }
                        }, 2000);
                    }
                }, 1000);
            }
        }, 2000);
    }
}
