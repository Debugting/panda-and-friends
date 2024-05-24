package com.example.lib_frame.extensions

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.lib_frame.manager.AppManager

fun getColor(@ColorRes id: Int) = ContextCompat.getColor(AppManager.app, id)

fun getString(@StringRes id: Int) = AppManager.app.getString(id)

fun getDrawable(@StringRes id: Int) = AppManager.app.getDrawable(id)

