package com.example.lib_frame.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.lib_frame.manager.AppManager

object KeyBoardUtils {
    /**
     * 打卡软键盘
     */
    fun openKeyBord(view: View?) {
        val imm = AppManager.activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun hideSoftInput() {
        val imm = AppManager.activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            AppManager.activity.window.decorView.windowToken, 0
        )
    }
}