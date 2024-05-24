package com.example.lib_frame.base

import android.app.Application
import android.content.Context
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.manager.ConfigManager
import com.example.lib_frame.net.HttpHelper
import com.example.lib_frame.utils.DialogUtils
import com.example.lib_frame.utils.GlideUtils
import com.example.lib_frame.utils.ToastUtils
import com.kongzue.dialog.util.DialogSettings.STYLE
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout

open class BaseApplication : Application() {

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, _: RefreshLayout ->
            ClassicsHeader(
                context
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this)
        initHttp()
        ToastUtils.init(this)
        DialogUtils.setStyle(STYLE.STYLE_IOS)
    }

    open fun initHttp() {
        val baseUrl: String = ConfigManager.baseUrl
        val hashMap: MutableMap<String, String> = getUrlMap()
        hashMap["bing"] = "https://cn.bing.com"
        HttpHelper
            .baseUrl(baseUrl)
            .mulBaseUrl(hashMap)
            .init()
        GlideUtils.init("$baseUrl/fileImage/watch?path=")
    }

    open fun getUrlMap(): MutableMap<String, String> {
        return mutableMapOf()
    }
}
