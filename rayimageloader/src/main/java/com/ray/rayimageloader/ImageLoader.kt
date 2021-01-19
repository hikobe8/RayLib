package com.ray.rayimageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/**
 * Author : Ray
 * Time : 2020/8/31 6:02 PM
 * Description :
 */
class ImageLoader {

    private val mExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    /**
     * 默认使用双缓存
     */
    var imageCache:ImageCache = DoubleCache()

    fun displayImage(url: String, imageView: ImageView) {
        val cache = imageCache.get(url)
        if (cache != null) {
            imageView.setImageBitmap(cache)
            return
        }
        imageView.tag = url
        mExecutor.submit(Runnable {
            var bitmap = imageCache.get(url)
            if (bitmap == null)
                bitmap = downloadImage(url) ?: return@Runnable
            if (imageView.tag == url) {
                imageView.post {
                    imageView.setImageBitmap(bitmap)
                }
            }
            imageCache.put(url, bitmap)
        })
    }

    private fun downloadImage(url: String): Bitmap? {
        val conn = URL(url).openConnection() as HttpURLConnection
        val bitmap = BitmapFactory.decodeStream(conn.inputStream)
        conn.disconnect()
        return bitmap
    }

}

interface ImageCache {
    fun get(url: String): Bitmap?
    fun put(url: String, bitmap: Bitmap)
}

class MemoryCache : ImageCache {

    private lateinit var mImageCache: LruCache<String, Bitmap>

    init {
        initImageCache()
    }

    private fun initImageCache() {
        val totalMemory = Runtime.getRuntime().totalMemory()
        mImageCache = object : LruCache<String, Bitmap>((totalMemory * .4f).toInt()) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                return value?.run {
                    (rowBytes * height / 1024f).toInt()
                } ?: 0
            }
        }
    }

    override fun get(url: String): Bitmap? = mImageCache.get(url)

    override fun put(url: String, bitmap: Bitmap) {
        mImageCache.put(url, bitmap)
    }

}

class DiskCache : ImageCache {

    override fun get(url: String) = BitmapFactory.decodeFile(RayApp.APP.cacheDir.absolutePath + File.separator + url.hashCode())

    override fun put(url: String, bitmap: Bitmap) {
        File(RayApp.APP.cacheDir, url.hashCode().toString()).apply {
            if (!exists()) {
                createNewFile()
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(this))
        }
    }
}

class DoubleCache : ImageCache {

    private val memoryCache = MemoryCache()

    private val diskCache = DiskCache()

    override fun get(url: String): Bitmap? {
        var bitmap = memoryCache.get(url)
        if (bitmap == null) {
            bitmap = diskCache.get(url)
        }
        return bitmap
    }

    override fun put(url: String, bitmap: Bitmap) {
        memoryCache.put(url, bitmap)
        diskCache.put(url, bitmap)
    }

}



