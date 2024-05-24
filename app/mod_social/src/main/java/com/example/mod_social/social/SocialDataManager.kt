package com.example.mod_social.social

import androidx.room.Room
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.room.BaseDataManager
import com.example.lib_frame.utils.GsonUtils
import com.example.mod_social.banner.BannerDataManager
import com.example.mod_user.dao.UserDataManager

object SocialDataManager {

    init {
        Room.databaseBuilder(
            AppManager.app, SocialDatabase::class.java, "tbSocial"
        ).build().apply {
            VisitedDataManager.dao = VisitedDao()
            PraisedDataManager.dao = PraisedDao()
            CollectedDataManager.dao = CollectedDao()
            CommentDataManager.dao = CommentDao()
            BannerDataManager.dao = BannerDao()
        }
    }

    suspend fun getSocialData(list: List<SocialBean<*>>) {
        if (list.isEmpty()) {
            return
        }
        val dataType = list.first().dataType()
        val visitedList = VisitedDataManager.getAll(
            mapOf(
                "dataType" to dataType
            )
        )
        val praisedList = PraisedDataManager.getAll(
            mapOf(
                "dataType" to dataType
            )
        )
        val collectedList = CollectedDataManager.getAll(
            mapOf(
                "dataType" to dataType
            )
        )
        val commentList = CommentDataManager.getAll(
            mapOf(
                "dataType" to dataType
            )
        )
        list.onEach { bean ->
            bean.visitedCount = 0
            visitedList?.onEach {
                if (it.outId == bean.id) {
                    bean.visitedCount++
                }
            }

            bean.praisedCount = 0
            praisedList?.onEach {
                if (it.outId == bean.id) {
                    bean.praisedCount++
                    if (it.userId == UserDataManager.loginUserId) {
                        bean.mPraised = it
                    }
                }
            }

            bean.collectedCount = 0
            collectedList?.onEach {
                if (it.outId == bean.id) {
                    bean.collectedCount++
                    if (it.userId == UserDataManager.loginUserId) {
                        bean.mCollected = it
                    }
                }
            }

            commentList?.onEach {
                if (it.outId == bean.id) {
                    bean.commentCount++
                }
            }
        }
    }

    suspend fun getComment(dataType: String, outId: Long): List<Comment> {
        val comments = mutableListOf<Comment>()
        CommentDataManager.getAll(
            mapOf(
                "dataType" to dataType, "outId" to outId
            )
        )?.apply {
            comments.addAll(this)
            CommentDataManager.getAll(
                mapOf("dataType" to Comment.COMMENT_TYPE)
            )?.onEach { subComment ->
                comments.onEachIndexed { index, comment ->
                    if (comment.dataType != Comment.COMMENT_TYPE && comment.id == subComment.outId) {
                        comments.add(index + 1, subComment)
                        return@onEach
                    }
                }
            }
        }
        UserDataManager.getAll()?.let { userList ->
            comments.onEach { comment ->
                userList.onEach { user ->
                    if (comment.userId == user.id) {
                        comment.user = user
                    }
                    if (comment.replyUserId == user.id) {
                        comment.replyUser = user
                    }
                }
            }
        }
        return comments
    }
}


object VisitedDataManager : BaseDataManager<Visited>() {

    override fun tableName(): String = TABLE_NAME_VISITED

    override fun convert(list: List<Any>): List<Visited> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }
}

object PraisedDataManager : BaseDataManager<Praised>() {

    override fun tableName(): String = TABLE_NAME_PRAISED

    override fun convert(list: List<Any>): List<Praised> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }

}

object CollectedDataManager : BaseDataManager<Collected>() {

    override fun tableName(): String = TABLE_NAME_COLLECTED

    override fun convert(list: List<Any>): List<Collected> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }
}

object CommentDataManager : BaseDataManager<Comment>() {

    override fun tableName(): String = TABLE_NAME_COMMENT

    override fun convert(list: List<Any>): List<Comment> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }
}

