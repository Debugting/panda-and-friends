plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.mod_paper"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    viewBinding {
        enable = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":lib_frame"))
    implementation(project(":mod_user"))
    implementation(project(":mod_social"))
    //room数据库
    ksp("androidx.room:room-compiler:2.5.2")
}