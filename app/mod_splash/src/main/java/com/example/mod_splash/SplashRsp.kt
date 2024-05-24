package com.example.mod_splash

data class SplashRsp(
    var images: List<ImagesDTO>? = null
) {
    data class ImagesDTO(
        var url: String? = null,
        var copyright: String? = null,
        var title: String? = null
    )
}
