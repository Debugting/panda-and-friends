package com.example.lib_frame.controller

import androidx.viewbinding.ViewBinding
import androidx.lifecycle.viewModelScope
import com.example.lib_frame.R
import com.example.lib_frame.base.BaseFragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.launch

class RefreshController(fragment: BaseFragment<out ViewBinding>, canRefresh: Boolean = true) {

    init {
        fragment.apply {
            binding.root.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)?.apply {
                setEnableRefresh(canRefresh)
                setEnableLoadMore(false)
            }.let {
                it?.setOnRefreshListener {
                    request {
                        initData().apply {
                            it.finishRefresh()
                        }
                    }
                }
            }
        }
    }
}
