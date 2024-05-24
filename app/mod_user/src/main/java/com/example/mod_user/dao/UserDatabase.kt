package com.example.mod_user.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.RoomDatabase
import com.example.lib_frame.base.RoomBean
import com.example.lib_frame.datacenter.IUser
import com.example.lib_frame.room.BaseDao
import com.example.lib_frame.utils.PickerUtils

internal const val TABLE_NAME = "tbUser"

@Entity(tableName = TABLE_NAME)
class User : RoomBean<User>(), IUser {
    //账号
    @ColumnInfo(name = "act")
    var act: String = ""

    //密码
    @ColumnInfo(name = "pwd")
    var pwd: String = ""

    //性别
    @ColumnInfo(name = "sex")
    var sex = 0

    //出生日期
    @ColumnInfo(name = "birthday")
    var birthday: String = ""

    //籍贯
    @ColumnInfo(name = "province")
    var province: String = ""

    //密保问题
    @ColumnInfo(name = "question")
    var question: String = ""

    //密保答案
    @ColumnInfo(name = "answer")
    var answer: String = ""

    //用户类型
    @ColumnInfo(name = "userType")
    var userType: Int = 1

    @Ignore
    override fun id(): Long = id

    @Ignore
    override fun name(): String = name

    @Ignore
    override fun province(): String = province

    @Ignore
    override fun imgUrl(): String = imgUrl

    @Ignore
    fun getSexWord(): String = PickerUtils.SEX.titles[sex]
}

@Dao
interface UserDao : BaseDao<User>

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        User::class,
    ]
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
}