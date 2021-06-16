package com.ray.router_runtime

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

object RayRouter {

    private const val TAG = "RayRouter"
    private const val CLASS_NAME = "com.ray.router.mapping.generated.RouterMapping"

    private val mapping = HashMap<String, String>()

    fun init() {
        try {
            val mappingClass = Class.forName(CLASS_NAME)
            val method = mappingClass.getMethod("get")
            val allMapping = method.invoke(null) as Map<String, String>
            if (allMapping.isNotEmpty()) {
                mapping.putAll(allMapping)
                Log.d(TAG, "init: get all mapping:")
                allMapping.onEach {
                    Log.d(TAG, "${it.key} : ${it.value}")
                }
            }
        } catch (t: Throwable) {
            Log.e(TAG, t.message!!)
        }
    }

    fun go(context: Context, url: String) {
        val destUri = Uri.parse(url)
        val destScheme = destUri.scheme
        val destHost = destUri.host
        val destPath = destUri.path
        var targetActivityClass = ""
        mapping.onEach {
            val uri = Uri.parse(it.key)
            val scheme = uri.scheme
            val host = uri.host
            val path = uri.path
            if (destScheme == scheme && destHost == host && destPath == path) {
                targetActivityClass = it.value
                return@onEach
            }
        }
        if (targetActivityClass.isEmpty()) {
            Log.e(TAG, "go: error url = $url")
            return
        }
        //封装参数
        val queryParameterNames = destUri.queryParameterNames
        val bundle = Bundle()
        if (queryParameterNames.isNotEmpty()) {
            queryParameterNames.forEach {
                bundle.putString(it, destUri.getQueryParameter(it))
            }
        }
        try {
            val targetActivity = Class.forName(targetActivityClass)
            val intent = Intent(context, targetActivity)
            intent.putExtras(bundle)
            context.startActivity(intent)
        } catch (e: Throwable) {
            Log.e(TAG, "error while start Activity = $targetActivityClass, e = $e")
        }
    }

}