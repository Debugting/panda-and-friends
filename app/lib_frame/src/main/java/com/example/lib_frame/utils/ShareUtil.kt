package com.example.lib_frame.utils

import android.content.Intent
import android.net.Uri
import com.example.lib_frame.manager.AppManager
import java.io.File

object ShareUtil {
    /**
     * 分享文字
     */
    fun shareText(text: String?) {
        val intent = Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.setType("text/plain")
        val chooserIntent = Intent.createChooser(intent, "分享到:")
        AppManager.activity.startActivity(chooserIntent)
    }

    /**
     * 分享图片
     */
    fun shareImg(file: File) {
        if (file.exists()) {
            val uri = Uri.fromFile(file)
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            val chooserIntent = Intent.createChooser(intent, "分享到:")
            AppManager.activity.startActivity(chooserIntent)
        } else {
            ToastUtils.toast("文件不存在")
        }
    }

    /**
     * 分享音乐
     */
    fun shareAudio(file: File) {
        if (file.exists()) {
            val uri = Uri.fromFile(file)
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.setType("audio/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            val chooserIntent = Intent.createChooser(intent, "分享到:")
            AppManager.activity.startActivity(chooserIntent)
        } else {
            ToastUtils.toast("文件不存在")
        }
    }

    /**
     * 分享视频
     */
    fun shareVideo(file: File) {
        if (file.exists()) {
            val uri = Uri.fromFile(file)
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.setType("video/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            val chooserIntent = Intent.createChooser(intent, "分享到:")
            AppManager.activity.startActivity(chooserIntent)
        } else {
            ToastUtils.toast("文件不存在")
        }
    }
}