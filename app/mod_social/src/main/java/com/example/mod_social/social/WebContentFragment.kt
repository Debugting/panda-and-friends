package com.example.mod_social.social

import android.os.Bundle
import android.webkit.WebSettings
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.base.RoomBean
import com.example.lib_frame.manager.ConfigManager
import com.example.lib_frame.utils.RegexUtils
import com.example.lib_frame.utils.RichUtils
import com.example.mod_social.databinding.WebContentFragmentBinding

internal class WebContentFragment : BaseFragment<WebContentFragmentBinding>(WebContentFragmentBinding::inflate) {

    override fun initView() {
        val webSettings: WebSettings = binding.webView.settings
        //缩放操作
        webSettings.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提
        // 设置出现缩放工具
        webSettings.builtInZoomControls = false //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = true //隐藏原生的缩放控件
        webSettings.builtInZoomControls = false //设置显示缩放按钮;如果设置这个为false则就不能手势缩放了
        webSettings.loadWithOverviewMode = false
        webSettings.useWideViewPort = false
        //与JS交互调用
        webSettings.javaScriptEnabled = false
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //允许webview对文件的操作
        webSettings.allowUniversalAccessFromFileURLs = false
        webSettings.allowFileAccess = false
        webSettings.allowFileAccessFromFileURLs = false

        //禁止上下左右滚动(不显示滚动条)
        binding.webView.isScrollContainer = false
        binding.webView.isVerticalScrollBarEnabled = false
        binding.webView.isHorizontalScrollBarEnabled = false
    }

    override suspend fun initData() {
        arguments?.apply {
            mBundle.putAll(this)
        }
        mBundle.getSerializable("bean").apply {
            if (this is RoomBean<*>) {
                if (RegexUtils.checkURL(content)) {
                    binding.webView.settings.textZoom = 200
                    binding.webView.loadUrl(content)
                } else {
                    loadData(content)
                }
            }
        }
    }

    fun loadData(data: String) {
        var realData = """</Div>
                            <head>
                                <style>body{font-size:16px;max-width: 100%}</style>
                                <style>img{ max-width:100% !important;margin-top:0.2em;margin-bottom:0.2em}</style>
                                <style>audio{ width:100% !important;margin-top:0.2em;margin-bottom:0.2em}</style>
                                <style>video{ width:100% !important;margin-top:0.2em;margin-bottom:0.2em}</style>
                                <style>ul{ padding-left: 1em;margin-top:0em}</style>
                                <style>ol{ padding-left: 1.2em;margin-top:0em}</style>
                            </head>
                            $data"""
        if (ConfigManager.isOpenNet) {
            val baseUrl: String = ConfigManager.baseUrl
            val imageUrls: ArrayList<String> = RichUtils.returnImageUrlsFromHtml(realData)
            val audioUrls: ArrayList<String> = RichUtils.returnAudioUrlsFromHtml(realData)
            val videoUrls: ArrayList<String> = RichUtils.returnVideoUrlsFromHtml(realData)
            for (i in imageUrls.indices) {
                val url = imageUrls[i]
                if (url.contains("http://") || url.contains("https://")) {
                    continue
                }
                realData = realData.replace(url, baseUrl + url)
            }
            for (i in audioUrls.indices) {
                val url = audioUrls[i]
                if (url.contains("http://") || url.contains("https://")) {
                    continue
                }
                realData = realData.replace(url, baseUrl + url)
            }
            for (i in videoUrls.indices) {
                val url = videoUrls[i]
                if (url.contains("http://") || url.contains("https://")) {
                    continue
                }
                realData = realData.replace(url, baseUrl + url)
            }
        }
        val local = "file:///android_asset/rich"
        binding.webView.loadDataWithBaseURL(local, realData, "text/html", "utf-8", null)
    }

    companion object {
        fun newInstance(bean: SocialBean<*>): WebContentFragment {
            val args = Bundle()
            args.putSerializable("bean", bean)
            val fragment = WebContentFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
