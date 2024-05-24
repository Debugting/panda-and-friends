package com.example.mod_paper.fragment

import android.os.Bundle
import com.example.lib_frame.base.BaseHomeFragment
import com.example.lib_frame.controller.MenuController
import com.example.lib_frame.controller.RefreshController
import com.example.lib_frame.databinding.FragmentHomeBinding

class PaperHomeFragment : BaseHomeFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    companion object {
        fun newInstance(typeId: Long, tabTitle: String, iconRes: Int): PaperHomeFragment {
            return PaperHomeFragment().apply {
                initNavMenu(tabTitle, iconRes)
                mBundle.putLong("typeId", typeId)
            }
        }
    }

    override fun initView() {
        initFragment(PaperListFragment().apply {
            mBundle.putAll(this@PaperHomeFragment.mBundle)
        })
        RefreshController(this, false)
        MenuController(
            this, {
                when (it) {
                    0 -> goto(PaperEditFragment::class.java, Bundle().apply {
                        putAll(this@PaperHomeFragment.mBundle)
                    })

                    1 -> goto(PaperListFragment::class.java, Bundle().apply {
                        putAll(this@PaperHomeFragment.mBundle)
                        putBoolean("isSearch", true)
                    })

                    2 -> goto(PaperListFragment::class.java, Bundle().apply {
                        putAll(this@PaperHomeFragment.mBundle)
                        putBoolean("isCollected", true)
                    })

                    3 -> goto(PaperListFragment::class.java, Bundle().apply {
                        putAll(this@PaperHomeFragment.mBundle)
                        putBoolean("isMine", true)
                    })
                }
            }, listOf(
                "我要发布",
                "搜索查询",
                "我的收藏",
                "我的发布",
            )
        )
    }

    override suspend fun initData() {}

}
