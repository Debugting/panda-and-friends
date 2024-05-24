package com.example.lib_frame.utils

import android.text.TextUtils
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.example.lib_frame.bean.ProvinceBean
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.manager.AssetManagerHelper.getJson
import com.example.lib_frame.utils.KeyBoardUtils.hideSoftInput
import com.example.lib_frame.utils.TimeUtils.formatDate
import com.example.lib_frame.utils.TimeUtils.formatDateAndTime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar
import java.util.Date

object PickerUtils {
    private val mProvinceBeanList = mutableListOf<ProvinceBean>()
    private val mProvinceList = mutableListOf<String>()
    private val mProvinceCityList = mutableListOf<List<String>>()
    private val mProvinceCityAreaList = mutableListOf<List<List<String>>>()

    fun choice(
        title: String, dataList: List<String>,
        onChoiceListener: OnChoiceListener,
    ) {
        hideSoftInput()
        OptionsPickerBuilder(AppManager.activity,
            OnOptionsSelectListener { options1: Int, _: Int, _: Int, _: View? ->
                if (options1 >= dataList.size) {
                    return@OnOptionsSelectListener
                }
                onChoiceListener.choice(options1, dataList[options1])
            }).setTitleText(title).build<String>().apply {
            setPicker(dataList)
            show()
        }
    }

    fun choiceSex(onChoiceListener: OnChoiceListener) {
        hideSoftInput()
        val picker = OptionsPickerBuilder(
            AppManager.activity
        ) { options1: Int, _: Int, _: Int, v: View? ->
            onChoiceListener.choice(
                options1, SEX.titles[options1]
            )
        }.setTitleText("选择性别").build<String>()
        picker.setPicker(SEX.titles)
        picker.show()
    }

    fun choiceStatus(onChoiceListener: OnChoiceListener) {
        hideSoftInput()
        val picker = OptionsPickerBuilder(
            AppManager.activity
        ) { options1: Int, _: Int, _: Int, v: View? ->
            onChoiceListener.choice(
                options1, STATUS.titles[options1]
            )
        }.setTitleText("选择状态").build<String>()
        picker.setPicker(STATUS.titles)
        picker.show()
    }

    fun choiceMonth(onDateChoiceListener: OnDateChoiceListener) {
        hideSoftInput()
        TimePickerBuilder(AppManager.activity) { date: Date, v: View? ->
            val dateStr = formatDate(date)
            onDateChoiceListener.choice(date.time, dateStr)
        }.setTitleText("选择年月").setType(booleanArrayOf(true, true, false, false, false, false))
            .build().show()
    }

    fun choiceDate(onDateChoiceListener: OnDateChoiceListener) {
        hideSoftInput()
        val timePicker = TimePickerBuilder(AppManager.activity) { date: Date, v: View? ->
            val dateStr = formatDate(date)
            onDateChoiceListener.choice(date.time, dateStr)
        }.setTitleText("选择日期").setType(booleanArrayOf(true, true, true, false, false, false))
            .build()
        timePicker.show()
    }

    fun choiceDate(
        startTime: Calendar,
        endTime: Calendar,
        onDateChoiceListener: OnDateChoiceListener,
    ) {
        hideSoftInput()
        val timePicker = TimePickerBuilder(AppManager.activity) { date: Date, v: View? ->
            val dateStr = formatDate(date)
            onDateChoiceListener.choice(date.time, dateStr)
        }.setTitleText("选择日期").setRangDate(startTime, endTime)
            .setType(booleanArrayOf(true, true, true, false, false, false)).build()
        timePicker.show()
    }

    fun choiceTime(onDateChoiceListener: OnDateChoiceListener) {
        hideSoftInput()
        val timePicker = TimePickerBuilder(AppManager.activity) { date: Date, _: View? ->
            val time = formatDate(
                date, "HH:mm:ss"
            )
            onDateChoiceListener.choice(-1, time)
        }.setTitleText("选择时间").setType(booleanArrayOf(false, false, false, true, true, true))
            .build()
        timePicker.show()
        hideSoftInput()
    }

    fun choiceDateAndTime(onChoiceListener: OnChoiceListener) {
        hideSoftInput()
        val timePicker = TimePickerBuilder(AppManager.activity) { date: Date, v: View? ->
            val time = formatDateAndTime(date.time)
            onChoiceListener.choice(-1, time)
        }.setTitleText("选择日期和时间").setType(booleanArrayOf(true, true, true, true, true, true))
            .build()
        timePicker.show()
    }

    fun choiceDateAndTime(start: Calendar, end: Calendar, onChoiceListener: OnChoiceListener) {
        hideSoftInput()
        TimePickerBuilder(AppManager.activity) { var1x: Date, _: View? ->
            onChoiceListener.choice(
                -1, formatDateAndTime(var1x.time)
            )
        }.setTitleText("选择日期和时间").setRangDate(start, end).setType(
            booleanArrayOf(true, true, true, true, true, true)
        ).build().show()
    }

    fun choiceProvince(onChoiceListener: OnChoiceListener) {
        hideSoftInput()
        initProvinceData()
        val pickerView: OptionsPickerView<String> =
            OptionsPickerBuilder(AppManager.activity) { var1x: Int, _: Int, _: Int, _: View? ->
                onChoiceListener.choice(
                    var1x, mProvinceList[var1x]
                )
            }.setTitleText("选择地区").build()
        pickerView.setPicker(mProvinceList)
        pickerView.show()
    }

    @JvmOverloads
    fun choiceArea(
        onAreaChoiceListener: OnAreaChoiceListener, province: String = "",
        city: String = "", area: String = "",
    ) {
        hideSoftInput()
        initProvinceData()
        val optionsPickerView: OptionsPickerView<String> = OptionsPickerBuilder(
            AppManager.activity
        ) { options1: Int, options2: Int, options3: Int, v: View? ->
            onAreaChoiceListener.choice(
                mProvinceList[options1],
                mProvinceCityList[options1][options2],
                mProvinceCityAreaList[options1][options2][options3]
            )
        }.setTitleText("选择地区").build()
        optionsPickerView.setPicker(mProvinceList, mProvinceCityList, mProvinceCityAreaList)

        mProvinceBeanList.onEachIndexed { index1, it ->
            if (!TextUtils.equals(province, it.name)) {
                return@onEachIndexed
            }
            it.city?.onEachIndexed { index2, it ->
                if (!TextUtils.equals(city, it.name)) {
                    return@onEachIndexed
                }
                it.area?.onEachIndexed { index3, it ->
                    if (it == area) {
                        optionsPickerView.setSelectOptions(index1, index2, index3)
                    }
                }
            }
        }
        optionsPickerView.show()
    }

    val provinceList: List<ProvinceBean>
        get() {
            initProvinceData()
            return mProvinceBeanList
        }

    private fun initProvinceData() {
        if (mProvinceBeanList.size != 0) {
            return
        }
        val json = getJson("json/province.json")
        val cityList = mutableListOf<String>()
        val cityAreaList = mutableListOf<List<String>>()
        Gson().fromJson<List<ProvinceBean>>(
            json, object : TypeToken<List<ProvinceBean>>() {}.type
        ).onEach {
            it.name?.let {
                mProvinceList.add(it)
            }
            it.city?.onEach {
                it.name?.let {
                    cityList.add(it)
                }
                cityAreaList.add(it.area ?: listOf())
            }
            mProvinceBeanList.add(it)
        }
        mProvinceCityList.add(cityList)
        mProvinceCityAreaList.add(cityAreaList)
        return
    }

    enum class STATUS(val value: Int, val title: String) {
        ENABLE(1, "可用"), UNABLE(0, "禁用");

        companion object {
            val titles = listOf(
                ENABLE.title, UNABLE.title
            )
            val values = listOf(
                ENABLE.value, UNABLE.value
            )
        }
    }

    enum class SEX(val value: Int, val title: String) {
        MAN(1, "男"), WOMEN(0, "女");

        companion object {
            val titles = listOf(
                MAN.title, WOMEN.title
            )
            val values = listOf(
                MAN.value, WOMEN.value
            )
        }
    }

    interface OnChoiceListener {
        fun choice(pos: Int, result: String)
    }

    @FunctionalInterface
    interface OnDateChoiceListener {
        fun choice(time: Long, str: String)
    }

    @FunctionalInterface
    interface OnAreaChoiceListener {
        fun choice(province: String, city: String, area: String)
    }
}
