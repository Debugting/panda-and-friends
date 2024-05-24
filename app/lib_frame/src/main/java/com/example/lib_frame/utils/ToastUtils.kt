package com.example.lib_frame.utils

import android.app.Application
import android.widget.Toast
import androidx.annotation.MainThread
import es.dmoral.toasty.Toasty

object ToastUtils {

    private lateinit var application: Application

    fun init(application: Application) {
        ToastUtils.application = application
    }

    @MainThread
    fun toast(message: CharSequence) {
        LogUtils.i("Toast", message)
        Toasty.normal(application, message, Toast.LENGTH_SHORT)
            .show()
    }

    @MainThread
    fun error(message: CharSequence = "操作失败") {
        LogUtils.e("Toast", message)
        Toasty.error(application, message, Toast.LENGTH_SHORT, true)
            .show()
    }

    @MainThread
    fun success(message: CharSequence = "操作成功") {
        LogUtils.i("Toast", message)
        Toasty.success(application, message, Toast.LENGTH_SHORT, true)
            .show()
    }

    @MainThread
    fun info(message: CharSequence = "请填写完整的信息") {
        LogUtils.i("Toast", message)
        Toasty.info(application, message, Toast.LENGTH_SHORT, true)
            .show()
    }

    @MainThread
    fun warning(message: CharSequence) {
        LogUtils.w("Toast", message)
        Toasty.warning(application, message, Toast.LENGTH_SHORT, true)
            .show()
    }
}
