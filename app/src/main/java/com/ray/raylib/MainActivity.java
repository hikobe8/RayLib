package com.ray.raylib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup view = findViewById(android.R.id.content);
        ProgressBar progressBar = new ProgressBar(this);
        view.addView(progressBar);
    }
}
