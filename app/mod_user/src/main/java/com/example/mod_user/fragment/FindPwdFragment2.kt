package com.example.mod_user.fragment

import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.controller.ToolbarController
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_user.dao.User
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.databinding.FragmentFindPwd2Binding
import kotlinx.coroutines.launch

class FindPwdFragment2 : BaseFragment<FragmentFindPwd2Binding>(FragmentFindPwd2Binding::inflate) {

    override fun initView() {
        mToolbarController.initTitle("找回密码")
    }

    override suspend fun initData() {
        (mBundle.getSerializable("bean") as User?)?.apply {
            binding.etAct.setText(act)
            binding.etQuestion.setText(question)
            binding.btnSubmit.setOnClickListener {
                val pwd: String = binding.etPwd.text.toString()
                val againPwd: String = binding.etPwdAgain.text.toString()
                val answer: String = binding.etAnswer.text.toString()
                if (answer.isEmpty() || pwd.isEmpty() || againPwd.isEmpty()) {
                    ToastUtils.info()
                } else if (pwd.length < 6) {
                    ToastUtils.info("请输入至少6位的密码")
                } else if (pwd != againPwd) {
                    ToastUtils.info("两次输入密码不一致")
                } else if (this.answer == answer) {
                    this.pwd = pwd
                    submit(this)
                } else {
                    ToastUtils.error("密保问题错误")
                }
            }
        }
    }

    private fun submit(user: User) {
        mViewModel.viewModelScope.launch {
            UserDataManager.insert(user)?.apply {
                ToastUtils.success("修改成功")
                goto(LoginFragment::class.java)
            }
        }
    }
}