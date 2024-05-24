package com.example.lib_frame.controller

import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.lib_frame.R
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.extensions.getString
import com.example.lib_frame.utils.DialogUtils
import com.example.lib_frame.widgets.IconFontImageView

class MenuController(
    fragment: BaseFragment<out ViewBinding>,
    callBack: (Int) -> Unit,
    menus: List<String>? = null,
    resId: Int = R.string.ic_menu,
) {

    init {
        fragment.binding.root.findViewById<IconFontImageView>(R.id.menu).apply {
            isVisible = true
            text = getString(resId)
            setOnClickListener {
                if (menus == null) {
                    callBack.invoke(0)
                } else {
                    DialogUtils.menuDialog(menus) { _, index ->
                        callBack.invoke(index)
                    }
                }
            }
        }
    }
}
