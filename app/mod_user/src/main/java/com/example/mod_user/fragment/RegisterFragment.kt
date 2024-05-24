package com.example.mod_user.fragment

import android.text.TextUtils
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.manager.UploadDataManager
import com.example.lib_frame.utils.MediaUtils
import com.example.lib_frame.utils.PickerUtils
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_user.dao.User
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.databinding.FragmentRegisterBinding
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Calendar

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val user: User = User()

    override fun initView() {
        mToolbarController.initTitle("注册账号")
        binding.btnSex.setOnClickListener {
            PickerUtils.choiceSex(object : PickerUtils.OnChoiceListener {
                override fun choice(pos: Int, result: String) {
                    user.sex = PickerUtils.SEX.values[pos]
                    binding.btnSex.text = result
                }
            })
        }
        binding.btnDate.setOnClickListener {
            val start = Calendar.getInstance()
            start[Calendar.YEAR] = start[Calendar.YEAR] - 70
            start[Calendar.MONTH] = 0
            start[Calendar.DAY_OF_MONTH] = 1
            start[Calendar.HOUR] = 0
            start[Calendar.MINUTE] = 0
            start[Calendar.SECOND] = 0
            val end = Calendar.getInstance()
            PickerUtils.choiceDate(
                start,
                end,
                object : PickerUtils.OnDateChoiceListener {
                    override fun choice(time: Long, str: String) {
                        user.birthday = str
                        binding.btnDate.text = str
                    }
                })
        }
        binding.btnProvince.setOnClickListener {
            PickerUtils.choiceProvince(object : PickerUtils.OnChoiceListener {
                override fun choice(pos: Int, result: String) {
                    user.province = result
                    binding.btnProvince.text = result
                }
            })
        }
        binding.ivImg.setOnClickListener {
            MediaUtils.choiceImage(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    result?.first()?.cutPath?.apply {
                        binding.ivImg.load(this)
                    }
                }

                override fun onCancel() {
                }
            }, 1, 1)
        }
        binding.btnSubmit.setOnClickListener { view ->
            user.apply {
                binding.etAct.text.toString().also { act = it }
                binding.etPwd.text.toString().also { pwd = it }
                binding.btnDate.text.toString().also { birthday = "$it 00:00:00" }
                binding.etQuestion.text.toString().also { question = it }
                binding.etAnswer.text.toString().also { answer = it }
                binding.etName.text.toString().also { name = it }
            }
            val pwdAgain: String = binding.etPwdAgain.text.toString()
            if (TextUtils.isEmpty(user.act)
                || TextUtils.isEmpty(user.pwd)
                || TextUtils.isEmpty(pwdAgain)
                || TextUtils.isEmpty(user.question)
                || TextUtils.isEmpty(user.answer)
                || TextUtils.isEmpty(user.name)
            ) {
                ToastUtils.info()
            } else if (user.act.length < 6 || user.pwd.length < 6) {
                ToastUtils.info("请输入至少6位的账号和密码")
            } else if (!user.pwd.equals(pwdAgain)) {
                ToastUtils.info("两次输入密码不一致")
            } else if (!user.pwd.equals(pwdAgain)) {
                ToastUtils.info("两次输入密码不一致")
            } else {
                mViewModel.viewModelScope.launch {
                    UserDataManager.getAll(mapOf("act" to user.act))?.apply {
                        if (isEmpty()) {
                            UploadDataManager.uploadImage(user.imgUrl)?.apply {
                                user.imgUrl = this
                                UserDataManager.insert(user)?.apply {
                                    ToastUtils.success("注册成功")
                                    finish()
                                }
                            }
                        } else {
                            ToastUtils.error("账号已存在")
                        }
                    }
                }
            }
        }
    }

    override suspend fun initData() {
        user.province = "北京"
        user.sex = 1
        user.userType = 1
        user.status = 1
    }
}