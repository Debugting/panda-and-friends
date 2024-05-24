package com.example.lib_frame.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.Fragment
import com.example.lib_frame.R
import com.example.lib_frame.databinding.FragmentHomeBinding

abstract class BaseHomeFragment<B : ViewBinding>(inflate: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding = FragmentHomeBinding::inflate) :
    BaseFragment<B>(inflate) {

    var mNavTitle = ""
    var mNavIcon: Int = R.mipmap.menu_ic_todo

    protected fun initNavMenu(title: String, iconRes: Int) {
        mNavTitle = title
        mNavIcon = iconRes
    }

    fun initFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
