package com.ray.rayimageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/**
 * Author : Ray
 * Time : 2020/8/31 6:02 PM
 * Description :
 */
class ImageLoader {

    private lateinit var mBitmapCache: LruCache<String, Bitmap>

    private val mExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    init {
        initImageCache()
    }

    private fun initImageCache() {
        val totalMemory = Runtime.getRuntime().totalMemory()
        mBitmapCache = object : LruCache<String, Bitmap>((totalMemory * .4f).toInt()) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                return value?.run {
                    (rowBytes * height / 1024f).toInt()
                } ?: 0
            }
        }
    }

    fun displayImage(url:String, imageView:ImageView) {
        imageView.tag = url
        mExecutor.submit(Runnable {
            val bitmap = downloadImage(url) ?: return@Runnable
            if (imageView.tag == url) {
                imageView.post {
                    imageView.setImageBitmap(bitmap)
                }
            }
            mBitmapCache.put(url, bitmap)
        })
    }

    fun downloadImage(url:String): Bitmap? {
        val conn = URL(url).openConnection() as HttpURLConnection
        val bitmap = BitmapFactory.decodeStream(conn.inputStream)
        conn.disconnect()
        return bitmap
    }

}