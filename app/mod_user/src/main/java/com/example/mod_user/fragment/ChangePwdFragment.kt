package com.example.mod_user.fragment

import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.controller.ToolbarController
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.databinding.FragmentChangePwdBinding
import kotlinx.coroutines.launch

class ChangePwdFragment :
    BaseFragment<FragmentChangePwdBinding>(FragmentChangePwdBinding::inflate) {

    override fun initView() {
        mToolbarController.initTitle("修改密码")
        binding.apply {
            btnSubmit.setOnClickListener {
                UserDataManager.loginUser
                    ?.let { user ->
                        val oldPwd: String = etOldPwd.text.toString()
                        val pwd: String = etPwd.text.toString()
                        val againPwd: String = etPwdAgain.text.toString()

                        if (oldPwd.isEmpty() || pwd.isEmpty() || againPwd.isEmpty()) {
                            ToastUtils.info()
                        } else if (pwd != oldPwd) {
                            ToastUtils.info("原密码错误")
                        } else if (pwd.length < 6) {
                            ToastUtils.info("请输入至少6位的密码")
                        } else if (pwd != againPwd) {
                            ToastUtils.info("两次输入密码不一致")
                        } else {
                            user.pwd = pwd
                            mViewModel.viewModelScope.launch {
                                UserDataManager.insert(user)
                                    ?.apply {
                                        ToastUtils.success("修改成功")
                                        goto(LoginFragment::class.java)
                                        finish()
                                    }
                            }
                        }
                    }
            }
        }
    }

    override suspend fun initData() {

    }
}