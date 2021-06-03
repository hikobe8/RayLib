package com.ray.rayrouterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ray.router.annotations.Destination

@Destination(
        url = "router://kotlin",
        description = "kotlin"
)
class KtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt)
    }
}