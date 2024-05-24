package com.example.application

import com.example.lib_frame.base.BaseApplication
import com.example.lib_frame.manager.ConfigManager
import com.example.lib_frame.manager.UploadDataManager

class MyApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ConfigManager.isOpenNet = true
        UploadDataManager.init(UploadManager)
    }
}
