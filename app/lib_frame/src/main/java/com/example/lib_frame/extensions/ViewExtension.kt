package com.example.lib_frame.extensions

import android.graphics.Outline
import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.lib_frame.R
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.utils.GlideUtils

fun ImageView.load(url: String?) {
    if (url.isNullOrBlank()) {
        setImageResource(R.mipmap.ic_pic_load_error)
    } else {
        GlideUtils.load(this, url)
    }
}

fun ImageView.load(resId: Int) {
    GlideUtils.load(this, resId)
}

fun View.expandClickArea(extraSpace: Int) {
    val parent = parent as? View
    parent?.post {
        val rect = Rect()
        getHitRect(rect)
        rect.top -= extraSpace
        rect.bottom += extraSpace
        rect.left -= extraSpace
        rect.right += extraSpace
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}

fun View.setCornerRadius(radius: Float) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                0,
                0,
                view.width,
                view.height,
                radius
            )
        }
    }
    clipToOutline = true
}

