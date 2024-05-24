package com.example.lib_frame.utils

import android.text.TextUtils
import java.util.regex.Pattern

object RichUtils {

    lateinit var baseUrl: String

    fun init(url: String) {
        baseUrl = url
    }

    /**
     * 截取富文本中的图片链接
     */
    fun returnImageUrlsFromHtml(content: String?): ArrayList<String> {
        val imageSrcList: MutableList<String> = ArrayList()
        if (TextUtils.isEmpty(content)) {
            return imageSrcList as ArrayList<String>
        }
        val p = Pattern.compile(
            "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\u000c>]+(\\.jpg|\\.bmp|\\.gif|\\.png|\\.jpeg|\\.pic|\\b)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE
        )
        val m = p.matcher(content)
        var quote: String?
        var src: String
        while (m.find()) {
            quote = m.group(1)
            src = if (quote == null || quote.trim { it <= ' ' }.length == 0) m.group(2)
                .split("//s+".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0] else m.group(2)
            imageSrcList.add(src)
        }
        return imageSrcList as ArrayList<String>
    }

    /**
     * 截取富文本中的音频链接
     */
    fun returnAudioUrlsFromHtml(content: String?): ArrayList<String> {
        val imageSrcList: MutableList<String> = ArrayList()
        if (TextUtils.isEmpty(content)) {
            return imageSrcList as ArrayList<String>
        }
        val p = Pattern.compile(
            "<audio\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\u000c>]+(\\.mp3|\\.m4a|\\.wma|\\.amr|\\.ape|\\.flac|\\.aac|\\b)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE
        )
        val m = p.matcher(content)
        var quote: String?
        var src: String
        while (m.find()) {
            quote = m.group(1)
            src = if (quote == null || quote.trim { it <= ' ' }.length == 0) m.group(2)
                .split("//s+".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0] else m.group(2)
            imageSrcList.add(src)
        }
        return imageSrcList as ArrayList<String>
    }

    /**
     * 截取富文本中的视频链接
     */
    fun returnVideoUrlsFromHtml(content: String?): ArrayList<String> {
        val videoSrcList: MutableList<String> = ArrayList()
        if (TextUtils.isEmpty(content)) {
            return videoSrcList as ArrayList<String>
        }
        val p = Pattern.compile(
            "<video\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\u000c>]+(\\.mp4|\\.flv|\\.avi|\\.rm|\\.rmvb|\\b)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE
        )
        val m = p.matcher(content)
        var quote: String?
        var src: String
        while (m.find()) {
            quote = m.group(1)
            src = if (quote == null || quote.trim { it <= ' ' }.length == 0) m.group(2)
                .split("//s+".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0] else m.group(2)
            videoSrcList.add(src)
        }
        return videoSrcList as ArrayList<String>
    }

    /**
     * 截取富文本中的纯文本内容
     */
    fun returnOnlyText(htmlStr: String): String {
        return if (TextUtils.isEmpty(htmlStr)) {
            ""
        } else {
            val regFormat = "\\s*|\t|\r|\n"
            val regTag = "<[^>]*>"
            var text = htmlStr.replace(regFormat.toRegex(), "").replace(regTag.toRegex(), "")
            text = text.replace("&nbsp;", "")
            text
        }
    }
}