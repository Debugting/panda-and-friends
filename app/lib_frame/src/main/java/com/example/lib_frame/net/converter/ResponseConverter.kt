package com.example.lib_frame.net.converter

import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter

class ResponseConverter(private val adapter: TypeAdapter<out Any>?) : Converter<ResponseBody, Any> {

    override fun convert(value: ResponseBody): Any? {
        val originalBody = value.string()
        return adapter?.fromJson(originalBody)
    }
}