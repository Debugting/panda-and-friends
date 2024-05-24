package com.example.mod_social.slike

import android.graphics.Bitmap
import android.graphics.Paint
import com.example.lib_frame.utils.DisplayUtils

class TextAnimationFrame(duration: Long) : BaseAnimationFrame(duration) {
    private var lastClickTimeMillis: Long = 0
    private var likeCount = 0
    override fun prepare(x: Int, y: Int, bitmapProvider: BitmapProvider.Provider) {
        reset()
        setStartPoint(x, y)
        calculateCombo()
        elements = generatedElements(x, y, bitmapProvider)
    }

    private fun calculateCombo() {
        if (System.currentTimeMillis() - lastClickTimeMillis < duration) {
            likeCount++
        } else {
            likeCount = 1
        }
        lastClickTimeMillis = System.currentTimeMillis()
    }

    override fun getType(): Int {
        return TYPE
    }

    override fun onlyOne(): Boolean {
        return true
    }

    /**
     * 生成N个Element
     */
    override fun generatedElements(
        x: Int,
        y: Int,
        bitmapProvider: BitmapProvider.Provider,
    ): List<Element> {
        val elements: MutableList<Element> = ArrayList()
        var count = likeCount
        var offset_x = 0
        while (count > 0) {
            val number = count % 10
            val bitmap = bitmapProvider.getNumberBitmap(number)
            offset_x += bitmap.width
            val element: Element = TextElement(bitmap, x - offset_x)
            elements.add(element)
            count = count / 10
        }
        var level = likeCount / 10
        if (level > 2) {
            level = 2
        }
        elements.add(TextElement(bitmapProvider.getLevelBitmap(level), x))
        return elements
    }

    class TextElement(private val bitmap: Bitmap, private val x: Int) : Element {
        private var y = 0
        override fun getX(): Int {
            return x
        }

        override fun getY(): Int {
            return y
        }

        override fun getBitmap(): Bitmap {
            return bitmap
        }

        override fun evaluate(start_x: Int, start_y: Int, time: Double) {
            y = start_y - DisplayUtils.dp2px(90f)
        }

        override fun getPaint(): Paint = Paint()
    }

    companion object {
        const val TYPE = 2
    }
}
