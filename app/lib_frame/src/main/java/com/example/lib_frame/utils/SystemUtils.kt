package com.example.lib_frame.utils

import android.content.Intent
import android.net.Uri
import com.example.lib_frame.manager.AppManager

object SystemUtils {
    fun getSystemService(name: String?): Any {
        return AppManager.app.getSystemService(name!!)
    }

    fun callPhone(phone: String) {
        val intent = Intent(
            Intent.ACTION_CALL, Uri.parse("tel:$phone")
        )
        AppManager.activity.startActivity(intent)
    }
}