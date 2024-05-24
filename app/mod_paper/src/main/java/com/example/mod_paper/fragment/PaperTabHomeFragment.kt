package com.example.mod_paper.fragment

import com.example.lib_frame.base.BaseHomeFragment
import com.example.lib_frame.controller.MenuController
import com.example.lib_frame.controller.TabController
import com.example.lib_frame.databinding.FragmentTabHomeBinding
import com.example.mod_paper.dao.PaperTypeDataManager
import com.example.mod_paper.R

class PaperTabHomeFragment :
    BaseHomeFragment<FragmentTabHomeBinding>(FragmentTabHomeBinding::inflate) {
    private lateinit var tabController: TabController

    init {
        initNavMenu("帖子", R.mipmap.ic_menu_paper)
    }

    override fun initView() {
        tabController = TabController(this)
        MenuController(
            this, {
                when (it) {
//                    0 -> goto(PaperSearchFragment::class.java)
//                    1 -> goto(PaperCollectedFragment::class.java)
                }
            }, listOf(
                "发布帖子",
                "搜索公告",
                "我的帖子",
                "我的收藏"
            )
        )
    }

    override suspend fun initData() {
        val fragments = mutableListOf<PaperListFragment>()
        val tabs = mutableListOf<String>()
        PaperTypeDataManager.getAll()?.apply {
            onEach {
                tabs.add(it.name)
                fragments.add(PaperListFragment().apply {
                    mBundle.putLong("typeId", it.id)
                })
            }
        }
        tabController.update(tabs, fragments)
    }

}
