package com.example.lib_frame.manager

import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

object AssetManagerHelper {
    private val mAssetManager: AssetManager = AppManager.app.assets

    fun list(dir: String): Array<String>? {
        try {
            return mAssetManager.list(dir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    val assetPath: String
        get() = "file:///android_asset/"

    fun open(fileName: String): InputStream? {
        try {
            return mAssetManager.open(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getJson(fileName: String): String {
        var json = ""
        try {
            val inputStream = mAssetManager.open(fileName)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            json = String(buffer, StandardCharsets.UTF_8)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println(json)
        return json
    }
}
