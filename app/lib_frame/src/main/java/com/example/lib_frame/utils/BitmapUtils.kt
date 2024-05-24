package com.example.lib_frame.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Base64
import android.view.View
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer

object BitmapUtils {
    fun toBase64(drawable: Drawable): String? {
        return toBase64(toBitmap(drawable))
    }

    fun toBase64(bitmap: Bitmap?): String? {
        // 要返回的字符串
        var reslut: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
                baos.flush()
                baos.close()
                // 转换为字节数组
                val byteArray = baos.toByteArray()

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT)
            } else {
                return null
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                baos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return reslut
    }

    fun toBase64(filePath: String?, compressPercent: Int): String? {
        return toBase64(toBitmap(filePath, compressPercent))
    }

    fun toBitmap(filePath: String?, compressPercent: Int): Bitmap {
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inSampleSize = compressPercent //直接设置它的压缩率，表示1/2
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * 保存Bitmap为文件
     */
    fun saveBitmapFile(bitmap: Bitmap, path: String?) {
        val file = File(path) //将要保存图片的路径
        FileUtils.saveFile(file)
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Base64转Bitmap
     */
    fun toBitmap(base64Data: String?): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * 图片质量压缩
     *
     * @param many 百分比
     */
    fun compressBitmap(bitmap: Bitmap, many: Float): Bitmap? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, many.toInt() * 100, baos)
        val isBm = ByteArrayInputStream(baos.toByteArray())
        return BitmapFactory.decodeStream(isBm, null, null)
    }

    /**
     * 按比例裁剪图片
     *
     * @param bitmap 位图
     * @param wScale 裁剪宽 0~100%
     * @param hScale 裁剪高 0~100%
     * @return bitmap
     */
    fun cropBitmap(bitmap: Bitmap, wScale: Float, hScale: Float): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val wh = (w * wScale).toInt()
        val hw = (h * hScale).toInt()
        val retX = (w * (1 - wScale) / 2).toInt()
        val retY = (h * (1 - hScale) / 2).toInt()
        return Bitmap.createBitmap(bitmap, retX, retY, wh, hw, null, false)
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap 位图
     * @param w 新的宽度
     * @param h 新的高度
     * @return Bitmap
     */
    fun zoomBitmap(bitmap: Bitmap, w: Int, h: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val matrix = Matrix()
        val scaleWidht = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidht, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    fun zoomBitmap(bitmap: Bitmap, w: Float, h: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val matrix = Matrix()
        val scaleWidht = w / width
        val scaleHeight = h / height
        matrix.postScale(scaleWidht, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    /**
     * 将一个view转换为Bitmap
     */
    fun convertViewToBitmap(view: View): Bitmap {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }

    /**
     * 绘制水印图片
     *
     * @param src 原图
     * @param watermark 水印
     */
    private fun createWaterMaskBitmap(
        src: Bitmap?, watermark: Bitmap,
        paddingLeft: Int, paddingTop: Int
    ): Bitmap? {
        if (src == null) {
            return null
        }
        val width = src.width
        val height = src.height
        //创建一个bitmap
        val newb =
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        val canvas = Canvas(newb)
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0f, 0f, null)
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft.toFloat(), paddingTop.toFloat(), null)
        // 保存
        canvas.save()
        // 存储
        canvas.restore()
        return newb
    }

    fun decodeResource(context: Context, resId: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, resId)
    }

    fun decode(inputStream: InputStream?): Bitmap {
        return BitmapFactory.decodeStream(inputStream)
    }

    fun toBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return (drawable as BitmapDrawable).getBitmap()
        }
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun toBitmap(image: Image): Bitmap {
        return toBitmap(image.planes, image.width, image.height)
    }

    fun toBitmap(planes: Array<Image.Plane>, width: Int, height: Int): Bitmap {
        val yBuffer: ByteBuffer = planes[0].getBuffer()
        val uBuffer: ByteBuffer = planes[1].getBuffer()
        val vBuffer: ByteBuffer = planes[2].getBuffer()
        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()
        val nv21 = ByteArray(ySize + uSize + vSize)
        //U and V are swapped
        yBuffer[nv21, 0, ySize]
        vBuffer[nv21, ySize, vSize]
        uBuffer[nv21, ySize + vSize, uSize]
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun rotateBitmap(origin: Bitmap?, rotation: Float): Bitmap? {
        if (origin == null) {
            return null
        }
        val width = origin.width
        val height = origin.height
        val matrix = Matrix()
        matrix.setRotate(rotation)
        // 围绕原地进行旋转
        val newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        if (newBM == origin) {
            return newBM
        }
        origin.recycle()
        return newBM
    }

    fun scaleBitmap(bitmap: Bitmap, w: Float, h: Float): Bitmap? {
        val width = bitmap.width.toFloat()
        val height = bitmap.height.toFloat()
        val x: Float
        val y: Float
        val scaleWidth: Float
        var scaleHeight = height
        val newbmp: Bitmap
        if (w > h) { //比例宽度大于高度的情况
            val scale = w / h
            val tempH = width / scale
            if (height > tempH) { //
                x = 0f
                y = (height - tempH) / 2
                scaleWidth = width
                scaleHeight = tempH
            } else {
                scaleWidth = height * scale
                x = (width - scaleWidth) / 2
                y = 0f
            }
        } else if (w < h) { //比例宽度小于高度的情况
            val scale = h / w
            val tempW = height / scale
            if (width > tempW) {
                y = 0f
                x = (width - tempW) / 2
                scaleWidth = tempW
                scaleHeight = height
            } else {
                scaleHeight = width * scale
                y = (height - scaleHeight) / 2
                x = 0f
                scaleWidth = width
            }
        } else { //比例宽高相等的情况
            if (width > height) {
                x = (width - height) / 2
                y = 0f
                scaleHeight = height
                scaleWidth = height
            } else {
                y = (height - width) / 2
                x = 0f
                scaleHeight = width
                scaleWidth = width
            }
        }
        newbmp = try {
            Bitmap.createBitmap(
                bitmap, x.toInt(), y.toInt(), scaleWidth.toInt(), scaleHeight.toInt(), null,
                false
            ) // createBitmap()方法中定义的参数x+width要小于或等于bitmap.getWidth()，y+height要小于或等于bitmap.getHeight()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return newbmp
    }
}
