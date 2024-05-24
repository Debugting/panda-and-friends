package com.example.lib_frame.room

import androidx.room.Delete
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.lib_frame.base.RoomBean

/**
 * 基础数据操作
 */
interface BaseDao<T : RoomBean<T>> {

    @Ignore
    fun tb(): String = javaClass.interfaces[0].simpleName

    //插入数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entities: T): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<T>): List<Long>

    //删除数据
    @Delete
    fun delete(entity: T): Int

    fun getAll(): List<T> {
        val query = SimpleSQLiteQuery("select * from ${tb()} order by createTime desc")
        return doGetAll(query)
    }

    fun deleteAll(): Long {
        val query = SimpleSQLiteQuery(
            "delete from ${tb()}"
        )
        return doDeleteAll(query)
    }

    @RawQuery
    fun doGetAll(query: SupportSQLiteQuery): List<T>

    @RawQuery
    fun doDeleteAll(query: SupportSQLiteQuery): Long

}
