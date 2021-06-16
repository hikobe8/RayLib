package com.ray.biz_reading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ray.router.annotations.Destination

@Destination(
    url = "router://reading",
    description = "阅读页"
)
class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)
    }
}