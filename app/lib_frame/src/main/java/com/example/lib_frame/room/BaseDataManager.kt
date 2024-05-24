package com.example.lib_frame.room

import android.util.Log
import com.example.lib_frame.base.RoomBean
import com.example.lib_frame.manager.ConfigManager
import com.example.lib_frame.manager.UploadDataManager
import com.example.lib_frame.net.HttpHelper
import com.example.lib_frame.net.BaseService
import com.example.lib_frame.utils.FileUtils
import com.example.lib_frame.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseDataManager<T : RoomBean<T>> {

    abstract fun tableName(): String

    abstract fun convert(list: List<Any>): List<T>

    lateinit var dao: BaseDao<T>
    private var service: BaseService = HttpHelper.getService(BaseService::class.java)

    suspend fun insert(bean: T): T? {
        return request {
            if (ConfigManager.isOpenNet) {
                if (FileUtils.isExists(bean.imgUrl)) {
                    UploadDataManager.uploadImage(bean.imgUrl)?.apply {
                        bean.imgUrl = this
                    }
                }
                if (FileUtils.isExists(bean.imgUrl2)) {
                    UploadDataManager.uploadImage(bean.imgUrl2)?.apply {
                        bean.imgUrl2 = this
                    }
                }
                if (FileUtils.isExists(bean.imgUrl3)) {
                    UploadDataManager.uploadImage(bean.imgUrl3)?.apply {
                        bean.imgUrl3 = this
                    }
                }
                val response = service.insert(tableName(), bean)
                if (response.code == 0) {
                    bean.id = response.data
                    bean
                } else {
                    ToastUtils.success(response.message)
                    null
                }
            } else {
                bean.id = dao.insert(bean)
                bean
            }
        }
    }

    suspend fun insert(list: List<T>): List<T?>? {
        return request {
            list.map {
                insert(it)
            }
        }
    }

    suspend fun delete(bean: T): Long? {
        return request {
            if (ConfigManager.isOpenNet) {
                bean.id?.apply {
                    val response = service.delete(tableName(), this)
                    if (response.code == 0) {
                        response.data
                    } else {
                        ToastUtils.success(response.message)
                        null
                    }
                }
            } else {
                dao.delete(bean).toLong()
            }
        }
    }

    suspend fun delete(list: List<T>): List<Long?>? {
        return request {
            list.map {
                delete(it)
            }
        }
    }

    suspend fun getAll(map: Map<String, Any> = mapOf()): List<T>? {
        return try {
            withContext(Dispatchers.IO) {
                if (ConfigManager.isOpenNet) {
                    val response = service.getAll(tableName(), map)
                    if (response.code == 0) {
                        convert(response.data)
                    } else {
                        ToastUtils.success(response.message)
                        null
                    }
                } else {
                    dao.getAll()
                }
            }
        } catch (e: Exception) {
            e.message?.let {
                Log.e("TAG", it)
                ToastUtils.error(it)
            }
            null
        }
    }

    private suspend fun <T> request(block: suspend () -> T): T? {
        return try {
            withContext(Dispatchers.IO) {
                block.invoke()
            }
        } catch (e: Exception) {
            e.message?.let {
                Log.e("TAG", it)
                ToastUtils.error(it)
            }
            null
        }
    }
}
