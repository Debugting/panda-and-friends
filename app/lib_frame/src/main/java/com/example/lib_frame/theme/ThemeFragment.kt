package com.example.lib_frame.theme

import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.controller.ToolbarController
import com.example.lib_frame.databinding.ThemeFragmentBinding

class ThemeFragment : BaseFragment<ThemeFragmentBinding>(ThemeFragmentBinding::inflate) {

    private lateinit var mAdapter: RcvThemeAdapter

    override fun initView() {
        mToolbarController.initTitle("主题中心")
        mAdapter = RcvThemeAdapter().apply {
            setOnItemClickListener { adapter, _, position ->
                adapter.getItem(position)?.apply {
                    ThemeManager.curTheme = this
                    mActivity.recreate()
                }
            }
            binding.recyclerview.adapter = this
        }
    }

    override suspend fun initData() {
        mAdapter.items = ThemeManager.themes
    }
}