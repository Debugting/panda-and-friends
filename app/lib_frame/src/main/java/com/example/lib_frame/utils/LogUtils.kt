package com.example.lib_frame.utils

import com.orhanobut.logger.Logger

object LogUtils {
    fun d(tag: String, msg: CharSequence) {
        Logger.d(msg.toString(), tag)
    }

    fun i(tag: String, msg: CharSequence) {
        Logger.i(msg.toString(), tag)
    }

    fun e(tag: String, msg: CharSequence) {
        Logger.e(msg.toString(), tag)
    }

    fun w(tag: String, msg: CharSequence) {
        Logger.w(msg.toString(), tag)
    }
}
