package com.example.lib_frame.base

import android.text.TextUtils
import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.lib_frame.datacenter.IUser
import com.example.lib_frame.utils.TimeUtils.currentTimeString
import java.io.Serializable

abstract class RoomBean<T : RoomBean<T>> : Comparable<RoomBean<T>>, Serializable {
    //唯一标识
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    //外键
    @ColumnInfo(name = "outId")
    var outId: Long = 0

    //用户
    @ColumnInfo(name = "userId")
    var userId: Long = 0

    //创建时间
    @ColumnInfo(name = "createTime")
    var createTime = currentTimeString()

    //开始时间
    @ColumnInfo(name = "beginTime")
    var beginTime = ""

    //结束时间
    @ColumnInfo(name = "finishTime")
    var finishTime = ""

    //开始日期
    @ColumnInfo(name = "beginDate")
    var beginDate = ""

    //结束日期
    @ColumnInfo(name = "finishDate")
    var finishDate = ""

    //排序
    @ColumnInfo(name = "sort")
    var sort = 0

    //状态
    @ColumnInfo(name = "status")
    var status = 0

    //类型
    @ColumnInfo(name = "type")
    var type = 0

    //数据分类
    @ColumnInfo(name = "dataType")
    open var dataType: String = ""

    //名称
    @ColumnInfo(name = "name")
    var name: String = ""

    //标题
    @ColumnInfo(name = "title")
    var title: String = ""

    //简介
    @ColumnInfo(name = "word")
    open var word: String = ""

    //备注
    @ColumnInfo(name = "remark")
    var remark: String = ""

    //内容
    @ColumnInfo(name = "content")
    var content: String = ""

    //金额
    @ColumnInfo(name = "charge")
    var charge: Double = 0.0

    //邮箱
    @ColumnInfo(name = "email")
    var email: String = ""

    //手机
    @ColumnInfo(name = "phone")
    var phone: String = ""

    //身份证
    @ColumnInfo(name = "idCard")
    var idCard: String = ""

    //地址
    @ColumnInfo(name = "address")
    var address: String = ""

    //经度
    @ColumnInfo(name = "latitude")
    var latitude: Double = 39.90835885636669

    //纬度
    @ColumnInfo(name = "longitude")
    var longitude: Double = 116.40170033789438

    //图片
    @ColumnInfo(name = "imgUrl")
    var imgUrl: String = ""

    //图片2
    @ColumnInfo(name = "imgUrl2")
    var imgUrl2: String = ""

    //图片3
    @ColumnInfo(name = "imgUrl3")
    var imgUrl3: String = ""

    @Ignore
    var user: IUser? = null

    val isEnable: Boolean
        get() = status == 1

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val bean = o as T
        return id == bean.id
    }

    override fun compareTo(o: RoomBean<T>): Int {
        val result = sort - o.sort
        return if (result == 0 && !TextUtils.isEmpty(createTime) && !TextUtils.isEmpty(
                o.createTime
            )
        ) {
            createTime.compareTo(o.createTime)
        } else result
    }
}
