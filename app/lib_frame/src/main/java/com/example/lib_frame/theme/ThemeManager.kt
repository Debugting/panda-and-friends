package com.example.lib_frame.theme

import com.example.lib_frame.R
import com.example.lib_frame.bean.ThemeBean
import com.example.lib_frame.manager.SpManager
import java.util.Arrays

object ThemeManager {

    private const val SP_KEY_CUR_THEME = "sp_key_cur_theme"

    var themes: List<ThemeBean> = Arrays.asList(
        ThemeBean("知乎蓝", R.color.zhihuBlue, R.style.ZhiHuBlueTheme),
        ThemeBean("美团黄", R.color.meituanYellow, R.style.meituanYellowTheme),
        ThemeBean("网易红", R.color.cloudRed, R.style.CloudRedTheme),
        ThemeBean("哔哩粉", R.color.biliPink, R.style.BiLiPinkTheme),
        ThemeBean("酷安绿", R.color.kuanGreen, R.style.KuAnGreenTheme),
        ThemeBean("藤萝紫", R.color.tengluoPurple, R.style.TengLuoPurpleTheme),
        ThemeBean("碧海蓝", R.color.seaBlue, R.style.SeaBlueTheme),
        ThemeBean("樱草绿", R.color.grassGreen, R.style.GrassGreenTheme),
        ThemeBean("咖啡棕", R.color.coffeeBrown, R.style.CoffeeBrownTheme),
        ThemeBean("柠檬橙", R.color.lemonOrange, R.style.LemonOrangeTheme),
        ThemeBean("星空灰", R.color.startSkyGray, R.style.StartSkyGrayTheme)
    )

    var curTheme: ThemeBean
        get() = SpManager.get(SP_KEY_CUR_THEME, ThemeBean::class.java) ?: themes[0]
        set(value) {
            SpManager.put(SP_KEY_CUR_THEME, value)
        }
}
