package com.example.mod_paper.dao

import androidx.room.Room
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.room.BaseDataManager
import com.example.lib_frame.utils.GsonUtils
import com.example.mod_social.social.SocialDataManager
import com.example.mod_user.dao.UserDataManager

object PaperDataManager : BaseDataManager<Paper>() {

    suspend fun getPapers(
        map: Map<String, Any> = mapOf(),
        isCollected: Boolean = false
    ): List<Paper> {
        val papers = mutableListOf<Paper>()
        PaperDataManager.getAll(map)?.apply {
            papers.addAll(this)
        }
        UserDataManager.getAll()?.let { users ->
            papers.onEach {
                users.onEach { user ->
                    if (it.userId == user.id) {
                        it.user = user
                    }
                }
            }
        }
        SocialDataManager.getSocialData(papers)
        return if (isCollected) {
            papers.filter {
                it.mCollected != null
            }
        } else {
            papers
        }
    }

    init {
        Room.databaseBuilder(AppManager.app, PaperDatabase::class.java, TABLE_NAME).build().apply {
            PaperTypeDataManager.dao = PaperTypeDao()
            PaperDataManager.dao = PaperDao()
        }
    }

    override fun tableName(): String = TABLE_NAME

    override fun convert(list: List<Any>): List<Paper> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }

}

object PaperTypeDataManager : BaseDataManager<PaperType>() {

    override fun tableName(): String = PAPER_TYPE_NAME

    override fun convert(list: List<Any>): List<PaperType> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }
}
