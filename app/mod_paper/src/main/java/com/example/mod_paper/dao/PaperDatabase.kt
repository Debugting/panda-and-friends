package com.example.mod_paper.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.RoomDatabase
import com.example.lib_frame.base.RoomBean
import com.example.lib_frame.room.BaseDao
import com.example.mod_social.social.SocialBean

/**
 * 帖子
 */
const val TABLE_NAME = "tbPaper"

@Entity(tableName = TABLE_NAME)
class Paper : SocialBean<Paper>() {

    //类型
    @ColumnInfo(name = "paperTypeId")
    var paperTypeId: Long? = null

    @Ignore
    override fun dataType(): String {
        return TABLE_NAME
    }
}

@Dao
interface PaperDao : BaseDao<Paper>

/**
 * 帖子类型
 */
const val PAPER_TYPE_NAME = "tbPaperType"

@Entity(tableName = PAPER_TYPE_NAME)
class PaperType : RoomBean<PaperType>()

@Dao
interface PaperTypeDao : BaseDao<PaperType>


@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Paper::class,
        PaperType::class,
    ]
)
abstract class PaperDatabase : RoomDatabase() {
    abstract fun PaperDao(): PaperDao
    abstract fun PaperTypeDao(): PaperTypeDao
}

