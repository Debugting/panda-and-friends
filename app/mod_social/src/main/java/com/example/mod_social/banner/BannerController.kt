package com.example.mod_social.banner

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import androidx.lifecycle.viewModelScope
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.R
import com.example.mod_social.social.SocialDetailsFragment
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.launch

class BannerController(
    val fragment: BaseFragment<out ViewBinding>,
    private val dataType: String
) {

    private val rootView = fragment.binding.root
    private val banner =
        rootView.findViewById<com.youth.banner.Banner<Banner, BannerAdapter<Banner, SocialBannerAdapter.ViewHolder>>>(
            R.id.banner
        )

    private var adapter: SocialBannerAdapter = SocialBannerAdapter()

    init {
        fragment::initView.apply {
            initView()
        }
        fragment::initData.apply {
            fragment.mViewModel.viewModelScope.launch {
                initData()
            }
        }
    }

    private fun initView() {
        banner?.setIndicator(CircleIndicator(fragment.mActivity))
        adapter.setOnBannerListener { data, _ ->
            fragment.goto(SocialDetailsFragment::class.java, serializable = data)
        }
        banner?.setAdapter(adapter)
    }

    private suspend fun initData() {
        BannerDataManager.getAll(
            mapOf(
                "dataType" to this@BannerController.dataType
            )
        )?.apply {
            adapter.setDatas(this)
        }
    }

    fun updateData(imgUrls: ArrayList<String>) {
        adapter.setOnBannerListener { _, position ->
            val bundle = Bundle()
            bundle.putString("imgPath", imgUrls[position])
            bundle.putStringArrayList("imgPaths", imgUrls)
            fragment.goto(BrowserImageFragment::class.java,bundle = bundle)
        }
        banner.setDatas(imgUrls.map {
            Banner().apply {
                imgUrl = it
            }
        })
    }
}


