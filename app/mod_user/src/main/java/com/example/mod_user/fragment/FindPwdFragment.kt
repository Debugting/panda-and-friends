package com.example.mod_user.fragment

import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.controller.ToolbarController
import com.example.lib_frame.utils.ToastUtils.error
import com.example.lib_frame.utils.ToastUtils.info
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.databinding.FragmentFindPwdBinding
import kotlinx.coroutines.launch

class FindPwdFragment : BaseFragment<FragmentFindPwdBinding>(FragmentFindPwdBinding::inflate) {

    override fun initView() {
        mToolbarController.initTitle("找回密码")
        binding.btnSubmit.setOnClickListener {
            binding.etAct.text.toString().apply {
                if (isEmpty()) {
                    info()
                } else {
                    submit(this)
                }
            }
        }
    }

    private fun submit(s: String) {
        mViewModel.viewModelScope.launch {
            UserDataManager.getAll(
                mapOf(
                    "act" to s
                )
            )?.apply {
                if (isEmpty()) {
                    error("账号不存在")
                } else {
                    goto(FindPwdFragment2::class.java, serializable = first())
                }
            }
        }
    }

    override suspend fun initData() {}
}