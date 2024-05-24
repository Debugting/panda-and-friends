package com.example.lib_frame.net.bean

data class BaseResponse<T>(
    var code: Int,
    var message: String,
    var data: T,
)