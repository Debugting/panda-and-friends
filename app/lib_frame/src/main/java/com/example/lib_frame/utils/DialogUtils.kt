package com.example.lib_frame.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.example.lib_frame.R
import com.example.lib_frame.manager.AppManager
import com.example.lib_frame.manager.ConfigManager
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialog.interfaces.OnMenuItemClickListener
import com.kongzue.dialog.interfaces.OnNotificationClickListener
import com.kongzue.dialog.util.DialogSettings
import com.kongzue.dialog.util.InputInfo
import com.kongzue.dialog.v3.BottomMenu
import com.kongzue.dialog.v3.InputDialog
import com.kongzue.dialog.v3.MessageDialog
import com.kongzue.dialog.v3.Notification

object DialogUtils {
    fun setStyle(style: DialogSettings.STYLE) {
        DialogSettings.style = style
    }

    fun messageDialog(title: String, content: String) {
        MessageDialog.show(AppManager.activity, title, content, "确定")
    }

    fun messageDialog(
        title: String, content: String, btn: String, onAskListener: OnAskListener,
    ) {
        MessageDialog.show(AppManager.activity, title, content, btn)
            .setOnOkButtonClickListener { baseDialog, v ->
                onAskListener.submit()
                false
            }
    }

    fun messageDialog(title: String, content: String, onAskListener: OnAskListener) {
        MessageDialog.show(AppManager.activity, title, content, "确定")
            .setOnOkButtonClickListener { baseDialog, v ->
                onAskListener.submit()
                false
            }
    }

    fun deleteDialog(askListener: OnAskListener) {
        MessageDialog.show(
            AppManager.activity, "提示", "是否删除该数据？", "是", "否",
        ).setButtonOrientation(LinearLayout.VERTICAL).setOnOkButtonClickListener { baseDialog, v ->
            askListener.submit()
            false
        }
    }

    fun askDialog(
        content: String, submit: () -> Unit, title: String = "提示",
    ) {
        MessageDialog.show(
            AppManager.activity, title, content, "是", "否"
        ).setButtonOrientation(LinearLayout.VERTICAL).setOnOkButtonClickListener { baseDialog, v ->
            submit.invoke()
            false
        }
    }

    fun askCall(phone: String) {
        MessageDialog.show(
            AppManager.activity, "提示", "是否拨打电话？", "是", "否", "取消"
        ).setButtonOrientation(LinearLayout.VERTICAL).setOnOkButtonClickListener { baseDialog, v ->
            SystemUtils.callPhone(phone)
            false
        }
    }

    fun askSubmit(askListener: OnAskListener) {
        MessageDialog.show(
            AppManager.activity, "提示", "是否提交？", "是", "否", "取消"
        ).setButtonOrientation(LinearLayout.VERTICAL).setOnOkButtonClickListener { baseDialog, v ->
            askListener.submit()
            false
        }
    }

    fun inputDialog(
        title: String,
        hint: String,
        onInputDialogButtonClickListener: OnInputDialogButtonClickListener,
    ) {
        InputDialog.build(AppManager.activity).setTitle(title).setMessage("").setHintText(hint)
            .setOkButton("确定", onInputDialogButtonClickListener).setCancelButton("取消")
            .setCancelable(true).show()
    }

    fun inputDialog(
        title: String,
        content: String,
        hint: String,
        input: String,
        onInputDialogButtonClickListener: OnInputDialogButtonClickListener,
    ) {
        InputDialog.build(AppManager.activity).setTitle(title).setMessage(content).setHintText(hint)
            .setInputText(input).setOkButton("确定", onInputDialogButtonClickListener)
            .setCancelButton("取消").setCancelable(true).show()
    }

    fun inputCount(onInputDialogButtonClickListener: OnInputDialogButtonClickListener) {
        InputDialog.build(AppManager.activity).setTitle("请输入数量").setMessage("")
            .setHintText("请输入数量")
            .setInputInfo(InputInfo().setInputType(InputType.TYPE_CLASS_NUMBER))
            .setOkButton("确定", onInputDialogButtonClickListener).setCancelButton("取消")
            .setCancelable(true).show()
    }

    fun notification(
        title: String,
        content: String,
        id: Int,
        onNotificationClickListener: OnNotificationClickListener,
    ) {
        Notification.show(AppManager.app, title, content, id)
            .setOnNotificationClickListener(onNotificationClickListener)
    }

    fun menuDialog(
        menus: Array<String>, onMenuItemClickListener: OnMenuItemClickListener,
    ) {
        BottomMenu.show(AppManager.activity, menus, onMenuItemClickListener)
    }

    fun menuDialog(menus: List<String>, onMenuItemClickListener: OnMenuItemClickListener) {
        val charSequences: MutableList<CharSequence> = ArrayList()
        charSequences.addAll(menus)
        BottomMenu.show(AppManager.activity, charSequences, onMenuItemClickListener)
    }

    fun menuDialog(
        title: String, menus: Array<String>, onMenuItemClickListener: OnMenuItemClickListener,
    ) {
        KeyBoardUtils.hideSoftInput()
        BottomMenu.show(AppManager.activity, title, menus, onMenuItemClickListener)
    }

    fun menuDialog(
        title: String, baseAdapter: BaseAdapter, onMenuItemClickListener: OnMenuItemClickListener,
    ) {
        BottomMenu.show(AppManager.activity, baseAdapter, onMenuItemClickListener)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun showHtml(html: String) {
        var content = html
        val activity: Activity = AppManager.activity
        val view: View = LayoutInflater.from(activity).inflate(R.layout.dialog_web, null)
        val webView: WebView = view.findViewById<WebView>(R.id.webView)
        webView.isVerticalScrollBarEnabled = false //不能垂直滑动
        webView.isHorizontalScrollBarEnabled = false //不能水平滑动
        webView.webViewClient = WebViewClient()
        val settings: WebSettings = webView.settings
        settings.loadWithOverviewMode = true //设置WebView是否使用预览模式加载界面。
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccess = true
        settings.allowFileAccessFromFileURLs = true
        settings.textSize = WebSettings.TextSize.NORMAL //通过设置WebSettings，改变HTML中文字的大小
        settings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        //设置WebView属性，能够执行Javascript脚本
        settings.javaScriptEnabled = true //设置js可用
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN //支持内容重新布局
        content =
            "</Div><head><style>body{font-size:16px}</style>" + "<style>img{ width:100% !important;margin-top:0.4em;margin-bottom:0.4em}</style>" + "<style>video{ width:100% !important;margin-top:0.4em;margin-bottom:0.4em}</style>" + "<style>ul{ padding-left: 1em;margin-top:0em}</style>" + "<style>ol{ padding-left: 1.2em;margin-top:0em}</style>" + "</head>" + content
        if (ConfigManager.isOpenNet) {
            val baseUrl: String = ConfigManager.baseUrl
            val imageUrls: ArrayList<String> = RichUtils.returnImageUrlsFromHtml(content)
            val videoUrls: ArrayList<String> = RichUtils.returnVideoUrlsFromHtml(content)
            for (i in imageUrls.indices) {
                val url = imageUrls[i]
                if (url.contains("http")) {
                    continue
                }
                content = content.replace(url, "$baseUrl/fileImage/watchpath=$url")
            }
            for (i in videoUrls.indices) {
                val url = videoUrls[i]
                if (url.contains("http")) {
                    continue
                }
                content = content.replace(url, "$baseUrl/fileVideo/watchpath=$url")
            }
        }
        val local = "file:///android_asset/rich"
        webView.loadDataWithBaseURL(local, content, "text/html", "utf-8", null)
        AlertDialog.Builder(activity).setView(view).show()
    }

    interface OnAskListener {
        fun submit()
    }
}
