package com.example.lib_frame.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtils {

    private const val FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
    private const val FORMAT_DATE = "yyyy-MM-dd"
    const val FORMAT_TIME = "HH:mm:ss"

    /**
     * 判断是否今年
     */
    fun isCurYear(timeStamp: Long): Boolean {
        val currentTime = currentTime()
        return (timeStamp > getYearStartTime(currentTime)
                && timeStamp < getYearEndTime(currentTime))
    }

    /**
     * 判断是否今天
     */
    fun isToday(timeStamp: Long): Boolean {
        val currentTime = currentTime()
        return timeStamp >= getDailyStartTime(currentTime)
                && timeStamp < getDailyEndTime(currentTime) || timeStamp == getDailyEndTime(
            currentTime
        )
    }

    /**
     * 判断是否今天
     */
    fun isToday(timeStampStr: String): Boolean {
        val currentTime = currentTime()
        val timeStamp = parseStringToDateTime(timeStampStr)
        return (timeStamp > getDailyStartTime(currentTime)
                && timeStamp < getDailyEndTime(currentTime))
    }

    /**
     * 判断是否近一周
     */
    fun isLastWeek(timeStamp: Long): Boolean {
        val currentTime = currentTime()
        return (currentTime > timeStamp
                && timeStamp > currentTime - 7 * 24 * 60 * 60 * 1000)
    }

    /**
     * 判断是否近一周
     */
    fun isLastWeek(timeStampStr: String): Boolean {
        val currentTime = currentTime()
        val timeStamp = parseStringToDateTime(timeStampStr)
        return (currentTime > timeStamp
                && timeStamp > currentTime - 7 * 24 * 60 * 60 * 1000)
    }

    /**
     * 判断是否近一月
     */
    fun isLastMonth(timeStamp: Long): Boolean {
        val currentTime = currentTime()
        return (currentTime > timeStamp
                && timeStamp > currentTime - 30 * 24 * 60 * 60 * 1000L)
    }

    /**
     * 判断是否近一月
     */
    fun isLastMonth(timeStampStr: String): Boolean {
        val currentTime = currentTime()
        val timeStamp = parseStringToDateTime(timeStampStr)
        return (currentTime > timeStamp
                && timeStamp > currentTime - 30 * 24 * 60 * 60 * 1000L)
    }

    /**
     * 判断是否同一天
     */
    fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
        return isSameDay(calendar1.timeInMillis, calendar2.timeInMillis)
    }

    fun isSameDay(timeStamp1: Long, timeStamp2: Long): Boolean {
        return getDailyStartTime(timeStamp1) == getDailyStartTime(timeStamp2)
    }

    /**
     * 获取当前时间
     */
    fun currentTime(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前时间
     */
    fun currentTimeString(): String {
        return formatDateAndTime(System.currentTimeMillis())
    }

    /**
     * 获取当前时间
     */
    fun currentTimeString(format: String?): String {
        return formatDateAndTime(System.currentTimeMillis(), format)
    }

    /**
     * 获取指定某一天的开始时间戳
     */
    fun getDailyStartTime(timeStamp: Long = currentTime()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }

    fun getDailyStartTime(timeStr: String): Long {
        return getDailyStartTime(parseStringToDateTime(timeStr))
    }

    /**
     * 获取指定某一天的结束时间戳
     */
    fun getDailyEndTime(timeStamp: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.timeInMillis
    }

    fun getDailyEndTime(timeStr: String): Long {
        return getDailyEndTime(parseStringToDateTime(timeStr))
    }

    /**
     * 获取当前周的开始时间
     */
    fun getWeekStartTime(timeStamp: Long = currentTime()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }

    /**
     * 获取当前周的结束时间
     */
    fun getWeekEndTime(timeStamp: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.timeInMillis
    }

    /**
     * 获取当前周的开始日期
     */
    fun getWeekStartDay(timeStamp: Long): String {
        return formatDate(getWeekStartTime(timeStamp))
    }

    /**
     * 获取当前周的结束时间
     */
    fun getWeekEndDay(timeStamp: Long): String {
        return formatDate(getWeekEndTime(timeStamp))
    }

    /**
     * 获取当月开始时间戳
     */
    fun getMonthStartTime(timeStamp: Long = currentTime()): Long {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.timeInMillis = timeStamp
        calendar.add(Calendar.YEAR, 0)
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DAY_OF_MONTH] = 1 // 设置为1号,当前日期既为本月第一天
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }

    /**
     * 获取当月的结束时间戳
     */
    fun getMonthEndTime(timeStamp: Long): Long {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.timeInMillis = timeStamp
        calendar.add(Calendar.YEAR, 0)
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DAY_OF_MONTH] =
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH) // 获取当前月最后一天
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.timeInMillis
    }

    /**
     * 获取当年的开始时间戳
     */
    fun getYearStartTime(timeStamp: Long = currentTime()): Long {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.timeInMillis = timeStamp
        calendar.add(Calendar.YEAR, 0)
        calendar.add(Calendar.DATE, 0)
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DAY_OF_YEAR] = 1
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }

    /**
     * 获取当年的最后时间戳
     */
    fun getYearEndTime(timeStamp: Long): Long {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.timeInMillis = timeStamp
        val year = calendar[Calendar.YEAR]
        calendar.clear()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        calendar.roll(Calendar.DAY_OF_YEAR, -1)
        return calendar.timeInMillis
    }

    /**
     * 时间戳转化为时间格式
     */
    fun formatDateAndTime(timeStamp: Long): String {
        val sdf =
            SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA)
        return sdf.format(timeStamp)
    }

    /**
     * 时间戳转化为时间格式
     */
    fun formatDateAndTime(timeStamp: Long, format: String?): String {
        val sdf =
            SimpleDateFormat(format, Locale.CHINA)
        return sdf.format(timeStamp)
    }

    /**
     * 获取当前是几号
     */
    fun getDay(timeStamp: Long): Int {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.timeInMillis = timeStamp
        return calendar[Calendar.DAY_OF_MONTH]
    }

    /**
     * 获取当前是第几周
     */
    fun getWeek(timeStamp: Long): Int {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.firstDayOfWeek = Calendar.MONDAY //设置星期一为一周开始的第一天
        calendar.timeInMillis = timeStamp
        return calendar[Calendar.WEEK_OF_YEAR]
    }

    /**
     * 获取当前月份
     */
    fun getMonth(timeStamp: Long): Int {
        val calendar = Calendar.getInstance() // 获取当前日期
        calendar.timeInMillis = timeStamp
        return calendar[Calendar.MONTH] + 1
    }

    fun getDayToWeek(timeStamp: Long): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        //一周的第几天
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w <= 0) {
            w = 7
        }
        return w
    }

    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     */
    fun dateToWeek(timeStamp: Long): String {
        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        //一周的第几天
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w < 0) {
            w = 0
        }
        return weekDays[w]
    }

    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     */
    fun dateToWeek2(timeStamp: Long): String {
        val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeStamp
        //一周的第几天
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w < 0) {
            w = 0
        }
        return weekDays[w]
    }

    fun formatDate(timeStr: String): String {
        val sdf = SimpleDateFormat(FORMAT_DATE, Locale.CHINA)
        return sdf.format(parseStringToDateTime(timeStr))
    }

    fun formatDate(timeStamp: Long): String {
        val sdf = SimpleDateFormat(FORMAT_DATE, Locale.CHINA)
        return sdf.format(timeStamp)
    }

    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat(FORMAT_DATE, Locale.CHINA)
        return sdf.format(date.time)
    }

    fun formatDate(timeStamp: Long, pattern: String?): String {
        val sdf = SimpleDateFormat(pattern, Locale.CHINA)
        return sdf.format(timeStamp)
    }

    fun formatDate(date: Date, pattern: String?): String {
        val sdf = SimpleDateFormat(pattern, Locale.CHINA)
        return sdf.format(date.time)
    }

    fun equalTime(tineStamp: Long, tineStamp2: Long): Boolean {
        return TextUtils.equals(getTime(tineStamp), getTime(tineStamp2))
    }

    /**
     * 得到时间  HH:mm:ss
     */
    fun getTime(timeStamp: Long): String? {
        var time: String? = null
        val sdf = SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA)
        val date = sdf.format(timeStamp)
        val split = date.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        if (split.size > 1) {
            time = split[1]
        }
        return time
    }

    fun getTime(timeStamp: String): String? {
        return getTime(parseStringToDateTime(timeStamp))
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     */
    fun convertTimeCompareNow(timeStamp: Long): String {
        val curTime = System.currentTimeMillis()
        var time = curTime - timeStamp
        time /= 1000
        return if (time < 60 && time >= 0) {
            "刚刚"
        } else if (time >= 60 && time < 3600) {
            (time / 60).toString() + "分钟前"
        } else if (time >= 3600 && time < 3600 * 24) {
            (time / 3600).toString() + "小时前"
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            (time / 3600 / 24).toString() + "天前"
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            (time / 3600 / 24 / 30).toString() + "个月前"
        } else if (time >= 3600 * 24 * 30 * 12) {
            (time / 3600 / 24 / 30 / 12).toString() + "年前"
        } else {
            "刚刚"
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     */
    fun convertTimeCompareNow(timeStr: String): String {
        val curTime = System.currentTimeMillis()
        var time = curTime - parseStringToDateTime(timeStr)
        time /= 1000
        return if (time < 60 && time >= 0) {
            "刚刚"
        } else if (time >= 60 && time < 3600) {
            (time / 60).toString() + "分钟前"
        } else if (time >= 3600 && time < 3600 * 24) {
            (time / 3600).toString() + "小时前"
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            (time / 3600 / 24).toString() + "天前"
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            (time / 3600 / 24 / 30).toString() + "个月前"
        } else if (time >= 3600 * 24 * 30 * 12) {
            (time / 3600 / 24 / 30 / 12).toString() + "年前"
        } else {
            "刚刚"
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     */
    fun timeStampToFormat(timeStamp: Long): String {
        val curTime = System.currentTimeMillis() / 1000L
        val time = curTime - timeStamp
        return (time / 60).toString() + ""
    }

    /**
     * String转换时间戳
     */
    fun parseStringToDateTime(timeStr: String): Long {
        val sdf: SimpleDateFormat = when (timeStr.length) {
            FORMAT_DATE.length -> {
                SimpleDateFormat(FORMAT_DATE, Locale.CHINA)
            }

            FORMAT_TIME.length -> {
                SimpleDateFormat(FORMAT_TIME, Locale.CHINA)
            }

            else -> {
                SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA)
            }
        }
        var date: Date? = null
        try {
            date = sdf.parse(timeStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date?.time ?: -1
    }

    fun getDurationMinAndSec(duration: Int): String {
        var duration = duration
        duration = duration / 1000
        val min = duration / 60
        val second = duration % 60
        val minStr: String
        var secondStr = ""
        minStr = if (min < 10) {
            "0$min"
        } else {
            min.toString() + ""
        }
        secondStr = if (second < 10) {
            "0$second"
        } else {
            second.toString() + ""
        }
        return "$minStr : $secondStr"
    }

    fun getDurationHourAndMin(duration: Long): String {
        var duration = duration
        duration = duration / 1000
        val hour = duration / (60 * 60)
        val min = (duration - hour * 60 * 60) / 60
        return hour.toString() + "小时" + min + "分钟"
    }

    fun timeToMilliseconds(time: String): Long {
        val parts = time.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        val seconds = parts[2].toInt()
        val hoursInMillis = hours * 60 * 60 * 1000
        val minutesInMillis = minutes * 60 * 1000
        val secondsInMillis = seconds * 1000
        return (hoursInMillis + minutesInMillis + secondsInMillis).toLong()
    }
}
