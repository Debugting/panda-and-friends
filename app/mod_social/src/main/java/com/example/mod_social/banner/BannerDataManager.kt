package com.example.mod_social.banner

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Ignore
import com.example.lib_frame.room.BaseDao
import com.example.lib_frame.room.BaseDataManager
import com.example.lib_frame.utils.GsonUtils
import com.example.mod_social.social.SocialBean

/**
 * 轮播图
 */

const val TABLE_NAME = "tbSocialBanner"

@Entity(tableName = TABLE_NAME)
class Banner : SocialBean<Banner>() {

    @Ignore
    override fun dataType(): String = TABLE_NAME
}

@Dao
interface BannerDao : BaseDao<Banner>

object BannerDataManager : BaseDataManager<Banner>() {

    override fun tableName(): String = TABLE_NAME

    override fun convert(list: List<Any>): List<Banner> {
        return GsonUtils.fromJson(GsonUtils.toJson(list))
    }
}
