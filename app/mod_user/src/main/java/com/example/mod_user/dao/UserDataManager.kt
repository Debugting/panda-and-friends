package com.example.mod_user.dao

import androidx.room.Room.databaseBuilder
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.manager.SpManager
import com.example.lib_frame.room.BaseDataManager
import com.example.lib_frame.utils.GsonUtils
import com.example.lib_frame.utils.ToastUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object UserDataManager : BaseDataManager<User>() {

    init {
        databaseBuilder(
            AppManager.app,
            UserDatabase::class.java,
            TABLE_NAME
        ).build().apply {
            dao = UserDao()
        }
    }

    override fun tableName(): String = TABLE_NAME

    override fun convert(list: List<Any>): List<User> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }

    suspend fun login(act: String, pwd: String): Boolean {
        UserDataManager.getAll(
            mapOf(
                "act" to act,
                "pwd" to pwd,
            )
        )?.apply {
            onEach {
                if (it.act == act && it.pwd == pwd) {
                    if (it.isEnable) {
                        lastLogin(it)
                        return true
                    } else {
                        ToastUtils.error("账号已禁用，请联系管理员！")
                    }
                    return false
                }
            }
            ToastUtils.error("账号或密码错误！")
        }
        return false
    }

    private const val SP_KEY_CUR_USER = "sp_key_cur_user"
    private const val SP_KEY_LAST_LOGIN = "sp_key_last_login"
    private const val SP_KEY_LOGIN_HISTORY = "sp_key_login_history"

    var loginUser: User?
        get() = SpManager.get(SP_KEY_CUR_USER, User::class.java)
        set(value) {
            SpManager.put(SP_KEY_CUR_USER, value)
        }

    var loginUserId: Long = loginUser?.id ?: 0

    val isLogin: Boolean
        get() = loginUser != null

    fun loginHistory(): HashMap<String?, User> {
        val json = SpManager.get(SP_KEY_LOGIN_HISTORY, "")
        return if (json.isNotEmpty()) {
            Gson().fromJson(json, object : TypeToken<HashMap<String, User>>() {}.type)
        } else HashMap()
    }

    fun lastLogin(): User? {
        return SpManager.get(SP_KEY_LAST_LOGIN, User::class.java)
    }

    fun lastLogin(user: User) {
        loginUser = user
        val users = loginHistory()
        users[user.act] = user
        SpManager.put(SP_KEY_LOGIN_HISTORY, users)
        SpManager.put(SP_KEY_LAST_LOGIN, user)
    }
}

