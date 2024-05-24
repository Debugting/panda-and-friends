plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.lib_frame"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    api("androidx.core:core-ktx:1.10.1")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.9.0")

    //页面捕捉器
    api("com.bytedance.tools.codelocator:codelocator-core:2.0.3")

    //viewmodel
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    //图片加载
    api("com.github.bumptech.glide:glide:4.16.0")
    //room数据库
    api("androidx.room:room-runtime:2.6.1")

    //网络请求
    api("com.squareup.okhttp3:okhttp")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")
    api("com.squareup.retrofit2:retrofit:2.10.0")
    api("com.squareup.retrofit2:converter-gson:2.10.0")

    //toast
    api("com.github.GrenderG:Toasty:1.5.2")
    //dialog
    api("com.kongzue.dialog_v3x:dialog:3.2.4")
    //recyclerview辅助类
    api("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.4")

    //下拉刷新
    api("io.github.scwang90:refresh-layout-kernel:2.1.0")
    api("io.github.scwang90:refresh-header-classics:2.1.0")
    api("io.github.scwang90:refresh-footer-classics:2.1.0")

    //图片选择器
    api("io.github.lucksiege:pictureselector:v3.11.2")
    api("io.github.lucksiege:ucrop:v3.11.2")

    //条件选择器
    api("com.contrarywind:Android-PickerView:4.1.9")

    //轮播图
    api("io.github.youth5201314:banner:2.2.3")

    //图表
    api("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //日志打印
    api("com.orhanobut:logger:2.2.0")

    //日历
    api("com.haibin:calendarview:3.7.1")

    implementation(kotlin("reflect"))

}