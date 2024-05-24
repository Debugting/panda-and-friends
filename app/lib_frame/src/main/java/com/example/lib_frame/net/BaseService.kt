package com.example.lib_frame.net

import com.example.lib_frame.net.bean.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface BaseService {

    @POST("{tb}/insert")
    suspend fun insert(
        @Path("tb") tb: String,
        @Body t: Any,
    ): BaseResponse<Long>

    @FormUrlEncoded
    @POST("{tb}/deleteByPrimaryKey")
    suspend fun delete(
        @Path("tb") tb: String,
        @Field("id") id: Long,
    ): BaseResponse<Long>

    @FormUrlEncoded
    @POST("{tb}/selectByCondition")
    suspend fun getAll(
        @Path("tb") tb: String,
        @FieldMap params: @JvmSuppressWildcards Map<String, Any>?,
    ): BaseResponse<List<Any>>

}