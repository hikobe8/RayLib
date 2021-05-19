package com.ray.rayrouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ray.router.annotations.Destination

@Destination(
    url = "router://home",
    description = "应用主页"
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}