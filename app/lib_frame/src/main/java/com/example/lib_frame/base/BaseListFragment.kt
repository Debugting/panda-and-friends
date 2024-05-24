package com.example.lib_frame.base

import android.view.View
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.lib_frame.controller.RefreshController
import com.example.lib_frame.databinding.FragmentListBinding

abstract class BaseListFragment<T : RoomBean<T>>(private val canRefresh: Boolean = true) :
    BaseFragment<FragmentListBinding>(FragmentListBinding::inflate),
    BaseQuickAdapter.OnItemLongClickListener<T>,
    BaseQuickAdapter.OnItemClickListener<T> {

    private var mKeyWord: String? = null
    private var mDataList = mutableListOf<T>()
    private var mAdapter: BaseRcvAdapter<T> = getAdapter().apply {
        setOnItemClickListener(this@BaseListFragment)
        setOnItemLongClickListener(this@BaseListFragment)
    }

    abstract fun getAdapter(): BaseRcvAdapter<T>

    override fun initView() {
        binding.recyclerview.adapter = mAdapter
        RefreshController(this, canRefresh)
        if (mBundle.getBoolean("isSearch")) {
            mToolbarController.apply {
                initTitle("查询搜索")
                initSearch { updateData(it) }
            }
        }
        if (mBundle.getBoolean("isCollected")) {
            mToolbarController.apply {
                initTitle("我的收藏")
                initSearch { updateData(it) }
            }
        }
        if (mBundle.getBoolean("isMine")) {
            mToolbarController.apply {
                initTitle("我的发布")
                initSearch { updateData(it) }
            }
        }
    }

    fun updateData(dataList: List<T>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        val items = mutableListOf<T>()
        mKeyWord.apply {
            if (this == null) {
                items.addAll(mDataList)
            } else {
                mDataList.onEach {
                    if (it.title.contains(this)
                        || it.name.contains(this)
                        || it.word.contains(this)
                        || it.remark.contains(this)
                        || it.content.contains(this)
                        || it.createTime.contains(this)
                    ) {
                        items.add(it)
                    }
                }
            }
        }
        mAdapter.update(items)
    }

    fun updateData(keyword: String? = null) {
        mKeyWord = keyword
        val items = mutableListOf<T>()
        mKeyWord.apply {
            if (this == null) {
                items.addAll(mDataList)
            } else {
                mDataList.onEach {
                    if (it.title.contains(this)
                        || it.name.contains(this)
                        || it.word.contains(this)
                        || it.remark.contains(this)
                        || it.content.contains(this)
                        || it.createTime.contains(this)
                    ) {
                        items.add(it)
                    }
                }
            }
        }
        mAdapter.update(items)
    }

    override fun onResume() {
        super.onResume()
        request { initData() }
    }

    override fun onClick(adapter: BaseQuickAdapter<T, *>, view: View, position: Int) {

    }

    override fun onLongClick(
        adapter: BaseQuickAdapter<T, *>,
        view: View,
        position: Int
    ): Boolean {
        return false
    }
}