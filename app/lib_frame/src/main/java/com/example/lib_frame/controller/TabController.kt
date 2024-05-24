package com.example.lib_frame.controller

import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.lib_frame.R
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.base.BasePaperAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabController(val fragment: BaseFragment<out ViewBinding>) {

    private val mTabLayout: TabLayout
    private val mViewPager: ViewPager2
    private val mAdapter: BasePaperAdapter
    private val mTabs = mutableListOf<String>()

    init {
        fragment.binding.root.apply {
            mTabLayout = findViewById(R.id.tabLayout)
            mViewPager = findViewById(R.id.viewPager)
            mAdapter = BasePaperAdapter(fragment)
            mViewPager.adapter = mAdapter
            TabLayoutMediator(
                mTabLayout,
                mViewPager
            ) { tab: TabLayout.Tab, position: Int ->
                tab.setText(mTabs[position])
            }.attach()
        }
    }

    fun update(
        tabs: List<String>,
        fragments: List<BaseFragment<out ViewBinding>>
    ) {
        mTabs.clear()
        mTabs.addAll(tabs)
        if (fragments.size > 4) {
            mTabLayout.tabMode = TabLayout.MODE_AUTO
        }
        mAdapter.update(fragments)
        mViewPager.adapter = mAdapter
    }
}