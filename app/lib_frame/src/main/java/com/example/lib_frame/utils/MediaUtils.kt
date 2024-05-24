package com.example.lib_frame.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.lib_frame.manager.AppManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.yalantis.ucrop.UCrop

object MediaUtils {

    fun choiceImage(
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>,
        ratioX: Int = 3, ratioY: Int = 4, maxNum: Int = 1,
    ) {
        PictureSelector.create(AppManager.activity)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setCropEngine { fragment, srcUri, destinationUri, dataSource, requestCode ->
                val uCrop: UCrop = UCrop.of(srcUri, destinationUri, dataSource)
                uCrop.withAspectRatio(ratioX.toFloat(), ratioY.toFloat())
                uCrop.start(AppManager.activity, fragment, requestCode)
            }.setSelectionMode(SelectModeConfig.MULTIPLE)
            .setMaxSelectNum(maxNum)
            .forResult(onResultCallbackListener)
    }

    fun choiceAudio(onResultCallbackListener: OnResultCallbackListener<LocalMedia>) {
        PictureSelector.create(AppManager.activity)
            .openGallery(SelectMimeType.ofAudio())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .forResult(onResultCallbackListener)
    }

    fun choiceVideo(onResultCallbackListener: OnResultCallbackListener<LocalMedia>) {
        PictureSelector.create(AppManager.activity)
            .openGallery(SelectMimeType.ofVideo())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .forResult(onResultCallbackListener)
    }

    private class GlideEngine private constructor() : ImageEngine {
        /**
         * 加载图片
         */
        override fun loadImage(context: Context, url: String, imageView: ImageView) {
            Glide.with(context).load(url).into(imageView)
        }

        override fun loadImage(
            context: Context,
            imageView: ImageView,
            url: String,
            maxWidth: Int,
            maxHeight: Int,
        ) {
            Glide.with(context).load(url).into(imageView)
        }

        override fun loadAlbumCover(context: Context, url: String, imageView: ImageView) {
            Glide.with(context).load(url).into(imageView)
        }

        /**
         * 加载图片列表图片
         *
         * @param context 上下文
         * @param url 图片路径
         * @param imageView 承载图片ImageView
         */
        override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
            Glide.with(context).load(url).override(200, 200).centerCrop().into(imageView)
        }

        override fun pauseRequests(context: Context) {}
        override fun resumeRequests(context: Context) {}

        private object InstanceHolder {
            val instance = GlideEngine()
        }

        companion object {
            fun createGlideEngine(): GlideEngine {
                return InstanceHolder.instance
            }
        }
    }
}
