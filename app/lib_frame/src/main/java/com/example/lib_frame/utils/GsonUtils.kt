package com.example.lib_frame.utils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object GsonUtils {

    val gson by lazy {
        Gson()
    }

    inline fun <reified T> toJson(obj: T): String {
        return gson.toJson(obj)
    }

    inline fun <reified T> fromJson(json: String): T {
        val typeToken = object : TypeToken<T>() {}.type
        return gson.fromJson(json, typeToken)
    }

    inline fun getAdapter(type: Type?): TypeAdapter<out Any>? =
        gson.getAdapter(TypeToken.get(type))
}
