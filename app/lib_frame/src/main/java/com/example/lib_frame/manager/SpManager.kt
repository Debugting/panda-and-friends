package com.example.lib_frame.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object SpManager {

    val sp: SharedPreferences by lazy {
        AppManager.app.getSharedPreferences(
            AppManager.app.packageName, Context.MODE_PRIVATE
        )
    }
    val gson: Gson by lazy {
        Gson()
    }

    /**
     * 保存数据的方法
     */
    inline fun <reified T> put(key: String, value: T) {
        val editor = sp.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> editor.putString(key, gson.toJson(value))
        }
        editor.apply()
    }

    /**
     * 获取数据的方法
     */
    inline fun <reified T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sp.getString(key, defaultValue)
            is Int -> sp.getInt(key, defaultValue)
            is Boolean -> sp.getBoolean(key, defaultValue)
            is Float -> sp.getFloat(key, defaultValue)
            is Long -> sp.getLong(key, defaultValue)
            else -> {
                sp.getString(key, "")
            }
        } as T
    }

    inline fun <reified T> get(key: String, cls: Class<T>): T? {
        val json = sp.getString(key, "")
        return if ("" != json) {
            gson.fromJson(json, cls)
        } else null
    }

    /**
     * 查询SharedPreferences是否含有某个key值
     */
    fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    val all: Map<String, *>
        /**
         * 获取SharedPreferences里所有的键值对
         */
        get() = sp.all

    /**
     * 移除某个key值对
     */
    fun remove(key: String) {
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * 清空SharedPreferences里的所有数据
     */
    fun clear(context: Context) {
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}
