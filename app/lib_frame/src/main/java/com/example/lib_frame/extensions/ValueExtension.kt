package com.example.lib_frame.extensions

import com.example.lib_frame.utils.TimeUtils


fun String.toTime(): Long {
    return TimeUtils.parseStringToDateTime(this)
}