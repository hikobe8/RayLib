package com.ray.raylib

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ray.lib.device.brightness.BrightnessUtil
import com.ray.lib.loading.LoadingViewManager
import com.ray.rayimageloader.ImageLoader
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
            HttpRequest.getInstance().requestPlainText(object : HttpRequest.HttpCallback {
                override fun onSuccess(response: String?) {
                    button.text = "请求成功 : $response"
                }

                override fun onError(reason: String?) {
                    button.text = "请求失败 : $reason"
                }
            })
        }
        ImageLoader.displayImage("https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo_2x.jpg!0x0.webp",
                findViewById(R.id.iv))
    }

    /***
     *  设置亮度在五秒之内从0到100
     */
    fun changeBrightness(){

    }

    fun brightnessTest(view: View) {
        BrightnessUtil.allowModifySettings(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        BrightnessUtil.onActivityResult(this, requestCode, resultCode, data)
    }

}



