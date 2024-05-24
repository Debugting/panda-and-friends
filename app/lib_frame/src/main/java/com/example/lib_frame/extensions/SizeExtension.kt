package com.example.lib_frame.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

fun Float.dp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale).roundToInt()
}

fun Int.dp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale).roundToInt()
}