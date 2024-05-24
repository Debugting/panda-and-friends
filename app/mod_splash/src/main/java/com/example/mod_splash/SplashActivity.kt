package com.example.mod_splash

import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseActivity
import com.example.lib_frame.extensions.load
import com.example.lib_frame.extensions.setFullScreen
import com.example.lib_frame.manager.RouterManager.LOGIN
import com.example.lib_frame.manager.RouterManager.MAIN
import com.example.lib_frame.utils.FileUtils
import com.example.lib_frame.manager.SpManager
import com.example.lib_frame.utils.GlideUtils
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_splash.databinding.ActivitySplashBinding
import com.example.mod_user.dao.UserDataManager
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun preSetContentView() {
        setFullScreen()
    }

    override fun initView() {
        UserDataManager.loginUser.let { user ->
            if (user == null) {
                mHandler.postDelayed({
                    goto(LOGIN)
                    finish()
                }, 1500)
            } else {
                mViewModel.viewModelScope.launch {
                    UserDataManager.login(user.act, user.pwd).apply {
                        if (this) {
                            goto(MAIN)
                            finish()
                        } else {
                            goto(LOGIN)
                            finish()
                        }
                    }
                }
            }
        }
    }

    override suspend fun initData() {
        val imgPath = FileUtils.imgDir + "th.jpg"
        mBinding.tvContent.text = SpManager.get("splash_text", "")
        mBinding.ivSplash.load(imgPath)
        try {
            SplashService.g().getSplashList("js", 1).apply {
                images?.let {
                    if (it.isEmpty()) {
                        return@let
                    }
                    val splash: SplashRsp.ImagesDTO = it[0]
                    val content: String? = splash.copyright
                    val url = SplashService.URL + splash.url
                    mBinding.tvContent.text = content
                    GlideUtils.download(url, imgPath)
                    mBinding.ivSplash.load(imgPath)
                    SpManager.put("splash_text", content)
                }
            }
        } catch (e: Exception) {
            e.message?.apply {
                ToastUtils.error(this)
            }
        }
    }
}