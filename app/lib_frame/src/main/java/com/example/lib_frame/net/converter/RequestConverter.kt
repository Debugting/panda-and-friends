package com.example.lib_frame.net.converter

import com.google.gson.TypeAdapter
import okhttp3.RequestBody
import retrofit2.Converter

class RequestConverter(private val adapter: TypeAdapter<out Any>?) : Converter<RequestBody, Any> {

    override fun convert(value: RequestBody): Any? {
        TODO("Not yet implemented")
    }
}