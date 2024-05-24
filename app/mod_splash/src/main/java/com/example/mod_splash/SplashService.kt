package com.example.mod_splash

import com.example.lib_frame.net.HttpHelper
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SplashService {

    companion object {
        const val KEY = "bing"
        const val URL = "https://cn.bing.com"
        const val HEADER: String = HttpHelper.MUL_URL + KEY

        fun g(): SplashService {
            return HttpHelper.getService(SplashService::class.java)
        }
    }

    @Headers(HEADER)
    @GET("/HPImageArchive.aspx")
    suspend fun getSplashList(
        @Query("format") format: String?,
        @Query("n") n: Int
    ): SplashRsp

}
