package com.example.lib_frame.net

import com.darkhorse.httphelper.converter.BaseConverterFactory
import com.example.lib_frame.net.interceptor.MulUrlInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpHelper {

    const val MUL_URL = "base_url:"

    private val mOkHttpBuilder by lazy {
        OkHttpClient.Builder()
    }
    private val mRetrofitBuilder by lazy {
        Retrofit.Builder()
    }
    private lateinit var mRetrofit: Retrofit

    fun init() {
        mOkHttpBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        mRetrofitBuilder.client(
            mOkHttpBuilder.build()
        )
        mRetrofitBuilder.addConverterFactory(GsonConverterFactory.create())
        mRetrofit = mRetrofitBuilder.build()
    }

    fun baseUrl(baseUrl: String): HttpHelper {
        mRetrofitBuilder.baseUrl(baseUrl)
        return this
    }

    fun mulBaseUrl(urlMap: MutableMap<String, String>): HttpHelper {
        mOkHttpBuilder.addInterceptor(MulUrlInterceptor(urlMap))
        return this
    }

    fun <T> getService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

}
