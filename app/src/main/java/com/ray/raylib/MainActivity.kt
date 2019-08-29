package com.ray.raylib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.ray.lib.loading.LoadingViewManager
import com.ray.retrofitnetwork.HttpRequest

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
        val button = findViewById<Button>(R.id.btn_retrofit)
        button.setOnClickListener {
            button.text = "请求中..."
            HttpRequest.getInstance().requestPlainText(object: HttpRequest.HttpCallback {
                override fun onSuccess(response: String?) {
                    button.text = "请求成功 : $response"
                }

                override fun onError(reason: String?) {
                    button.text = "请求失败 : $reason"
                }
            })
        }
    }
}
