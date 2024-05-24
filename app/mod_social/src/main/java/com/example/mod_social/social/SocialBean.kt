package com.example.mod_social.social

import androidx.room.Ignore
import com.example.lib_frame.base.RoomBean
import java.io.Serializable

abstract class SocialBean<T : RoomBean<T>> : RoomBean<T>(), Serializable {
    @Ignore
    var visitedCount: Int = 0

    @Ignore
    var praisedCount: Int = 0

    @Ignore
    var collectedCount: Int = 0

    @Ignore
    var commentCount: Int = 0

    @Ignore
    var mPraised: Praised? = null

    @Ignore
    var mCollected: Collected? = null

    abstract fun dataType(): String
}