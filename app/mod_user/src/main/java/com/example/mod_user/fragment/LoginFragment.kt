package com.example.mod_user.fragment

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.manager.ConfigManager
import com.example.lib_frame.manager.RouterManager.MAIN
import com.example.lib_frame.utils.DialogUtils
import com.example.lib_frame.utils.RegexUtils
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_user.dao.User
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

/**
 * 登录界面
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private var mHistory: HashMap<String, User> = HashMap()

    override fun initView() {
        binding.apply {
            etAct.apply {
                addTextChangedListener {
                    it.isNullOrEmpty().apply {
                        btnClearAct.isInvisible = this
                    }
                }
                setOnFocusChangeListener { _, b ->
                    hint = if (b) "" else "请输入账号"
                }
            }
            etPwd.apply {
                addTextChangedListener {
                    it.isNullOrEmpty().apply {
                        btnClearPwd.isInvisible = this
                    }
                }
                setOnFocusChangeListener { _, b ->
                    hint = if (b) "" else "请输入密码"
                }
            }
            btnClearAct.setOnClickListener {
                etAct.setText("")
                etPwd.setText("")
            }
            btnClearPwd.setOnClickListener {
                etPwd.setText("")
            }

            btnLogin.setOnClickListener {
                val act: String = etAct.text.toString()
                val pwd: String = etPwd.text.toString()

                if (act.isEmpty() || pwd.isEmpty()) {
                    ToastUtils.info("请输入账号密码")
                    return@setOnClickListener
                }

                mViewModel.viewModelScope.launch {
                    if (UserDataManager.login(act, pwd)) {
                        goto(MAIN)
                    }
                }
            }

            btnRegister.setOnClickListener { goto(RegisterFragment::class.java) }

            btnFindPwd.setOnClickListener { goto(FindPwdFragment::class.java) }

            ivLogo.setOnLongClickListener {
                val oldAddress: String = ConfigManager.baseUrl
                DialogUtils.inputDialog(
                    "配置", "请输入服务器地址", oldAddress, oldAddress
                ) { _, v, inputStr ->
                    if (!RegexUtils.checkIpAddress(inputStr)) {
                        ToastUtils.error("请输入正确的服务器地址")
                        return@inputDialog false
                    }
                    ConfigManager.baseUrl = inputStr
                    ToastUtils.success("修改成功，即将重启")
                    mHandler.postDelayed({ AppManager.restartApp() }, 1000)
                    false
                }
                false
            }
        }
    }

    override suspend fun initData() {
        binding.apply {
            UserDataManager.lastLogin()?.apply {
                etAct.setText(act)
                etPwd.setText(pwd)
                ivHead.load(imgUrl)
                btnClearAct.visibility = View.VISIBLE
                btnClearPwd.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {}
}
