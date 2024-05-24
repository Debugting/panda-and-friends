package com.example.application

import com.bytedance.tools.codelocator.response.BaseResponse
import com.example.lib_frame.manager.IUploadService
import com.example.lib_frame.net.HttpHelper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

object UploadManager : IUploadService {

    private val uploadService = HttpHelper.getService(UploadService::class.java)

    override suspend fun uploadImage(path: String): String? =
        with(File(path)) {
            if (exists()) {
                val response = uploadService.uploadImage(
                    MultipartBody.Part.createFormData(
                        "file",
                        name,
                        asRequestBody("image/jpg".toMediaType())
                    )
                )
                if (response.code == 0) {
                    response.data
                } else {
                    null
                }
            } else {
                path
            }
        }

    override suspend fun uploadAudio(path: String): String? =
        with(File(path)) {
            if (exists()) {
                val response = uploadService.uploadImage(
                    MultipartBody.Part.createFormData(
                        "file",
                        name,
                        asRequestBody("audio/mp3".toMediaType())
                    )
                )
                if (response.code == 0) {
                    response.data
                } else {
                    null
                }
            } else {
                path
            }
        }

    override suspend fun uploadVideo(path: String): String? =
        with(File(path)) {
            if (exists()) {
                val response = uploadService.uploadImage(
                    MultipartBody.Part.createFormData(
                        "file",
                        name,
                        asRequestBody("video/mp4".toMediaType())
                    )
                )
                if (response.code == 0) {
                    response.data
                } else {
                    null
                }
            } else {
                path
            }
        }
}

interface UploadService {
    /**
     * 上传图片文件
     */
    @Multipart
    @POST("fileImage/upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part): BaseResponse<String>

    /**
     * 上传音频文件
     */
    @Multipart
    @POST("fileAudio/upload")
    fun uploadAudio(@Part file: MultipartBody.Part): BaseResponse<String>

    /**
     * 上传视频文件
     */
    @Multipart
    @POST("fileVideo/upload")
    fun uploadVideo(@Part file: MultipartBody.Part): BaseResponse<String>

}