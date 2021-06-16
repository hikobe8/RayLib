package com.ray.rayrouter;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ray.router.annotations.Destination;

@Destination(
        url = "router://test",
        description = "测试页面"
)
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String msg = getIntent().getExtras().getString("msg");
        TextView text = findViewById(R.id.text);
        text.setText("msg = " + msg);

    }
}
