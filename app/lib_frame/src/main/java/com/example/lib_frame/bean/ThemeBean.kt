package com.example.lib_frame.bean

import java.io.Serializable
import java.util.Objects

class ThemeBean(var name: String, var color: Int, var theme: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val themeBean = other as ThemeBean
        return color == themeBean.color && theme == themeBean.theme && name == themeBean.name
    }

    override fun hashCode(): Int {
        return Objects.hash(name, color, theme)
    }

}
