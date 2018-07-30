package com.ray.raylib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ray.lib.loading.LoadingViewManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(R.id.test_layout)
        val register = LoadingViewManager.register(view)
        register.switchLoading()
        view.postDelayed({
            register.switchEmpty()
            view.postDelayed({
                register.switchLoading()
                view.postDelayed({ register.switchSuccess() }, 2000)
            }, 1000)
        }, 2000)
    }
}
