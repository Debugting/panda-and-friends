package com.example.lib_frame.manager

object ConfigManager {

    var baseUrl: String
        get() = SpManager.get("baseUrl", "http://192.168.1.3:8080")
        set(url) {
            SpManager.put("baseUrl", url)
        }

    var isOpenNet: Boolean
        get() = SpManager.get("isOpenNet", true)
        set(openNet) {
            SpManager.put("isOpenNet", openNet)
        }

    val isOpenVoiceInput: Boolean
        get() = SpManager.get("isOpenVoiceInput", false)

    fun openVoiceInput() {
        SpManager.put("isOpenVoiceInput", true)
    }

}