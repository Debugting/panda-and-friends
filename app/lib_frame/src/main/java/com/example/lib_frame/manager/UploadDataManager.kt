package com.example.lib_frame.manager

object UploadDataManager {

    private lateinit var uploadService: IUploadService

    fun init(uploadService: IUploadService) {
        this.uploadService = uploadService
    }

    suspend fun uploadImage(path: String): String? =
        uploadService.uploadImage(path)

    suspend fun uploadAudio(path: String): String? =
        uploadService.uploadAudio(path)

    suspend fun uploadVideo(path: String): String? =
        uploadService.uploadVideo(path)

}

interface IUploadService {

    /**
     * 上传图片
     */
    suspend fun uploadImage(path: String): String?

    /**
     * 上传音频
     */
    suspend fun uploadAudio(path: String): String?

    /**
     * 上传视频
     */
    suspend fun uploadVideo(path: String): String?

}
