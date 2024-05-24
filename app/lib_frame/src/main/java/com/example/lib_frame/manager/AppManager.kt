package com.example.lib_frame.manager

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.os.Bundle
import android.os.Process
import com.example.lib_frame.base.BaseActivity

object AppManager {
    lateinit var app: Application

    lateinit var activity: BaseActivity<*>

    fun init(application: Application) {
        this.app = application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                if (activity is BaseActivity<*>) {
                    this@AppManager.activity = activity
                }
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    fun restartApp() {
        val LaunchIntent = app.packageManager.getLaunchIntentForPackage(
            app.packageName
        )
        LaunchIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        app.startActivity(LaunchIntent)
        Process.killProcess(Process.myPid())
    }
}
