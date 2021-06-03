package com.ray.rayrouterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ray.router.annotations.Destination;

@Destination(
        url = "router://home",
        description = "应用主页"
)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}