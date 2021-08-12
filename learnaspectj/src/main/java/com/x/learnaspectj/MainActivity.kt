package com.x.learnaspectj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<TextView>(R.id.text).setOnClickListener {
//             Animal().fly()
//        }
    }

    fun click(view: View) {
             Animal().fly()
    }
}