package com.example.mod_user.fragment

import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.controller.ToolbarController
import com.example.lib_frame.utils.PickerUtils
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_user.dao.User
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.databinding.FragmentChangeInfoBinding
import kotlinx.coroutines.launch

class ChangeInfoFragment :
    BaseFragment<FragmentChangeInfoBinding>(FragmentChangeInfoBinding::inflate) {

    private val user = UserDataManager.loginUser

    override fun initView() {
        mToolbarController.initTitle("修改资料")
        binding.apply {
            btnSubmit.setOnClickListener {
                val name: String = etName.text.toString()
                if (name.isEmpty()) {
                    ToastUtils.info()
                } else {
                    user?.let {
                        it.name = name
                        submit(user)
                    }
                }
            }
            btnSex.setOnClickListener {
                PickerUtils.choiceSex(object : PickerUtils.OnChoiceListener {
                    override fun choice(pos: Int, result: String) {
                        user?.sex = pos
                        btnSex.text = result
                    }
                })
            }
            btnDate.setOnClickListener {
                PickerUtils.choiceDate(object : PickerUtils.OnDateChoiceListener {
                    override fun choice(time: Long, str: String) {
                        user?.birthday = str
                        btnDate.text = str
                    }
                })
            }
            btnProvince.setOnClickListener {
                PickerUtils.choiceProvince(object : PickerUtils.OnChoiceListener {
                    override fun choice(pos: Int, result: String) {
                        user?.province = result
                        btnProvince.text = result
                    }
                })
            }
        }
    }

    override suspend fun initData() {
        user?.apply {
            binding.apply {
                etName.setText(name)
                btnSex.text = getSexWord()
                btnDate.text = birthday.substring(0, 10)
                btnProvince.text = province
            }
        }
    }

    private fun submit(user: User) {
        mViewModel.viewModelScope.launch {
            UserDataManager.insert(user)?.apply {
                ToastUtils.success("修改成功")
                UserDataManager.loginUser = user
                finish()
            }
        }
    }
}