package com.example.lib_frame.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.lib_frame.R
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.manager.ConfigManager
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.concurrent.ExecutionException

object GlideUtils {
    private var mWatchUrl = ""

    fun init(watchUrl: String) {
        mWatchUrl = watchUrl
    }

    private fun checkHttp(url: String): String {
        if (ConfigManager.isOpenNet) {
            if (url.contains("http://") || url.contains("https://") || FileUtils.isExists(url)) {
                return url
            }
            return mWatchUrl + url
        }
        return url
    }

    fun load(iv: ImageView, id: Int) {
        Glide.with(iv)
            .load(id)
            .into(iv)
        iv.imageTintMode = null
    }

    fun load(
        iv: ImageView,
        url: String,
        placeholder: Int? = null,
        error: Int = R.mipmap.ic_pic_load_error
    ) {
        val readUrl = checkHttp(url)
        try {
            Glide.with(iv)
                .load(readUrl).apply {
                    placeholder?.apply {
                        placeholder(this)
                    }
                    error(error)
                    into(iv)
                }
        } catch (e: Exception) {
            e.message?.apply {
                ToastUtils.error(this)
            }
        }
        iv.imageTintMode = null
    }

    fun load(iv: ImageView, url: String, transformation: BitmapTransformation) {
        Glide.with(iv)
            .load(checkHttp(url))
            .apply(RequestOptions.bitmapTransform(transformation))
            .into(iv)
    }

    fun load(iv: ImageView, url: String, requestListener: RequestListener<Drawable>) {
        Glide.with(iv)
            .load(checkHttp(url))
            .addListener(requestListener)
            .into(iv)
        iv.imageTintMode = null
    }

    fun load(url: String, requestListener: RequestListener<Drawable>) {
        Glide.with(AppManager.app)
            .load(checkHttp(url))
            .listener(requestListener)
            .preload()
    }

    fun download(url: String, requestListener: RequestListener<File>) {
        Glide.with(AppManager.app)
            .downloadOnly()
            .load(checkHttp(url))
            .listener(requestListener)
            .preload()
    }

    fun download(url: String, path: String) {
        val target: FutureTarget<File> = Glide.with(AppManager.app)
            .asFile()
            .load(checkHttp(url))
            .submit()
        Thread {
            try {
                val source: File = target.get()
                val inputStream = FileInputStream(source)
                val file = File(path)
                FileUtils.saveFile(file, inputStream)
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }.start()
    }
}
