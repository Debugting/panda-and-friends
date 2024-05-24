package com.example.mod_social.social

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.RoomDatabase
import com.example.lib_frame.base.RoomBean
import com.example.lib_frame.datacenter.IUser
import com.example.lib_frame.room.BaseDao
import com.example.mod_social.banner.Banner
import com.example.mod_social.banner.BannerDao
import com.example.mod_user.dao.User


/**
 * 浏览
 */
internal const val TABLE_NAME_VISITED = "tbSocialVisited"

@Entity(tableName = TABLE_NAME_VISITED)
class Visited : RoomBean<Visited>()

@Dao
interface VisitedDao : BaseDao<Visited>

/**
 * 点赞
 */
internal const val TABLE_NAME_PRAISED = "tbSocialPraised"

@Entity(tableName = TABLE_NAME_PRAISED)
class Praised : RoomBean<Praised>()

@Dao
interface PraisedDao : BaseDao<Praised>


/**
 * 收藏
 */
internal const val TABLE_NAME_COLLECTED = "tbSocialCollected"

@Entity(tableName = TABLE_NAME_COLLECTED)
class Collected : RoomBean<Collected>()

@Dao
interface CollectedDao : BaseDao<Collected>


/**
 * 评论
 */
internal const val TABLE_NAME_COMMENT = "tbSocialComment"

@Entity(tableName = TABLE_NAME_COMMENT)
class Comment : RoomBean<Comment>() {
    companion object {
        const val COMMENT_TYPE = "commentType"
    }

    @ColumnInfo(name = "replyUserId")
    var replyUserId: Long = 0

    @Ignore
    var replyUser: IUser? = null
}

@Dao
interface CommentDao : BaseDao<Comment>


@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Visited::class,
        Praised::class,
        Collected::class,
        Comment::class,
        Banner::class,
    ]
)
abstract class SocialDatabase : RoomDatabase() {
    abstract fun VisitedDao(): VisitedDao
    abstract fun PraisedDao(): PraisedDao
    abstract fun CollectedDao(): CollectedDao
    abstract fun CommentDao(): CommentDao
    abstract fun BannerDao(): BannerDao
}