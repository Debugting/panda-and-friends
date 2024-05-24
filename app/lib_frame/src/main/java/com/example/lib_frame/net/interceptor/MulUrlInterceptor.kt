package com.example.lib_frame.net.interceptor

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class MulUrlInterceptor(private val mHashMap: MutableMap<String, String>) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val headers = request.headers("base_url")
        if (headers.isNotEmpty()) {
            builder.removeHeader("base_url")
            val key = headers[0]
            val value = mHashMap[key]
            if (value != null) {
                val newUrl = value.toHttpUrlOrNull()
                val oldUrl = request.url
                if (newUrl != null) {
                    val fullUrl = oldUrl
                        .newBuilder()
                        .scheme(newUrl.scheme)
                        .host(newUrl.host)
                        .port(newUrl.port)
                        .build()
                    return chain.proceed(builder.url(fullUrl).build())
                }
            }
        }
        return chain.proceed(builder.build())
    }
}
