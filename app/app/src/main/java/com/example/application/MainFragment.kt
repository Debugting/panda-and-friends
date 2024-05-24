package com.example.application

import android.text.TextUtils
import android.view.Gravity
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.application.databinding.DrawerContentBinding
import com.example.application.databinding.DrawerSlideBinding
import com.example.application.databinding.MainFragmentBinding
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.base.BaseHomeFragment
import com.example.lib_frame.base.BasePaperAdapter
import com.example.lib_frame.manager.UploadDataManager
import com.example.lib_frame.theme.ThemeFragment
import com.example.lib_frame.utils.DialogUtils
import com.example.lib_frame.utils.MediaUtils
import com.example.mod_paper.fragment.PaperHomeFragment
import com.example.mod_user.dao.UserDataManager
import com.example.mod_user.fragment.ChangeInfoFragment
import com.example.mod_user.fragment.ChangePwdFragment
import com.example.mod_user.fragment.LoginFragment
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    private val user = UserDataManager.loginUser
    private lateinit var contentBinding: DrawerContentBinding
    private lateinit var slideBinding: DrawerSlideBinding

    private val fragments = listOf<BaseHomeFragment<*>>(
        PaperHomeFragment.newInstance(1, "指南", com.example.mod_paper.R.mipmap.ic_menu_paper),
        PaperHomeFragment.newInstance(2, "视频", com.example.mod_paper.R.mipmap.ic_menu_video),
        PaperHomeFragment.newInstance(3, "讨论", com.example.mod_paper.R.mipmap.ic_menu_discuss),
        PaperHomeFragment.newInstance(4, "故事", com.example.mod_paper.R.mipmap.ic_menu_story),
    )

    override fun initView() {
        initViewPaper()
    }

    private fun initViewPaper() {
        contentBinding = DrawerContentBinding.bind(binding.root).apply {
            mViewPaper.isUserInputEnabled = false
            fragments.onEach {
                mBottomNavigationView.menu.add(it.mNavTitle).setIcon(it.mNavIcon)
            }
            mBottomNavigationView.setOnItemReselectedListener { item ->
                fragments.onEachIndexed { index, it ->
                    if (TextUtils.equals(item.title, it.mNavTitle)) {
                        mViewPaper.currentItem = index
                    }
                }
            }
            BasePaperAdapter(this@MainFragment).apply {
                mViewPaper.adapter = this
                update(fragments)
            }
            mViewPaper.offscreenPageLimit = fragments.size
            mViewPaper.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    mBottomNavigationView.menu.getItem(position).setChecked(true)
                    tvTitle.text = mBottomNavigationView.menu.getItem(position).title
                }
            })

            tvTitle.text = mBottomNavigationView.menu.getItem(0).title
            ivImg.setOnClickListener {
                binding.drawerLayout.openDrawer(Gravity.LEFT)
            }
        }
        slideBinding = DrawerSlideBinding.bind(binding.root).apply {
            menuChangeInfo.setOnClickListener { goto(ChangeInfoFragment::class.java) }
            menuChangePwd.setOnClickListener { goto(ChangePwdFragment::class.java) }
            menuTheme.setOnClickListener { goto(ThemeFragment::class.java) }
            menuLoginOut.setOnClickListener {
                DialogUtils.askDialog("是否退出登录？", {
                    UserDataManager.loginUser = null
                    goto(LoginFragment::class.java)
                    finish()
                })
            }
            ivImgSlide.setOnClickListener {
                MediaUtils.choiceImage(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: java.util.ArrayList<LocalMedia>?) {
                        result?.first()?.cutPath?.apply {
                            updateAvatar(this)
                        }
                    }

                    override fun onCancel() {
                    }
                }, 1, 1)
            }
        }
    }

    private fun updateAvatar(path: String) {
        mViewModel.viewModelScope.launch {
            user?.apply {
                UploadDataManager.uploadImage(path)?.let { url ->
                    imgUrl = url
                    UserDataManager.insert(this)?.apply {
                        contentBinding.ivImg.load(url)
                        slideBinding.ivImgSlide.load(url)
                    }
                }
            }
        }
    }

    override suspend fun initData() {
        user?.apply {
            binding.apply {
                contentBinding.ivImg.load(imgUrl)
                slideBinding.ivImgSlide.load(imgUrl)
                slideBinding.tvName.text = name
                slideBinding.tvWord.text = word
            }
        }
    }

    override fun onBackPressed() {
    }
}
