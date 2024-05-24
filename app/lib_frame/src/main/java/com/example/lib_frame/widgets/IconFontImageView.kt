package com.example.lib_frame.widgets

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.lib_frame.R
import com.example.lib_frame.extensions.setCornerRadius
import com.example.lib_frame.utils.GlideUtils
import java.io.File

class IconFontImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var drawable: Drawable? = null

    init {
        // 设置字体
        typeface = Typeface.createFromAsset(context.assets, "font/iconfont.ttf")
        context.obtainStyledAttributes(
            attrs,
            intArrayOf(
                android.R.attr.textColor,
                android.R.attr.textStyle,
                R.attr.radius,
                android.R.attr.src,
            )
        ).apply {
            var index = 0
            paint.color = getColor(index, Color.WHITE)
            paint.isFakeBoldText = getInt(++index, 0) == 1
            setCornerRadius(getDimension(++index, 0f))
            drawable = getDrawable(++index)
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawable.apply {
            if (this == null) {
                drawText(canvas)
            } else {
                setBounds(0, 0, width, height)
                draw(canvas)
            }
        }
    }

    private fun drawText(canvas: Canvas) {
        // 获取视图的宽度和高度
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()

        // 计算字体大小，确保图标填充整个视图
        val textSize =
            (width - paddingStart - paddingRight).coerceAtMost(height - paddingTop - paddingBottom)

        // 设置字体大小和颜色
        paint.textSize = textSize

        // 获取绘制文本的基线
        val baseline = (height - paint.ascent() - paint.descent()) / 2

        // 设置文本在视图中居中
        paint.textAlign = Paint.Align.CENTER

        // 绘制文本
        canvas.drawText(text.toString(), width / 2, baseline, paint)
    }

    fun load(url: String?) {
        if (url == null) {
            drawable = null
            return
        }
        File(url).apply {
            if (exists()) {
                drawable = BitmapDrawable(context.resources, BitmapFactory.decodeFile(absolutePath))
            } else {
                GlideUtils.load(url, object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        drawable = null
                        invalidate()
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        drawable = resource
                        invalidate()
                        return true
                    }
                })
            }
        }
    }
}