package com.example.lib_frame.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import com.example.lib_frame.manager.AppManager
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.StringReader
import java.net.URLConnection

object FileUtils {

    /**
     * 获取存储位置
     */
    val cacheDir: String
        get() = AppManager.app.cacheDir?.absolutePath + File.separator

    /**
     * 获取图片存储文件夹位置
     */
    val imgDir: String
        get() = AppManager.app.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath + File.separator

    /**
     * 获取图片存储位置
     */
    val imgTempPath: String
        get() = imgDir + "img_" + TimeUtils.currentTime() + ".jpg"

    /**
     * 获取视频存储文件夹位置
     */
    val videoDir: String
        get() = AppManager.app.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
            ?.getAbsolutePath() + File.separator

    /**
     * 获取视频存储位置
     */
    val videoTempPath: String
        get() = videoDir + "video_" + TimeUtils.currentTime() + ".mp4"

    /**
     * 兼容android 10
     * 更新图库
     */
    fun updatePhotoAlbum(filePath: String) {
        updatePhotoAlbum(File(filePath))
    }

    /**
     * 兼容android 10
     * 更新图库
     */
    fun updatePhotoAlbum(file: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
            values.put(MediaStore.MediaColumns.MIME_TYPE, getMimeType(file))
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            val contentResolver: ContentResolver = AppManager.app.getContentResolver()
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                ?: return
            try {
                val out = contentResolver.openOutputStream(uri)
                val fis = FileInputStream(file)
                FileUtils.copy(fis, out!!)
                fis.close()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            MediaScannerConnection.scanFile(
                AppManager.app.getApplicationContext(),
                arrayOf<String>(file.absolutePath),
                arrayOf<String>("image/jpeg")
            ) { path, uri -> }
        }
    }

    fun getMimeType(file: File): String {
        val fileNameMap = URLConnection.getFileNameMap()
        return fileNameMap.getContentTypeFor(file.name)
    }

    /**
     * 保存文件
     */
    fun saveFile(path: String?, inputStream: InputStream): Boolean {
        return saveFile(File(path), inputStream)
    }

    /**
     * 保存文件
     */
    fun saveFile(file: File, inputStream: InputStream): Boolean {
        try {
            if (saveFile(file)) {
                // 1.建立通道对象
                val fos = FileOutputStream(file)
                val size = 4096
                // 2.定义存储空间
                val buffer = ByteArray(size)
                // 3.开始读文件
                var lenght = 0
                // 循环从输入流读取buffer字节
                while (inputStream.read(buffer).also { lenght = it } != -1) {
                    // 将Buffer中的数据写到outputStream对象中
                    fos.write(buffer, 0, lenght)
                }
                fos.flush() // 刷新缓冲区
                // 4.关闭流
                fos.close()
                inputStream.close()
                return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res      原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    fun string2File(res: String?, filePath: String?): Boolean {
        val flag = true
        var bufferedReader: BufferedReader? = null
        val bufferedWriter: BufferedWriter
        try {
            val distFile = File(filePath)
            if (!distFile.parentFile.exists()) distFile.parentFile.mkdirs()
            bufferedReader = BufferedReader(StringReader(res))
            bufferedWriter = BufferedWriter(FileWriter(distFile))
            var len: String
            while (bufferedReader.readLine().also { len = it } != null) {
                // 这里因为 单引号 ' 有些问题, 做了下面特殊处理
                len = len.replace("&apos;", "'")
                bufferedWriter.write(len)
                bufferedWriter.newLine()
            }
            bufferedWriter.flush()
            bufferedReader.close()
            bufferedWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return flag
    }

    /**
     * 保存文件
     */
    fun saveFile(file: File): Boolean {
        if (isFile(file)) {
            return true
        }
        try {
            return saveDir(file.parentFile) && file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 保存文件夹
     */
    fun saveDir(file: File): Boolean {
        return if (isDir(file)) {
            true
        } else saveDir(file.parentFile) && file.mkdir()
    }

    fun isExists(path: String?): Boolean {
        return path != null && File(path).exists()
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap Bitmap
     */
    fun saveBitmap(bitmap: Bitmap, path: String?): Boolean {
        val file = File(path)
        saveFile(file)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            ToastUtils.error("图片保存失败！")
            return false
        }
        return true
    }

    fun isExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    fun isFile(file: File): Boolean {
        return isExists(file) && file.isFile
    }

    fun isDir(file: File): Boolean {
        return isExists(file) && file.isDirectory
    }
}
